package com.foora.perevozkadev.ui.order;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.add_order.model.Place;
import com.foora.perevozkadev.ui.choose_transport.ChooseTransportActivity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;
import com.perevozka.foora.routedisplayview.RouteDisplayView;
import com.perevozka.foora.routedisplayview.RouteItem;
import com.perevozka.foora.routedisplayview.ViewUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.view.View.GONE;

public class OrderFragment extends BottomSheetDialogFragment implements OnMapReadyCallback {

    public static final String TAG = OrderFragment.class.getSimpleName();

    private static final String ORDER_KEY = "order_key";


    private RouteDisplayView routeDisplayView;
    private MapView mapView;
    private View rootView;
    private View btnBack;
    private View toolbar;
    private Button btnRespond;
    private TextView distanceTxtv;
    private TextView timeTxtv;
    private TextView carQuantityTxtv;
    private TextView transportTypeTxtv;
    private TextView cargoMassTxtv;
    private TextView volumeTxtv;
    private TextView heightTxtv;
    private TextView widthTextv;
    private TextView depthTxtv;
    private TextView priceForKmTxtv;
    private TextView paymentType;
    private TextView costTxtv;
    private TextView additionallyTxtv;

    private List<com.google.maps.model.LatLng> places;

    private Gson gson;
    private GoogleMap googleMap;
    private Order order;

    public static OrderFragment newInstance(String orderJson) {
        Bundle args = new Bundle();
        OrderFragment fragment = new OrderFragment();
        args.putString(ORDER_KEY, orderJson);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        gson = new Gson();
        places = new ArrayList<>();

        if (order == null)
            order = gson.fromJson(getArguments().getString(ORDER_KEY), Order.class);

        //Set the custom view
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_order_route, null);

        routeDisplayView = view.findViewById(R.id.routeDisplayView);
        rootView = view.findViewById(R.id.root_view);
        toolbar = view.findViewById(R.id.toolbar);
        btnBack = view.findViewById(R.id.btn_back);
        View v = view.findViewById(R.id.view2);
        View shadow = view.findViewById(R.id.shadow);
        mapView = view.findViewById(R.id.mapView);
        distanceTxtv = view.findViewById(R.id.distance_txtv);
        btnRespond = view.findViewById(R.id.btn_respond);
        timeTxtv = view.findViewById(R.id.time_txtv);
        carQuantityTxtv = view.findViewById(R.id.car_quantity_txtv);
        transportTypeTxtv = view.findViewById(R.id.transport_type_txtv);
        cargoMassTxtv = view.findViewById(R.id.mass_txtv);
        volumeTxtv = view.findViewById(R.id.volume_txtv);
        heightTxtv = view.findViewById(R.id.height_txtv);
        widthTextv = view.findViewById(R.id.width_txtv);
        depthTxtv = view.findViewById(R.id.depth_txtv);
        priceForKmTxtv = view.findViewById(R.id.km_cost_txtv);
        paymentType = view.findViewById(R.id.payment_txtv);
        costTxtv = view.findViewById(R.id.cost_txtv);
        additionallyTxtv = view.findViewById(R.id.additonally_txtv);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        btnBack.setOnClickListener(v1 -> dismiss());
        btnRespond.setOnClickListener(v1 -> {
            ChooseTransportActivity.start(getActivity(), order.getCarQuantity(), order.getId());
            dismiss();
        });
//        mapView.onCreate(savedInstanceState);

        routeDisplayView.setOnTouchListener((v1, event) -> false);


        FrameLayout.LayoutParams paramsWith5dpMargin = (FrameLayout.LayoutParams) rootView.getLayoutParams();
        paramsWith5dpMargin.setMargins(0, ViewUtils.dpToPx(5), 0, 0);
        FrameLayout.LayoutParams paramsNoMargin = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        routeDisplayView.setRoutes(getRouteItemsFromOrder(order));
        carQuantityTxtv.setText(String.format(Locale.getDefault(), "%d шт", order.getCarQuantity()));
        transportTypeTxtv.setText(order.getTransportType());

        additionallyTxtv.setText(order.getAdditionally());
        cargoMassTxtv.setText(String.format(Locale.getDefault(), "%.2f кг", order.getWeightTo()));
        volumeTxtv.setText(String.format(Locale.getDefault(), "%.2f м³", order.getVolumeTo()));

        String[] size = order.getSize().split("x");
        widthTextv.setText(String.format(Locale.getDefault(), "%s м", size[0]));
        heightTxtv.setText(String.format(Locale.getDefault(), "%s м", size[1]));
        depthTxtv.setText(String.format(Locale.getDefault(), "%s м", size[2]));

        paymentType.setText(order.getPaymentType1());
        costTxtv.setText(String.format(Locale.getDefault(), "%d %s", Math.round(order.getCost()), order.getCurrency().toLowerCase()));

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
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
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

        Log.d(TAG, "getRouteItemsFromOrder: ");

