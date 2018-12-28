package com.foora.perevozkadev.ui.add_employee;

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
public class UserTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String TAG = UserTypeAdapter.class.getSimpleName();

    private List<String> items;
    private int selectedPos = -1;

    public UserTypeAdapter() {
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
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_user_type, viewGroup, false);
        return new UserTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        UserTypeViewHolder userTypeViewHolder = (UserTypeViewHolder) viewHolder;
        userTypeViewHolder.onBind(i);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public String getItem(int pos) {
        return items.get(pos);
    }

    public int getSelectedPos() {
        return selectedPos;
    }

    private class UserTypeViewHolder extends BaseViewHolder {

        private TextView textView;

        public UserTypeViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.text);
        }

        @Override
        protected void clear() {

        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            if (position != selectedPos)
                itemView.setSelected(false);

            textView.setText(getItem(position));
            itemView.setOnClickListener(v -> {
                itemView.setSelected(true);
                selectedPos = position;
                notifyDataSetChanged();
            });

        }
    }
}
