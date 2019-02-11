package com.foora.perevozkadev.ui.search_order.orders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.base.BaseViewHolder;
import com.perevozka.foora.routedisplayview.RouteDisplayView;
import com.perevozka.foora.routedisplayview.RouteItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandr.
 */
public class OrdersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Order> items;
    private List<Order> visibleItems;

    private int visibleCount = -1;
    private Callback listener;

    public OrdersAdapter() {
        items = new ArrayList<>();
        visibleItems = new ArrayList<>();
    }

    public void setItems(List<Order> orders) {
        this.items.clear();
        this.items.addAll(orders);
        if (visibleCount == -1)
            visibleCount = orders.size();
        updateVisibleItems();
    }

    public void addItem(Order order) {
        this.items.add(order);
        if (visibleCount == -1)
            visibleCount = this.items.size();
        updateVisibleItems();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_order, viewGroup, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        OrderViewHolder orderViewHolder = (OrderViewHolder) viewHolder;

        orderViewHolder.onBind(i);
    }

    @Override
    public int getItemCount() {
        return items.size();
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

    public class OrderViewHolder extends BaseViewHolder {

        private RouteDisplayView routeDisplayView;
        private TextView transportType;
        private TextView cargoType;
        private TextView carQuant;
        private TextView cargoParam;
        private TextView cost;
        private TextView costType;


        public OrderViewHolder(View itemView) {
            super(itemView);
            routeDisplayView = itemView.findViewById(R.id.route_display_view);
            transportType = itemView.findViewById(R.id.transport_type);
            cargoType = itemView.findViewById(R.id.cargo_type);
            carQuant = itemView.findViewById(R.id.transport_quantity);
            cargoParam = itemView.findViewById(R.id.cargo_size);
            cost = itemView.findViewById(R.id.cost);
            costType = itemView.findViewById(R.id.cost_type);
        }

        @Override
        protected void clear() {

        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            if (listener != null)
                itemView.setOnClickListener(v -> listener.onClick(getItem(position)));

            Order item = getItem(position);

//            if (item.getCurrency() == null) {
//                List<RouteItem> routeItems = new ArrayList<>();
//                routeItems.add(new RouteItem("2018-11-12", "Запорожье", "UA"));
//                routeItems.add(new RouteItem("2018-11-12", "Киев", "UA"));
//                routeDisplayView.setRoutes(routeItems);
//
//                return;
//            }
            routeDisplayView.setRoutes(getRouteItemsFromOrder(item));

            transportType.setText(item.getTransportType());
            cargoType.setText(item.getCargo());
            carQuant.setText(String.valueOf(item.getCarQuantity()));
            cargoParam.setText(item.getSize());
            cost.setText(String.format("%s%s", String.valueOf(item.getCost()), item.getCurrency()));
            costType.setText(String.format("%s %s", item.getPaymentType1(), item.getPaymentType2()));

        }

        private List<RouteItem> getRouteItemsFromOrder(Order order) {
            List<RouteItem> result = new ArrayList<>();

            String[] firstPlace = order.getLoadingPlaces().get(0).getName().split(",");
            String[] secondPlace = order.getUnloadingPlaces().get(0).getName().split(",");

            String firstCity;
            String firstCountry = "";
            String secondCity;
            String secondCountry = "";

            int fIndex = firstPlace.length - 1;
            int sIndex = secondPlace.length - 1;



            firstCity = firstPlace[0].replaceAll("\\s", "");
            secondCity = secondPlace[0].replaceAll("\\s", "");
            firstCountry = firstPlace[fIndex].replaceAll("\\s", "");
            secondCountry = secondPlace[sIndex].replaceAll("\\s", "");


            Log.d("OrdersAdapter", "getRouteItemsFromOrder: first " + firstCountry);
            Log.d("OrdersAdapter", "getRouteItemsFromOrder: second " + secondCountry);
//            if (firstPlace.length < 3) {
//                firstCity = firstPlace[0].replaceAll("\\s", "");
//                secondCity = secondPlace[0].replaceAll("\\s", "");
//            } else {
//                firstCity = firstPlace[0].replaceAll("\\s", "");
//                secondCity = secondPlace[0].replaceAll("\\s", "");
//                firstCountry = firstPlace[2].replaceAll("\\s", "");
//                secondCountry = secondPlace[2].replaceAll("\\s", "");
//            }


            result.add(new RouteItem(order.getLoadingDate(), firstCity, firstCountry));
            result.add(new RouteItem("", secondCity, secondCountry));

            return result;
        }

        public Order getItem(int pos) {
            return items.get(pos);
        }

    }

    public Callback getListener() {
        return listener;
    }

    public void setListener(Callback listener) {
        this.listener = listener;
    }

    public interface Callback {
        void onClick(Order order);
    }

}
