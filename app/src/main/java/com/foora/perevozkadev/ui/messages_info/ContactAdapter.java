package com.foora.perevozkadev.ui.messages_info;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander Matvienko on 04.02.2019.
 */
public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> contacts;

    public ContactAdapter() {
        contacts = new ArrayList<>();
    }

    public void setItems(List<String> items) {
        contacts.clear();
        contacts.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(String item) {
        contacts.add(item);
        notifyItemInserted(contacts.size() - 1);
    }

    public String getItem(int pos) {
        return contacts.get(pos);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_message_contact, viewGroup, false);
        return new ContactViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ContactViewHolder contactViewHolder = (ContactViewHolder) viewHolder;
        contactViewHolder.onBind(i);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    private class ContactViewHolder extends BaseViewHolder {
        ImageView contactImage;
        TextView contactData;

        public ContactViewHolder(View itemView) {
            super(itemView);

            contactImage = itemView.findViewById(R.id.contact_image);
            contactData = itemView.findViewById(R.id.contact_data);

        }

        @Override
        protected void clear() {

        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            contactData.setText(getItem(position));

        }
    }

}
