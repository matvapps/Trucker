package com.foora.perevozkadev.ui.my_transport.transport;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.base.BaseViewHolder;
import com.foora.perevozkadev.ui.my_transport.model.Transport;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandr.
 */
public class TransportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String TAG = TransportAdapter.class.getSimpleName();

    private List<Transport> items;
    private List<Transport> visibleItems;

    private int visibleCount = -1;
    private boolean isArchive;
    private Callback listener;

    public TransportAdapter(boolean isArchive) {
        items = new ArrayList<>();
        visibleItems = new ArrayList<>();
        this.isArchive = isArchive;
    }

    public void setItems(List<Transport> transports) {
        this.items.clear();
        this.items.addAll(transports);
        if (visibleCount == -1) {
            visibleCount = transports.size();
        }

        updateVisibleItems();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_transport, viewGroup, false);
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

        private ImageView bgImage;
        private TextView transportName;
        private TextView transportNum;
        private TextView transportType;
        private ImageButton settingsBtn;

        public TransportViewHolder(View itemView) {
            super(itemView);

            bgImage = itemView.findViewById(R.id.bg_image);
            transportName = itemView.findViewById(R.id.transport_name);
            transportNum = itemView.findViewById(R.id.transport_num);
            transportType = itemView.findViewById(R.id.transport_type);
            settingsBtn = itemView.findViewById(R.id.settings);

        }

        @Override
        protected void clear() {

        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            Transport transport = getItem(position);

            if (listener != null)
                itemView.setOnClickListener(v -> listener.onClick(position, transport));

            if (transport.getPhotos().size() >= 1)
                Picasso.get().load("http://dev.perevozka.me" + transport.getPhotos().get(0))
                        .into(bgImage);

            transportName.setText(transport.getModel());
            transportNum.setText(transport.getPassportNum());
            transportType.setText(transport.getType());

            if (listener != null) {
                settingsBtn.setVisibility(View.VISIBLE);

                settingsBtn.setOnClickListener(v -> {
                    PopupMenu popupMenu = new PopupMenu(itemView.getContext(), itemView, Gravity.END);
                    int menuId = isArchive ? R.menu.archive_context_menu : R.menu.transport_context_menu ;

                    popupMenu.getMenuInflater().inflate(menuId, popupMenu.getMenu());

                    for (int i = 0; i < popupMenu.getMenu().size(); i++) {
                        MenuItem item = popupMenu.getMenu().getItem(i);
                        String title = "Редактировать";

                        if (!isArchive) {
                            if (i == 1)
                                title = "Добавить в архив";
                        } else {
                            if (i == 0)
                                title = "Восстановить из архива";
                        }

                        SpannableString s = new SpannableString(title);
                        s.setSpan(new ForegroundColorSpan(Color.DKGRAY), 0, s.length(), 0);
                        item.setTitle(s);
                    }


                    popupMenu.setOnMenuItemClickListener(item -> {
                        switch (item.getItemId()) {
                            case R.id.action_edit:
                                listener.onActionEdit(position, getItem(position));
                                return true;
                            case R.id.action_add_to_archive:
                                listener.onActionAddToArchive(position, getItem(position));
                                return true;
                            case R.id.action_restore_from_archive:
                                listener.onActionRestoreFromArchive(position, getItem(position));
                                return true;
                            default:
                                return false;
                        }
                    });

                    popupMenu.show();
                });
            } else {
                settingsBtn.setVisibility(View.GONE);
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
        void onClick(int pos, Transport transport);

        void onActionEdit(int pos, Transport transport);

        void onActionAddToArchive(int pos, Transport transport);

        void onActionRestoreFromArchive(int pos, Transport transport);
    }

}