        for (int i = 0; i < loadingPlaces.size(); i++) {
            Place place = loadingPlaces.get(i);
            int index = place.getName().split(",").length - 1;

            Log.d(TAG, "getRouteItemsFromOrder: " + place.getName());

            String city = place.getName().split(",")[0];
            String country = place.getName().split(",")[index].replaceAll("\\s", "");

            Log.d(TAG, "getRouteItemsFromOrder: " + country);

            if (i == 0) {
                result.add(new RouteItem(order.getLoadingDate(), city, country));
            } else {
                result.add(new RouteItem("", city, country));
            }
        }

        for (int i = 0; i < unloadingPlaces.size(); i++) {
            Place place = unloadingPlaces.get(i);
            int index = place.getName().split(",").length - 1;

            String city = place.getName().split(",")[0];
            String country = place.getName().split(",")[index].replaceAll("\\s", "");

            if (i == unloadingPlaces.size() - 1) {
                result.add(new RouteItem("", city, country));
            } else {
                result.add(new RouteItem("", city, country));
            }
        }


        return result;
    }

    private com.google.maps.model.LatLng getLatLngFromAddress(String address) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        List<android.location.Address> addresses = new ArrayList();
        try {
            addresses = geocoder.getFromLocationName(address, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        android.location.Address add = addresses.get(0);
        double lat = add.getLatitude();
        double lng = add.getLongitude();

        return new com.google.maps.model.LatLng(lat, lng);
    }

    private void initMapRoute() {
        for (Place place : order.getLoadingPlaces()) {
            places.add(getLatLngFromAddress(place.getName()));
        }

        for (Place place : order.getUnloadingPlaces()) {
            places.add(getLatLngFromAddress(place.getName()));
        }

        for (int i = 0; i < places.size(); i++) {
            addMarkerToMap(places.get(i));
        }


        DirectionsResult result = null;
        try {

            com.google.maps.model.LatLng[] latLngs = new com.google.maps.model.LatLng[places.size()];
            for (int i = 0; i < places.size(); i++) {
                latLngs[i] = places.get(i);
            }

            result = DirectionsApi.newRequest(getGeoContext())
                    .mode(TravelMode.DRIVING)
                    .language("ru")
                    .origin(places.get(0))
                    .destination(places.get(places.size() - 1))
                    .waypoints(latLngs)
                    .await();

            CameraUpdate track =
                    CameraUpdateFactory.newLatLngBounds(
                            getLatLngBounds(result), 100);

            if (googleMap != null) {
                googleMap.moveCamera(track);
//                googleMap.setMaxZoomPreference(4);
                googleMap.addPolyline(getLine(result));

                distanceTxtv.setText(getDistance(result));
                timeTxtv.setText(getTime(result));


            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        }

    }

    private void addMarkerToMap(com.google.maps.model.LatLng point) {
        googleMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(point.lat, point.lng)));
    }

    private GeoApiContext getGeoContext() {
        return new GeoApiContext.Builder()
                .apiKey("AIzaSyCeq76Uh6AckX9RUiTwITjAakze2d4rpNM")
                .build();
    }

    private LatLngBounds getLatLngBounds(DirectionsResult result) {
        List<com.google.maps.model.LatLng> path = result.routes[0].overviewPolyline.decodePath();

        LatLngBounds.Builder latLngBuilder = new LatLngBounds.Builder();

        for (int i = 0; i < path.size(); i++) {
            com.google.android.gms.maps.model.LatLng latLng =
                    new com.google.android.gms.maps.model.LatLng(path.get(i).lat, path.get(i).lng);

            latLngBuilder.include(latLng);
        }

        return latLngBuilder.build();
    }

    private String getDistance(DirectionsResult result) {
        long distance = 0;

        for (int i = 0; i < result.routes[0].legs.length; i++) {
            distance += result.routes[0].legs[i].distance.inMeters;
        }
        return String.format(Locale.getDefault(), "%d км", distance / 1000);
    }

    private String getTime(DirectionsResult result) {
        long timeSec = 0;
        for (int i = 0; i < result.routes[0].legs.length; i++) {
            timeSec += result.routes[0].legs[i].duration.inSeconds;
        }

        return String.format(Locale.getDefault(), "%02d ч %02d м", timeSec / 3600, (timeSec % 3600) / 60);
    }

    private PolylineOptions getLine(DirectionsResult result) {
        List<com.google.maps.model.LatLng> path = result.routes[0].overviewPolyline.decodePath();

        PolylineOptions line = new PolylineOptions();
        LatLngBounds.Builder latLngBuilder = new LatLngBounds.Builder();

        for (int i = 0; i < path.size(); i++) {
            com.google.android.gms.maps.model.LatLng latLng =
                    new com.google.android.gms.maps.model.LatLng(path.get(i).lat, path.get(i).lng);

            line.add(latLng);
            latLngBuilder.include(latLng);
        }

        line.width(com.foora.perevozkadev.utils.ViewUtils.dpToPx(4)).color(ContextCompat.getColor(getContext(), R.color.color_map_route));

        return line;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.getUiSettings().setAllGesturesEnabled(false);
        Handler handler = new Handler();
        handler.postDelayed(this::initMapRoute, 700);
    }
}
