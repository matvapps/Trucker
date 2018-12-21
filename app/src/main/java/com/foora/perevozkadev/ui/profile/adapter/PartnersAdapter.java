package com.foora.perevozkadev.ui.profile.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.base.BaseViewHolder;
import com.foora.perevozkadev.ui.profile.model.Partner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandr.
 */
public class PartnersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String TAG = PartnersAdapter.class.getSimpleName();

    private List<Partner> items;
    private List<Partner> visibleItems;

    private int visibleCount = -1;

    public PartnersAdapter() {
        items = new ArrayList<>();
        visibleItems = new ArrayList<>();
    }

    public void setItems(List<Partner> partners) {
        this.items.clear();
        this.items.addAll(partners);
        if (visibleCount == -1)
            visibleCount = partners.size();
        updateVisibleItems();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_partners, viewGroup, false);
        return new PartnersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        PartnersViewHolder partnersViewHolder = (PartnersViewHolder) viewHolder;
        partnersViewHolder.onBind(i);
    }

    @Override
    public int getItemCount() {
        return visibleItems.size();
    }

    public Partner getItem(int pos) {
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
    }

    public void collapse() {
        updateVisibleItems();
    }

    class PartnersViewHolder extends BaseViewHolder {

        private ImageView image;
        private TextView name;
        private TextView description;

        public PartnersViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.descr);

        }

        @Override
        protected void clear() {

        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
        }
    }

}
