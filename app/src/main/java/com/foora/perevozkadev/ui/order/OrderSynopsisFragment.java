package com.foora.perevozkadev.ui.order;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.add_order.model.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.perevozka.foora.routedisplayview.RouteDisplayView;
import com.perevozka.foora.routedisplayview.RouteItem;
import com.perevozka.foora.routedisplayview.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class OrderSynopsisFragment extends BottomSheetDialogFragment implements OnMapReadyCallback {

    public static final String TAG = OrderSynopsisFragment.class.getSimpleName();

    private static final String ORDER_KEY = "order_key";

    private RouteDisplayView routeDisplayView;
    private MapView mapView;
    private View rootView;
    private View btnBack;
    private View toolbar;

    private GoogleMap googleMap;

    public static OrderSynopsisFragment newInstance(Order order) {
        Bundle args = new Bundle();
        OrderSynopsisFragment fragment = new OrderSynopsisFragment();
        args.putSerializable(ORDER_KEY, order);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapsInitializer.initialize(getContext());
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Order order = (Order) getArguments().getSerializable(ORDER_KEY);

        //Set the custom view
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_order_route, null);

        routeDisplayView = view.findViewById(R.id.routeDisplayView);
        rootView = view.findViewById(R.id.root_view);
        toolbar = view.findViewById(R.id.toolbar);
        btnBack = view.findViewById(R.id.btn_back);
        View v = view.findViewById(R.id.view2);
        View shadow = view.findViewById(R.id.shadow);
        mapView = view.findViewById(R.id.mapView);

        mapView.onCreate(savedInstanceState);

        routeDisplayView.setOnTouchListener((v1, event) -> false);


        FrameLayout.LayoutParams paramsWith5dpMargin = (FrameLayout.LayoutParams) rootView.getLayoutParams();
        paramsWith5dpMargin.setMargins(0, ViewUtils.dpToPx(5), 0, 0);
        FrameLayout.LayoutParams paramsNoMargin = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        routeDisplayView.setRoutes(getRouteItemsFromOrder(order));


        getDialog().setContentView(view);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();
        ((View) view.getParent()).setBackgroundColor(Color.TRANSPARENT);


        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setPeekHeight(ViewUtils.dpToPx(280));
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @SuppressLint("ClickableViewAccessibility")
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
                            hideToolbar();
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
                            showToolbar();
                            break;
                        }
                        case BottomSheetBehavior.STATE_COLLAPSED: {
                            state = "COLLAPSED";
                            rootView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.order_synopsis_bg));
                            rootView.setLayoutParams(paramsWith5dpMargin);
                            v.setVisibility(View.VISIBLE);
                            shadow.setVisibility(View.VISIBLE);
                            hideToolbar();
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

        return super.onCreateView(inflater, container, savedInstanceState);


    }

    private void showToolbar() {
        toolbar.setVisibility(View.VISIBLE);
    }

    private void hideToolbar() {
        toolbar.setVisibility(View.GONE);
    }

    public void show(FragmentManager fragmentManager, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment prevFragment = fragmentManager.findFragmentByTag(tag);
        if (prevFragment != null) {
            transaction.remove(prevFragment);
        }
        transaction.addToBackStack(null);
        show(transaction, tag);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
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


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setMinZoomPreference(12);
        LatLng ny = new LatLng(40.7143528, -74.0059731);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(ny));
    }
}
