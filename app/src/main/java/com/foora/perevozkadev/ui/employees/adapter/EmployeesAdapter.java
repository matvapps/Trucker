package com.foora.perevozkadev.ui.employees.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
 * Created by Alexandr.
 */
public class EmployeesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String TAG = EmployeesAdapter.class.getSimpleName();

    private List<Profile> items;
    private Callback listener;

    public EmployeesAdapter() {
        items = new ArrayList<>();
    }

    public void setItems(List<Profile> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_employee, viewGroup, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        EmployeeViewHolder employeeViewHolder = (EmployeeViewHolder) viewHolder;
        employeeViewHolder.onBind(i);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public Profile getItem(int pos) {
        return items.get(pos);
    }

    private class EmployeeViewHolder extends BaseViewHolder {

        private ImageView userImage;
        private TextView userName;
        private TextView userPhone;

        public EmployeeViewHolder(View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.user_image);
            userName = itemView.findViewById(R.id.user_name);
            userPhone = itemView.findViewById(R.id.user_phone);
        }

        @Override
        protected void clear() {
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            Profile profile = getItem(position);

            if (listener != null)
                itemView.setOnClickListener(v -> listener.onClick(profile));

            userName.setText(String.format("%s %s", profile.getFirstName(), profile.getLastName()));
            userPhone.setText(profile.getPhone());
        }
    }

    public Callback getListener() {
        return listener;
    }

    public void setListener(Callback listener) {
        this.listener = listener;
    }

    public interface Callback {
        void onClick(Profile profile);
    }

}
