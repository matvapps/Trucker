package com.foora.perevozkadev.ui.profile.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.base.BaseViewHolder;
import com.foora.perevozkadev.ui.my_transport.model.Transport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandr.
 */
public class ProfileTransportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String TAG = ProfileTransportAdapter.class.getSimpleName();

    private List<Transport> items;
    private List<Transport> visibleItems;

    private int visibleCount = -1;

    private Callback listener;

    public ProfileTransportAdapter() {
        items = new ArrayList<>();
        visibleItems = new ArrayList<>();
    }

    public void setItems(List<Transport> transports) {
        this.items.clear();
        this.items.addAll(transports);
        if (visibleCount == -1) {
            visibleCount = transports.size();
        }
        updateVisibleItems();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_profile_transport, viewGroup, false);
        return new TransportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        TransportViewHolder partnersViewHolder = (TransportViewHolder) viewHolder;
        partnersViewHolder.onBind(i);
    }

    @Override
    public int getItemCount() {
        return visibleItems.size();
    }

    public Transport getItem(int pos) {
        return visibleItems.get(pos);
    }

    public int getVisibleCount() {
        return visibleCount;
    }

    public void setVisibleCount(int visibleCount) {
        this.visibleCount = visibleCount;
    }

    private void updateVisibleItems() {
        visibleItems.clear();
        if (items.size() <= visibleCount) {
            visibleItems.addAll(items);
        } else {
            for (int i = 0; i < visibleCount; i++) {
                visibleItems.add(items.get(i));
            }
        }
        notifyDataSetChanged();
    }

    public void showAll() {
        visibleItems.clear();
        visibleItems.addAll(items);
        notifyDataSetChanged();
        //        visibleCount = items.size();
    }

    public void collapse() {
        updateVisibleItems();
    }

    class TransportViewHolder extends BaseViewHolder {

        private TextView transportName;
        private TextView transportNum;
        private TextView transportType;
        private ImageButton settingsBtn;

        public TransportViewHolder(View itemView) {
            super(itemView);

            transportName = itemView.findViewById(R.id.transport_name);
            transportNum = itemView.findViewById(R.id.transport_num);
            transportType = itemView.findViewById(R.id.transport_type);
            settingsBtn = itemView.findViewById(R.id.settings);

        }

        @Override
        protected void clear() {

        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            Transport transport = getItem(position);

            if (listener != null)
                itemView.setOnClickListener(v -> listener.onClick(position, transport));

            transportName.setText(transport.getModel());
            transportType.setText(transport.getType());

        }
    }

    public Callback getListener() {
        return listener;
    }

    public void setListener(Callback listener) {
        this.listener = listener;
    }

    public interface Callback {
        void onClick(int pos, Transport transport);
    }

}
