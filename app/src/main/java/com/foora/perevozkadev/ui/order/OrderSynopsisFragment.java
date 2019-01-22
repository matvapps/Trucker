package com.foora.perevozkadev.ui.order;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.add_order.model.Place;
import com.google.android.gms.maps.MapView;
import com.perevozka.foora.routedisplayview.RouteDisplayView;
import com.perevozka.foora.routedisplayview.RouteItem;
import com.perevozka.foora.routedisplayview.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class OrderSynopsisFragment extends BottomSheetDialogFragment {

    public static final String TAG = OrderSynopsisFragment.class.getSimpleName();

    private static final String ORDER_KEY = "order_key";

    private RouteDisplayView routeDisplayView;
    private MapView mapView;
    private View rootView;

    public static OrderSynopsisFragment newInstance(Order order) {
        Bundle args = new Bundle();
        OrderSynopsisFragment fragment = new OrderSynopsisFragment();
        args.putSerializable(ORDER_KEY, order);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        Order order = (Order) getArguments().getSerializable(ORDER_KEY);

        //Set the custom view
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_order_route, null);

        routeDisplayView = view.findViewById(R.id.routeDisplayView);
        mapView = view.findViewById(R.id.mapView);
        rootView = view.findViewById(R.id.root_view);
        View v = view.findViewById(R.id.view2);
        View shadow = view.findViewById(R.id.shadow);


        FrameLayout.LayoutParams paramsWith5dpMargin = (FrameLayout.LayoutParams) rootView.getLayoutParams();
        paramsWith5dpMargin.setMargins(0,ViewUtils.dpToPx(5),0,0);
        FrameLayout.LayoutParams paramsNoMargin = (FrameLayout.LayoutParams) rootView.getLayoutParams();
        paramsNoMargin.setMargins(0,0,0,0);

        routeDisplayView.setRoutes(getRouteItemsFromOrder(order));


        dialog.setContentView(view);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();
        ((View) view.getParent()).setBackgroundColor(Color.TRANSPARENT);

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setPeekHeight(ViewUtils.dpToPx(280));
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    String state = "";

                    switch (newState) {
                        case BottomSheetBehavior.STATE_DRAGGING: {
                            state = "DRAGGING";
                            rootView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.order_synopsis_bg));
                            rootView.setLayoutParams(paramsWith5dpMargin);
                            v.setVisibility(View.VISIBLE);
                            shadow.setVisibility(View.VISIBLE);
                            break;
                        }
                        case BottomSheetBehavior.STATE_SETTLING: {
                            state = "SETTLING";
                            break;
                        }
                        case BottomSheetBehavior.STATE_EXPANDED: {
                            state = "EXPANDED";
                            rootView.setBackgroundColor(Color.WHITE);
                            rootView.setLayoutParams(paramsNoMargin);
                            v.setVisibility(GONE);
                            shadow.setVisibility(View.GONE);
                            break;
                        }
                        case BottomSheetBehavior.STATE_COLLAPSED: {
                            state = "COLLAPSED";
                            rootView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.order_synopsis_bg));
                            rootView.setLayoutParams(paramsWith5dpMargin);
                            v.setVisibility(View.VISIBLE);
                            shadow.setVisibility(View.VISIBLE);
                            break;
                        }
                        case BottomSheetBehavior.STATE_HIDDEN: {
                            dismiss();
                            state = "HIDDEN";
                            break;
                        }
                    }

                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            });
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0f;
        windowParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(windowParams);
    }

    private List<RouteItem> getRouteItemsFromOrder(Order order) {
        List<RouteItem> result = new ArrayList<>();
        List<Place> loadingPlaces = order.getLoadingPlaces();
        List<Place> unloadingPlaces = order.getUnloadingPlaces();

//        String[] firstPlace = order.getLoadingPlaces().get(0).getName().split(",");
//        String[] secondPlace = order.getUnloadingPlaces().get(0).getName().split(",");
//
//        String firstCity;
//        String secondCity;
//
//        if (firstPlace.length <= 3) {
//            firstCity = firstPlace[0].replaceAll("\\s", "");
//            secondCity = secondPlace[0].replaceAll("\\s", "");
//        } else {
//            firstCity = firstPlace[2].replaceAll("\\s", "");
//            secondCity = secondPlace[2].replaceAll("\\s", "");
//        }
//
//
//        result.add(new RouteItem(order.getLoadingDate(), firstCity, ""));
//        result.add(new RouteItem(order.getUnloadingDate(), secondCity, ""));


        for (int i = 0; i < loadingPlaces.size(); i++) {
            Place place = loadingPlaces.get(i);
            String name = place.getName().split(",")[0];

            if (i == 0) {
                result.add(new RouteItem(order.getLoadingDate(), name, ""));
            } else {
                result.add(new RouteItem("", name, ""));
            }
        }

        for (int i = 0; i < unloadingPlaces.size(); i++) {
            Place place = unloadingPlaces.get(i);
            String name = place.getName().split(",")[0];

            if (i == unloadingPlaces.size() - 1) {
                result.add(new RouteItem(order.getUnloadingDate(), name, ""));
            } else {
                result.add(new RouteItem("", name, ""));
            }
        }


        return result;
    }

}
