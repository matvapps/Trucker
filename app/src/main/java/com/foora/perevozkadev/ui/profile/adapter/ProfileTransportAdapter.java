package com.foora.perevozkadev.ui.profile.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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

    private List<Integer> selectedItemPositions;

    private int visibleCount = -1;
    private boolean useSelection = false;
    private int maxSelectedItems = -1;

    private Callback listener;

    public ProfileTransportAdapter() {
        items = new ArrayList<>();
        visibleItems = new ArrayList<>();
        selectedItemPositions = new ArrayList<>();
    }

    public void setItems(List<Transport> transports) {
        this.items.clear();
        this.items.addAll(transports);
        if (visibleCount == -1) {
            visibleCount = transports.size();
        }
        updateVisibleItems();
    }

    public void addItem(Transport transport) {
        this.items.add(transport);
        if (visibleCount == -1) {
            visibleCount = items.size() + 1;
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
        private CheckBox checkBox;

        public TransportViewHolder(View itemView) {
            super(itemView);

            transportName = itemView.findViewById(R.id.transport_name);
            transportNum = itemView.findViewById(R.id.transport_num);
            transportType = itemView.findViewById(R.id.transport_type);
            settingsBtn = itemView.findViewById(R.id.settings);
            checkBox = itemView.findViewById(R.id.checkBox);

        }

        @Override
        protected void clear() {

        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            Transport transport = getItem(position);
            if (isUseSelection()) {
                checkBox.setVisibility(View.VISIBLE);
                if (selectedItemPositions.contains(position)) {
                    checkBox.setSelected(true);
                }
            } else
                checkBox.setVisibility(View.GONE);

            itemView.setOnClickListener(v -> {

//                if (isUseSelection()) {
//                    checkBox.setChecked(!checkBox.isChecked());
////                    if (maxSelectedItems == -1 || selectedItemPositions.size() < maxSelectedItems) {
//
//                }
//
                if (isUseSelection()) {

                    if (maxSelectedItems == -1 || selectedItemPositions.size() < maxSelectedItems) {
                        checkBox.setChecked(!checkBox.isChecked());
                        if (checkBox.isChecked()) {
                            selectedItemPositions.add((Integer) position);
                        } else {
                            selectedItemPositions.remove((Integer) position);
                        }
                    } else {
                        checkBox.setChecked(false);
                        selectedItemPositions.remove((Integer) position);
                    }

                }


                if (listener != null)
                    listener.onClick(position, transport);
            });


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

    public List<Transport> getSelectedItems() {
        List<Transport> selectedTransports = new ArrayList<>();
        for (int pos : selectedItemPositions) {
            selectedTransports.add(getItem(pos));
        }

        return selectedTransports;
    }

    public boolean isUseSelection() {
        return useSelection;
    }

    public void setUseSelection(boolean useSelection) {
        this.useSelection = useSelection;
    }

    public int getMaxSelectedItems() {
        return maxSelectedItems;
    }

    public void setMaxSelectedItems(int maxSelectedItems) {
        this.maxSelectedItems = maxSelectedItems;
    }
}
