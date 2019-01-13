package com.github.matvapps.gallery;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Alexandr.
 */
public class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> items;
    private int maxItems;
    private Callback listener;

    public void setItems(List<String> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public GalleryAdapter() {
        items = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(
                viewGroup.getContext()).inflate(R.layout.item_gallery, viewGroup, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        GalleryViewHolder gviewholder = (GalleryViewHolder) viewHolder;
        gviewholder.onBind(i);
    }

    @Override
    public int getItemCount() {
        return maxItems;
    }

    public String getItem(int position) {
        return items.get(position);
    }

    public List<String> getItems() {
        return items;
    }

    public int getMaxItems() {
        return maxItems;
    }

    public void setMaxItems(int maxItems) {
        this.maxItems = maxItems;
        notifyDataSetChanged();
    }

    public Callback getListener() {
        return listener;
    }

    public void setListener(Callback listener) {
        this.listener = listener;
    }

    private class GalleryViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView imageView;
        FrameLayout container;
        TextView textView;

        public GalleryViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
            container = itemView.findViewById(R.id.container);
            textView = itemView.findViewById(R.id.text);
        }

        public void onBind(final int position) {
            if (getItem(position).isEmpty())
                return;
            Picasso.get()
                    .load(getItem(position))
                    .placeholder(R.color.colorPlaceholder)
                    .into(imageView);
            if (position == maxItems - 1) {
                container.setVisibility(View.VISIBLE);
                int num = items.size() - maxItems;
                textView.setText(String.format(Locale.getDefault(),"+%d", num));
            } else {
                container.setVisibility(View.GONE);
            }

            if (listener != null)
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick(position);
                    }
                });

        }
    }

    public interface Callback {
        void onItemClick(int pos);
    }

}
