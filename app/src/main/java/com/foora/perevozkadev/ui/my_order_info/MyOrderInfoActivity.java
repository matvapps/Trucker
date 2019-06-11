package com.foora.perevozkadev.ui.my_order_info;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.DataManagerImpl;
import com.foora.perevozkadev.data.db.LocalRepo;
import com.foora.perevozkadev.data.db.LocalRepoImpl;
import com.foora.perevozkadev.data.network.RemoteRepo;
import com.foora.perevozkadev.data.network.RemoteRepoImpl;
import com.foora.perevozkadev.data.prefs.PrefRepo;
import com.foora.perevozkadev.data.prefs.PrefRepoImpl;
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.add_order.model.Place;
import com.foora.perevozkadev.ui.base.BasePresenterActivity;
import com.foora.perevozkadev.ui.change_status.ChangeStatusFragment;
import com.foora.perevozkadev.ui.profile.model.Profile;
import com.foora.perevozkadev.ui.search_order.SearchOrderActivity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.perevozka.foora.routedisplayview.RouteDisplayView;
import com.perevozka.foora.routedisplayview.RouteItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MyOrderInfoActivity extends BasePresenterActivity<MyOrderInfoMvpPresenter> implements MyOrderInfoMvpView,
        OnMapReadyCallback, ChangeStatusFragment.Callback {

    public static final String TAG = MyOrderInfoActivity.class.getSimpleName();
    private static final String KEY_ORDER_ID = "key_order_id";

    int orderId;

    private Profile profile;

    public static void start(Activity activity, int orderId) {
        Intent intent = new Intent(activity, MyOrderInfoActivity.class);
        intent.putExtra(KEY_ORDER_ID, orderId);
        activity.startActivity(intent);
    }


    private RouteDisplayView routeDisplayView;
    private NestedScrollView nestedScrollView;
    private MapView mapView;
    private View btnBack;
    private View btnMenu;
    private TextView titleTxtv;
    private TextView distanceTxtv;
    private TextView timeTxtv;
    private TextView additionalInfo;
    private TextView carQuantityTxtv;
    private TextView transportTypeTxtv;
    private TextView cargoMassTxtv;
    private TextView volumeTxtv;
    private TextView heightTxtv;
    private TextView widthTextv;
    private TextView depthTxtv;
    private TextView paymentType;
    private TextView costTxtv;
    private TextView cargoTypeTxtv;
    private TextView btnSos;

    BottomSheetBehavior bottomSheetBehavior;

    private List<LatLng> places;

    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        orderId = getIntent().getIntExtra(KEY_ORDER_ID, -1);

        setContentView(R.layout.activity_my_order);
        setUnBinder(ButterKnife.bind(this));


        places = new ArrayList<>();

        routeDisplayView = findViewById(R.id.routeDisplayView);
        btnBack = findViewById(R.id.btn_back);
        btnMenu = findViewById(R.id.btn_menu);
        titleTxtv = findViewById(R.id.title);
        mapView = findViewById(R.id.mapView);
        distanceTxtv = findViewById(R.id.distance_txtv);
        timeTxtv = findViewById(R.id.time_txtv);
        carQuantityTxtv = findViewById(R.id.car_quantity_txtv);
        transportTypeTxtv = findViewById(R.id.transport_type_txtv);
        cargoMassTxtv = findViewById(R.id.mass_txtv);
        volumeTxtv = findViewById(R.id.volume_txtv);
        heightTxtv = findViewById(R.id.height_txtv);
        widthTextv = findViewById(R.id.width_txtv);
        depthTxtv = findViewById(R.id.depth_txtv);
        paymentType = findViewById(R.id.payment_txtv);
        costTxtv = findViewById(R.id.cost_txtv);
        additionalInfo = findViewById(R.id.additional_info);
        nestedScrollView = findViewById(R.id.scrollView);
        cargoTypeTxtv = findViewById(R.id.cargo_txtv);
        btnSos = findViewById(R.id.btn_sos);

        btnMenu.setVisibility(View.GONE);

        LinearLayout llBottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);

