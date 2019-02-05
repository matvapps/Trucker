package com.foora.perevozkadev.ui.change_status;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandr.
 */
public class ChangeStatusAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> items;

    private Callback listener;
    private String status = "";

    public ChangeStatusAdapter() {
        items = new ArrayList<>();
    }

    public void setItems(List<String> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_status, viewGroup, false);
        return new StatusViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        StatusViewHolder statusViewHolder = (StatusViewHolder) viewHolder;
        statusViewHolder.onBind(i);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public String getItem(int pos) {
        return items.get(pos);
    }

    private class StatusViewHolder extends BaseViewHolder {

        TextView item;

        public StatusViewHolder(View itemView) {
            super(itemView);

            item = itemView.findViewById(R.id.item);
        }

        @Override
        protected void clear() {

        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            item.setText(getItem(position));

            if (getItem(position).equals(status))
                item.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.color_app_blue));

            if (listener != null)
                itemView.setOnClickListener(v -> {
                    listener.onItemClick(position, getItem(position));
                });

        }
    }

    public void setListener(Callback listener) {
        this.listener = listener;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public interface Callback {
        void onItemClick(int pos, String item);
    }

}
