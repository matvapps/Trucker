package com.foora.perevozkadev.ui.add_order.route;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.add_order.route.model.RouteItem;
import com.foora.perevozkadev.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import foora.perevozka.com.choosecityview.ChooseCityView;

/**
 * Created by Alexander Matvienko on 13.12.2018.
 */
public class RouteListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String TAG = RouteListAdapter.class.getSimpleName();

    private List<RouteItem> items;
    private Context context;
    private Callback listener;


    public RouteListAdapter(Context context) {
        items = new ArrayList<>();
        this.context = context;
    }

    public void addItem(RouteItem.Type type) {
//        ChooseCityView view = new ChooseCityView(context);
//
//        switch (type) {
//            case LOADING_PLACE:
//                view.setTitle("Место погрузки");
//                break;
//            case UNLOADING_PLACE:
//                view.setTitle("Место выгрузки");
//                break;
//        }
//
//        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        view.setLayoutParams(layoutParams);

        items.add(new RouteItem(type));
//        notifyDataSetChanged();
        notifyItemInserted(items.size() - 1);

    }

//    public View getView(int pos) {
//
//    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v;

        if (i == FOOTER_VIEW) {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_routes_footer, viewGroup, false);

            FooterViewHolder vh = new FooterViewHolder(v);

            return vh;
        }

        ChooseCityView view = new ChooseCityView(viewGroup.getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);

//        items.set(i, new RouteItem(view, items.get(i).getType()));

        return new RouteViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == items.size()) {
            // This is where we'll add footer.
            return FOOTER_VIEW;
        }

        return super.getItemViewType(position);
    }

    public RouteItem getItem(int pos) {
        if (pos == items.size())
            return null;
        return items.get(pos);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

        try {
            if (holder instanceof RouteViewHolder) {
                RouteViewHolder vh = (RouteViewHolder) holder;
                ChooseCityView view = (ChooseCityView) vh.itemView;

                view.setListener(new ChooseCityView.Callback() {
                    @Override
                    public void onCountryChanged(String name) {
                        if (listener != null)
                            listener.onCountryChange(i, getItem(i).getType(), name);
                    }

                    @Override
                    public void onRegionChanged(String name) {
                        if (listener != null)
                            listener.onRegionChange(i, getItem(i).getType(), name);
                    }

                    @Override
                    public void onCityChanged(String name) {
                        if (listener != null)
                            listener.onCityChange(i, getItem(i).getType(), name);
                    }
                });

                vh.onBind(i);
            } else if (holder instanceof FooterViewHolder) {
                FooterViewHolder vh = (FooterViewHolder) holder;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return items.size() + 1;
    }


    private static final int FOOTER_VIEW = 1;


    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> {
                addItem(RouteItem.Type.LOADING_PLACE);
                if (listener != null)
                    listener.onAddNewItem();
            });

        }
    }


    public interface Callback {
        void onAddNewItem();
        void onCountryChange(int id, RouteItem.Type routeType, String country);
        void onRegionChange(int id, RouteItem.Type routeType, String region);
        void onCityChange(int id, RouteItem.Type routeType, String city);
    }


    class RouteViewHolder extends BaseViewHolder {

        public RouteViewHolder(View itemView) {
            super(itemView);

        }

        @Override
        protected void clear() {

        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            RouteItem.Type type = getItem(position).getType();

            switch (type) {
                case LOADING_PLACE:
                    ((ChooseCityView) itemView).setTitle("Место погрузки");
                    break;
                case UNLOADING_PLACE:
                    ((ChooseCityView) itemView).setTitle("Место выгрузки");
                    break;
            }
        }
    }

    public List<RouteItem> getItems() {
        return items;
    }

    public Callback getListener() {
        return listener;
    }

    public void setListener(Callback listener) {
        this.listener = listener;
    }

}