// init the bottom sheet behavior
        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        btnSos.setOnClickListener(v -> {
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(MyOrderInfoActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(MyOrderInfoActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
            } else {
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    Log.d(TAG, "onCreate: " + latitude + " " + longitude);

                    getPresenter().sendSOS(latitude, longitude);
                    Toast.makeText(this, "SOS-запрос отправлен", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        btnBack.setOnClickListener(v1 -> SearchOrderActivity.start(this));

        getPresenter().getProfile();
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
    protected void setUp() {


    }


    @Override
    protected MyOrderInfoMvpPresenter createPresenter() {
        RemoteRepo remoteRepo = new RemoteRepoImpl();
        PrefRepo preferencesHelper = new PrefRepoImpl(this);
        LocalRepo localRepo = new LocalRepoImpl(this);
        DataManager dataManager = new DataManagerImpl(remoteRepo, preferencesHelper, localRepo);

        MyOrderInfoMvpPresenter presenter = new MyOrderInfoPresenter(dataManager, AndroidSchedulers.mainThread());
        presenter.onAttach(this);

        return presenter;
    }


    @Override
    public void onGetOrder(Order order) {

        initMapRoute(order);

        titleTxtv.setText(String.format(Locale.getDefault(), "Заказ №%d", order.getId()));

        routeDisplayView.setRoutes(getRouteItemsFromOrder(order));
        carQuantityTxtv.setText(String.format(Locale.getDefault(), "%d шт", order.getCarQuantity()));
        transportTypeTxtv.setText(order.getTransportType());
        additionalInfo.setText(order.getAdditionalInfo());
        cargoMassTxtv.setText(String.format(Locale.getDefault(), "%.0f т", order.getWeightFrom()));
        volumeTxtv.setText(String.format(Locale.getDefault(), "%.0f м³", order.getVolumeFrom()));

        String[] size = order.getSize().split("x");
        widthTextv.setText(String.format(Locale.getDefault(), "%s м", size[0]));
        heightTxtv.setText(String.format(Locale.getDefault(), "%s м", size[1]));
        depthTxtv.setText(String.format(Locale.getDefault(), "%s м", size[2]));

        cargoTypeTxtv.setText(order.getCargoTypeName());


        if (!order.getStatus().equals("finished")
                || !order.getStatus().equals("Груз доставлен")) {
            btnMenu.setVisibility(View.VISIBLE);
        }

        Log.d(TAG, "onGetOrder: " + order.toString());

        btnMenu.setOnClickListener(v -> MenuFragment.newInstance(new Gson().toJson(order), profile.getUserId()).show(getSupportFragmentManager(), MenuFragment.TAG));

//        paymentType.setText(order.getPaymentType1());
//        costTxtv.setText(String.format(Locale.getDefault(),
//                "%d %s",
//                Math.round(order.getCost()),
//                order.getCurrency().toLowerCase()));
    }

    @Override
    public void onChangeOrderStatus() {
        showMessage("Статус заказа успешно изменен");
        getPresenter().getOrderById(orderId);
    }

    @Override
    public void onGetProfile(Profile profile) {
        this.profile = profile;

        if (profile.getGroups().contains("driver"))
            btnSos.setVisibility(View.VISIBLE);

    }

    private List<RouteItem> getRouteItemsFromOrder(@NonNull Order order) {
        List<RouteItem> result = new ArrayList<>();
        List<Place> loadingPlaces = order.getLoadingPlaces();
        List<Place> unloadingPlaces = order.getUnloadingPlaces();

//        for (int i = 0; i < loadingPlaces.size(); i++) {
//            Place place = loadingPlaces.get(i);
//            String name = place.getName().split(",")[0];
//
//            if (i == 0) {
//                result.add(new RouteItem(order.getLoadingDate(), name, ""));
//            } else {
//                result.add(new RouteItem("", name, ""));
//            }
//        }

        for (int i = 0; i < loadingPlaces.size(); i++) {
            Place place = loadingPlaces.get(i);
            int index = place.getName().split(",").length - 1;

            String city = place.getName().split(",")[0];
            String country = place.getName().split(",")[index].replaceAll("\\s", "");

            if (i == loadingPlaces.size() - 1) {
                result.add(new RouteItem("", city, country));
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

//        for (int i = 0; i < unloadingPlaces.size(); i++) {
//            Place place = unloadingPlaces.get(i);
//            String name = place.getName().split(",")[0];
//
//            if (i == unloadingPlaces.size() - 1) {
//                result.add(new RouteItem(order.getUnloadingDate(), name, ""));
//            } else {
//                result.add(new RouteItem("", name, ""));
//            }
//        }


        return result;
    }

    private com.google.maps.model.LatLng getLatLngFromAddress(String address) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<android.location.Address> addresses = new ArrayList();
        try {
            addresses = geocoder.getFromLocationName(address, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addresses.size() == 0) {
            return null;
        }
        android.location.Address add = addresses.get(0);
        double lat = add.getLatitude();
        double lng = add.getLongitude();

        return new com.google.maps.model.LatLng(lat, lng);
    }

    private void initMapRoute(Order order) {
        for (Place place : order.getLoadingPlaces()) {
            com.google.maps.model.LatLng latLng = getLatLngFromAddress(place.getName());
            if (latLng != null) {
                places.add(latLng);
            }
        }

        for (Place place : order.getUnloadingPlaces()) {
            com.google.maps.model.LatLng latLng = getLatLngFromAddress(place.getName());
            if (latLng != null) {
                places.add(latLng);
            }
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

            if (places.size() < 1)
                return;

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
                        .position(new com.google.android.gms.maps.model.LatLng(point.lat, point.lng)));
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

        line.width(com.foora.perevozkadev.utils.ViewUtils.dpToPx(4))
                .color(ContextCompat.getColor(this, R.color.color_map_route));

        return line;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.getUiSettings().setAllGesturesEnabled(false);
        getPresenter().getOrderById(orderId);
    }

    @Override
    public void onChangeStatus(String status) {
        if (status.equals("Груз доставлен"))
            status = "finished";
        getPresenter().changeOrderStatus(orderId, status);
    }

    @Override
    public void onCallManager() {

    }
}
