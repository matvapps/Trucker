package com.foora.perevozkadev.ui.messages_info;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.data.network.model.Action;
import com.foora.perevozkadev.data.network.model.OrderRequest;
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.base.BaseViewHolder;
import com.foora.perevozkadev.ui.my_transport.model.Transport;
import com.foora.perevozkadev.ui.profile.adapter.ProfileTransportAdapter;
import com.foora.perevozkadev.ui.profile.model.Profile;
import com.foora.perevozkadev.utils.ViewUtils;
import com.foora.perevozkadev.utils.custom.ItemSpacingDecoration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Alexander Matvienko on 04.02.2019.
 */
public class MessagesInfoAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final String TAG = MessagesInfoAdapter.class.getSimpleName();

    private OrderRequest requestInfo;
    private List<Action> actions;
    private ProfileTransportAdapter transportAdapter;
    private ContactAdapter contactAdapter;
    private Profile profile;
    private Order order;

    private Callback listener;

    public MessagesInfoAdapter() {
        this.actions = new ArrayList<>();
    }

    public void setOrderRequest(OrderRequest requestInfo) {
        this.requestInfo = requestInfo;

        Action actionForRemove = null;

        List<Action> actions = requestInfo.getActions();
        for (Action action: actions) {
            if (action.getAction().equals("Request accepted"))
                actionForRemove = action;
        }

        if (actionForRemove != null)
            actions.remove(actionForRemove);
//        actions.remove(actions.size() - 1);
        Collections.sort(actions, (lhs, rhs) -> {
            SimpleDateFormat formatIn = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss", Locale.getDefault());
            try {
                Date date1 = formatIn.parse(lhs.getTime());
                Date date2 = formatIn.parse(rhs.getTime());

                return date1.after(date2) ? 1 : date1.before(date2) ? -1 : 0;

            } catch (ParseException e) {
                e.printStackTrace();
            }

            return 0;
            // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
//                return lhs.getId() > rhs.getId() ? -1 : (lhs.customInt < rhs.customInt ) ? 1 : 0;
        });

        this.actions.clear();
        this.actions.addAll(actions);
        notifyDataSetChanged();
    }

    public Action getItem(int pos) {
        return actions.get(pos);
    }

    public void addTransport(Transport transport) {
        transportAdapter.addItem(transport);
        if (transportAdapter.getItemCount() == requestInfo.getTransports().size())
            notifyDataSetChanged();
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public void setOrder(Order order) {
        this.order = order;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView;

        switch (i) {
            case 0:
                rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_message_request, viewGroup, false);
                return new ActionRequestViewHolder(rootView);
            case 1:
                rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_message_request_accepted, viewGroup, false);
                return new ActionAcceptedViewHolder(rootView);
            default:
                if (getItem(i).getAction().equals("finished")
                        || getItem(i).getAction().equals("Груз доставлен")) {
                    rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_message_order_finish, viewGroup, false);
                    return new ActionFinishedViewHolder(rootView);
                }
                rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_message_base, viewGroup, false);
                return new ActionBaseViewHolder(rootView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder, int i) {
        viewHolder.onBind(i);
    }

    @Override
    public int getItemCount() {
        return actions.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private class ActionRequestViewHolder extends BaseMessageViewHolder {

        RecyclerView transportList;
        Button btnRefuse;
        Button btnAccept;

        public ActionRequestViewHolder(View itemView) {
            super(itemView);

            transportList = itemView.findViewById(R.id.transport_list);
            btnRefuse = itemView.findViewById(R.id.btn_refuse);
            btnAccept = itemView.findViewById(R.id.btn_accept);

            transportAdapter = new ProfileTransportAdapter();
            transportAdapter.setListener(new ProfileTransportAdapter.Callback() {
                @Override
                public void onClick(int pos, Transport transport) {
                    listener.onOpenTransport(transport.getId());
                }
            });
            transportList.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            transportList.addItemDecoration(new ItemSpacingDecoration(0, ViewUtils.dpToPx(4), 0, 0));
            transportList.setAdapter(transportAdapter);
        }

        @Override
        protected void clear() {

        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            if (profile.getUserId() == requestInfo.getSenderId()) {
                btnAccept.setVisibility(View.GONE);
                btnRefuse.setText("Ожидание ответа");

            } else {
                btnRefuse.setOnClickListener(v -> {
                    if (listener != null)
                        listener.onRefuseRequest(requestInfo.getId());
                });

                btnAccept.setOnClickListener(v -> {
                    if (listener != null) {
                        listener.onAcceptRequest(requestInfo.getId());
                        Log.d(TAG, "onBind: " + requestInfo.getId());
                    }
                });
            }

            if (requestInfo.getStatus() == 1) {
                btnRefuse.setVisibility(View.GONE);
                btnAccept.setVisibility(View.GONE);
            }

//            if (getItemCount() > 1) {

//            }


            if (transportAdapter.getItemCount() != requestInfo.getTransports().size()) {
                for (int transportId : requestInfo.getTransports()) {
                    if (listener != null)
                        listener.onRequestTransport(transportId);
                }
            } else transportAdapter.notifyDataSetChanged();
        }
    }

    private class ActionAcceptedViewHolder extends BaseMessageViewHolder {

        RecyclerView contactList;

        public ActionAcceptedViewHolder(View itemView) {
            super(itemView);

            contactList = itemView.findViewById(R.id.contact_list);

            contactAdapter = new ContactAdapter();
            contactList.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            contactList.addItemDecoration(new ItemSpacingDecoration(0, ViewUtils.dpToPx(8), 0, 0));
            contactList.setAdapter(contactAdapter);
        }

        @Override
        protected void clear() {

        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            if (order == null) {
                if (listener != null) {
                    listener.onRequestOrder(requestInfo.getOrderId());
                }
            } else {
                contactAdapter.clear();
                if (profile.getUserId() == requestInfo.getSenderId()) {
                    titleTxtv.setText(String.format("%s %s", order.getContactPerson(), "принял вашу заявку"));
                } else {
                    titleTxtv.setText("Вы отправили данные пользователю");
                }
                contactAdapter.addItem(order.getPhone());
                contactAdapter.addItem(order.getEmail());
            }

        }
    }

    private class ActionBaseViewHolder extends BaseMessageViewHolder {

        public ActionBaseViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void clear() {

        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            if (requestInfo.getStatus() == 2) {
                titleTxtv.setTextColor(R.color.red_error);
                titleTxtv.setText("Ваш запрос отклонен");
            } else
                titleTxtv.setText(getItem(position).getAction());

        }
    }

    private class ActionSosViewHolder extends BaseMessageViewHolder {

        public ActionSosViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void clear() {

        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
        }
    }

    private class ActionFinishedViewHolder extends BaseMessageViewHolder {

        public ActionFinishedViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void clear() {

        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            itemView.setOnClickListener(v -> {
                if (listener != null)
                    listener.onOpenOrder(requestInfo.getOrderId());
            });

        }
    }

    private class BaseMessageViewHolder extends BaseViewHolder {

        TextView dateTxtv;
        TextView titleTxtv;
        TextView messageTxtv;

        public BaseMessageViewHolder(View itemView) {
            super(itemView);

            dateTxtv = itemView.findViewById(R.id.date);
            titleTxtv = itemView.findViewById(R.id.title);
            messageTxtv = itemView.findViewById(R.id.message);
        }

        @Override
        protected void clear() {

        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            SimpleDateFormat formatIn = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss", Locale.getDefault());
            SimpleDateFormat formatOut = new SimpleDateFormat("dd.MM.yyyy, hh:mm", Locale.getDefault());
            try {
                Date date = formatIn.parse(getItem(position).getTime());
                dateTxtv.setText(formatOut.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }

    public void setListener(Callback listener) {
        this.listener = listener;
    }

    public interface Callback {
        void onRequestTransport(int id);

        void onAcceptRequest(int requestId);

        void onRefuseRequest(int requestId);

        void onRequestOrder(int orderId);

        void onOpenOrder(int orderId);

        void onOpenTransport(int trasportId);

    }

}
