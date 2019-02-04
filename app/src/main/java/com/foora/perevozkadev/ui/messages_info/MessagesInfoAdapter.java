package com.foora.perevozkadev.ui.messages_info;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.data.network.model.Action;
import com.foora.perevozkadev.data.network.model.RequestInfo;
import com.foora.perevozkadev.ui.base.BaseViewHolder;
import com.foora.perevozkadev.ui.my_transport.model.Transport;
import com.foora.perevozkadev.ui.profile.adapter.ProfileTransportAdapter;
import com.foora.perevozkadev.utils.ViewUtils;
import com.foora.perevozkadev.utils.custom.ItemSpacingDecoration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Alexander Matvienko on 04.02.2019.
 */
public class MessagesInfoAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private RequestInfo requestInfo;
    private List<Action> actions;
    private ProfileTransportAdapter transportAdapter;
    private ContactAdapter contactAdapter;

    private Callback listener;

    public MessagesInfoAdapter() {
        this.actions = new ArrayList<>();
    }

    public void setRequestInfo(RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
        this.actions = requestInfo.getActions();
    }

    public Action getItem(int pos) {
        return actions.get(pos);
    }

    public void addTransport(Transport transport) {
        transportAdapter.addItem(transport);
        if (transportAdapter.getItemCount() == requestInfo.getTransports().size())
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
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder, int i) {
        viewHolder.onBind(i);
    }

    @Override
    public int getItemCount() {
        return actions.size();
    }

    private class ActionRequestViewHolder extends BaseViewHolder {

        RecyclerView transportList;
        Button btnRefuse;
        Button btnAccept;

        public ActionRequestViewHolder(View itemView) {
            super(itemView);

            transportList = itemView.findViewById(R.id.transport_list);
            btnRefuse = itemView.findViewById(R.id.btn_refuse);
            btnAccept = itemView.findViewById(R.id.btn_accept);

            transportAdapter = new ProfileTransportAdapter();
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

            if (getItemCount() > 1) {
                btnRefuse.setVisibility(View.GONE);
                btnAccept.setVisibility(View.GONE);
            }

            btnRefuse.setOnClickListener(v -> {
                if (listener != null)
                    listener.onRefuseRequest(requestInfo.getId());
            });

            btnAccept.setOnClickListener(v -> {
                if (listener != null)
                    listener.onAcceptRequest(requestInfo.getId());
            });

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


            if (listener != null) {
//                listener.onRequestProfile(requestInfo.);
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

        void onRequestProfile(int userId);
    }

}
