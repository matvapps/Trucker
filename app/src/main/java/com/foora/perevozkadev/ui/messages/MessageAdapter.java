package com.foora.perevozkadev.ui.messages;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.base.BaseViewHolder;
import com.foora.perevozkadev.ui.messages.model.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandr.
 */
public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Message> items;
    private Callback listener;

    public MessageAdapter() {
        items = new ArrayList<>();
    }

    public void setItems(List<Message> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(Message item) {
        this.items.add(item);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(
                viewGroup.getContext()).inflate(R.layout.item_message, viewGroup, false);
        return new MessageViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MessageViewHolder messageViewHolder = (MessageViewHolder) viewHolder;
        messageViewHolder.onBind(i);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public Message getItem(int position) {
        return items.get(position);
    }

    private class MessageViewHolder extends BaseViewHolder {

        TextView nameTxtv;
        TextView routeTxtv;
        TextView messageTxtv;
        TextView dateTxtv;

        public MessageViewHolder(View itemView) {
            super(itemView);
            nameTxtv = itemView.findViewById(R.id.name);
            routeTxtv = itemView.findViewById(R.id.title);
            messageTxtv = itemView.findViewById(R.id.message);
            dateTxtv = itemView.findViewById(R.id.date);
        }

        @Override
        protected void clear() {

        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            itemView.setOnClickListener(v -> {
                if (listener != null)
                    listener.onItemClick(position, getItem(position));
            });

            nameTxtv.setText(getItem(position).getName().toUpperCase());
            routeTxtv.setText(getItem(position).getRoute());
            messageTxtv.setText(getItem(position).getMessage());
            dateTxtv.setText(getItem(position).getDate());

        }
    }

    public void setListener(Callback listener) {
        this.listener = listener;
    }

    public interface Callback {
        void onItemClick(int pos, Message message);
    }

}
