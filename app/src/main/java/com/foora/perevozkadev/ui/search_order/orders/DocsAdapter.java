package com.foora.perevozkadev.ui.search_order.orders;

import android.support.annotation.NonNull;
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
public class DocsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = DocsAdapter.class.getSimpleName();

    private List<String> items;
    private List<String> visibleItems;

    private int visibleCount = -1;

    public DocsAdapter() {
        items = new ArrayList<>();
        visibleItems = new ArrayList<>();
    }

    public void setItems(List<String> orders) {
        this.items.clear();
        this.items.addAll(orders);
        if (visibleCount == -1)
            visibleCount = orders.size();
        updateVisibleItems();
    }

    public void addItem(String order) {
        this.items.add(order);
        if (visibleCount == -1)
            visibleCount = this.items.size();
        updateVisibleItems();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_doc_type, viewGroup, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        OrderViewHolder orderViewHolder = (OrderViewHolder) viewHolder;

        orderViewHolder.onBind(i);
    }

    @Override
    public int getItemCount() {
        return items.size();
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
    }

    public void collapse() {
        updateVisibleItems();
    }

    public class OrderViewHolder extends BaseViewHolder {

        private TextView text;


        public OrderViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
        }

        @Override
        protected void clear() {

        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            text.setText(getItem(position));
        }

        public String getItem(int pos) {
            return items.get(pos);
        }

    }
}