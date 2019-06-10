package com.foora.perevozkadev.ui.add_drivers_to_order.order_drivers.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.base.BaseViewHolder;
import com.foora.perevozkadev.ui.profile.model.Profile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander Matvienko on 10.05.2019.
 */
public class OrderDriversAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Callback listener;
    private boolean useSelect;
    private List<Profile> items;

    public OrderDriversAdapter(boolean useSelect) {
        this.useSelect = useSelect;
        items = new ArrayList<>();
    }

    public void setItems(List<Profile> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(Profile item) {
        if (!items.contains(item))
            this.items.add(item);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_driver, viewGroup, false);
        return new OrderDriverViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((OrderDriverViewHolder) viewHolder).onBind(i);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public Profile getItem(int position) {
        return items.get(position);
    }

    private class OrderDriverViewHolder extends BaseViewHolder {

        private ImageView userImage;
        private TextView userShortNameTxtv;
        private TextView userName;
        private TextView userPhone;
        private AppCompatCheckBox checkBox;
        private View btnDelete;

        public OrderDriverViewHolder(View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.user_image);
            userName = itemView.findViewById(R.id.user_name);
            userPhone = itemView.findViewById(R.id.user_phone);
            userShortNameTxtv = itemView.findViewById(R.id.short_name);
            checkBox = itemView.findViewById(R.id.checkBox);
            btnDelete = itemView.findViewById(R.id.action_delete);

            checkBox.setClickable(false);
        }

        @Override
        protected void clear() {
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            Profile profile = getItem(position);

            if (!useSelect) checkBox.setVisibility(View.GONE);
            else btnDelete.setVisibility(View.GONE);

            if (listener != null) {
                itemView.setOnClickListener(v -> {
                    if (checkBox.getVisibility() != View.GONE) {
                        checkBox.setChecked(!checkBox.isChecked());
                        listener.onItemSelectChange(profile, checkBox.isChecked());
                    }
                });

                btnDelete.setOnClickListener(v -> {
                    listener.onItemRemove(profile);
                });

            }


            String name = String.format("%s %s", profile.getFirstName(), profile.getLastName());

            userName.setText(name);
            userPhone.setText(profile.getPhone());

            Log.d("OrderDriversAdapter", "onBind: phone " + profile.getPhone());

            if (!name.equals(" ")) {

                StringBuilder shortName = new StringBuilder();

                String[] names = name.split(" ");
                for (int i = 0; i < names.length; i++) {
                    if (i == 2) break;

                    if (names[i].length() > 1)
                        shortName.append(names[i].substring(0, 1));
                }

                if (!shortName.toString().equals("")) {
                    userShortNameTxtv.setText(shortName.toString());
                }

            }
        }
    }

    public Callback getListener() {
        return listener;
    }

    public void setListener(Callback listener) {
        this.listener = listener;
    }

    public interface Callback {

        void onItemSelectChange(Profile profile, boolean selected);

        void onItemRemove(Profile profile);

    }

}
