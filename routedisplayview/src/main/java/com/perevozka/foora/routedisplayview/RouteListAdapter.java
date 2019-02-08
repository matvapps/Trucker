package com.perevozka.foora.routedisplayview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandr.
 */
public class RouteListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<RouteItem> routes;
    private boolean displayAllItems = true;

    public RouteListAdapter() {
        routes = new ArrayList<>();
    }

    public void setItems(List<RouteItem> items) {
        routes.clear();
        routes.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_route, viewGroup, false);
        return new RouteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        RouteViewHolder routeViewHolder = (RouteViewHolder) viewHolder;
        routeViewHolder.onBind(i);
    }

    public RouteItem getItem(int pos) {
        return routes.get(pos);
    }

    @Override
    public int getItemCount() {
        return routes.size();
    }

    public boolean isDisplayAllItems() {
        return displayAllItems;
    }

    public void displayAllItems(boolean displayAll) {
        this.displayAllItems = displayAll;
        updateView();
    }

    private void updateView() {
        if (!displayAllItems) {
            for (int i = 1; i < routes.size() - 1; i++) {
                routes.remove(i);
            }
        }
        notifyDataSetChanged();
    }

    class RouteViewHolder extends RecyclerView.ViewHolder {

        private RouteDrawable routeDrawable;
        private TextView textDate;
        private TextView textCity;
        private TextView textCountry;

        public RouteViewHolder(@NonNull View itemView) {
            super(itemView);

            routeDrawable = itemView.findViewById(R.id.route_drawable);
            textDate = itemView.findViewById(R.id.txt_date);
            textCity = itemView.findViewById(R.id.txt_city);
            textCountry = itemView.findViewById(R.id.txt_country);

        }

        public void onBind(int pos) {
            RouteItem routeItem = getItem(pos);

            if (pos == 0) {
                routeDrawable.setType(RouteDrawable.Type.FIRST_ROUTE);
            } else if (pos == getItemCount() - 1) {
                routeDrawable.setType(RouteDrawable.Type.LAST_ROUTE);
            } else {
                routeDrawable.setType(RouteDrawable.Type.BASE_ROUTE);
            }

            if (!routeItem.getDate().equals("")) {
                textDate.setVisibility(View.VISIBLE);
                textDate.setText(routeItem.getDate());
            } else
                textDate.setVisibility(View.GONE);
            textCity.setText(routeItem.getCity());
            textCountry.setText(Utils.getCountryCode(routeItem.getCountry()));

        }

    }

}
