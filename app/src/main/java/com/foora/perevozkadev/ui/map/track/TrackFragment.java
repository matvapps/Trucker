package com.foora.perevozkadev.ui.map.track;

import android.graphics.Color;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.DataManagerImpl;
import com.foora.perevozkadev.data.db.LocalRepo;
import com.foora.perevozkadev.data.db.LocalRepoImpl;
import com.foora.perevozkadev.data.network.RemoteRepo;
import com.foora.perevozkadev.data.network.RemoteRepoImpl;
import com.foora.perevozkadev.data.network.model.Location;
import com.foora.perevozkadev.data.prefs.PrefRepo;
import com.foora.perevozkadev.data.prefs.PrefRepoImpl;
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.add_order.model.Place;
import com.foora.perevozkadev.ui.base.BasePresenterFragment;
import com.foora.perevozkadev.ui.map.MapActivity;
import com.foora.perevozkadev.ui.nav.NavDrawerFragment;
import com.foora.perevozkadev.utils.ScreenUtils;
import com.foora.perevozkadev.utils.ViewUtils;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class TrackFragment extends BasePresenterFragment<TrackMvpPresenter>
        implements TrackMvpView {

    public static final String TAG = TrackFragment.class.getSimpleName();

    private GoogleApiClient googleApiClient;
    private GeoDataClient geoDataClient;
    private FloatingActionButton btnSearch;
    private EditText orderIdEdtxt;

    private List<LatLng> places = new ArrayList<>();
    private List<LatLng> readyRoutePlace = new ArrayList<>();

    private GoogleMap googleMap;
    private NavDrawerFragment navDrawerFragment;
    private View btnMain;

    private Order order;

    public static TrackFragment newInstance() {
        Bundle args = new Bundle();
        TrackFragment fragment = new TrackFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_track_order, container, false);


        setUp(rootView);
        return rootView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        googleMap = ((MapActivity) getActivity()).getGoogleMap();
    }

    @Override
    protected void setUp(View view) {
        super.setUp(view);

        btnMain = view.findViewById(R.id.btn_main);
        btnSearch = view.findViewById(R.id.btn_search);
        orderIdEdtxt = view.findViewById(R.id.order_id_edtxt);

        navDrawerFragment = NavDrawerFragment.newInstance();

        googleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .build();
        geoDataClient = Places.getGeoDataClient(getContext(), null);


        btnMain.setOnClickListener(v -> navDrawerFragment.show(getFragmentManager()));
        btnSearch.setOnClickListener(v -> {

            ((MapActivity) getActivity()).getGoogleMap().clear();
            orderIdEdtxt.getText().toString();
            if (!orderIdEdtxt.getText().toString().isEmpty()) {
                int orderId = Integer.parseInt(orderIdEdtxt.getText().toString());
                getPresenter().getOrderById(orderId);
                hideKeyboard();
            }
        });

    }

    private GeoApiContext getGeoContext() {
        return new GeoApiContext.Builder()
                .apiKey("AIzaSyCeq76Uh6AckX9RUiTwITjAakze2d4rpNM")
                .build();
    }

    private void addMarkerToMap(LatLng point) {
        ((MapActivity) getActivity())
                .getGoogleMap()
                .addMarker(
                        new MarkerOptions()
                                .position(new com.google.android.gms.maps.model.LatLng(point.lat, point.lng)));
    }

    private PolylineOptions getLine(DirectionsResult result) {
        List<LatLng> path = result.routes[0].overviewPolyline.decodePath();

        PolylineOptions line = new PolylineOptions();
        LatLngBounds.Builder latLngBuilder = new LatLngBounds.Builder();

        for (int i = 0; i < path.size(); i++) {
            com.google.android.gms.maps.model.LatLng latLng =
                    new com.google.android.gms.maps.model.LatLng(path.get(i).lat, path.get(i).lng);

            line.add(latLng);
            latLngBuilder.include(latLng);
        }

        line.width(ViewUtils.dpToPx(4)).color(ContextCompat.getColor(getContext(), R.color.color_map_route));

        return line;
    }

    private PolylineOptions getRedLine(DirectionsResult result) {
        List<LatLng> path = result.routes[0].overviewPolyline.decodePath();

        PolylineOptions line = new PolylineOptions();
        LatLngBounds.Builder latLngBuilder = new LatLngBounds.Builder();

        for (int i = 0; i < path.size(); i++) {
            com.google.android.gms.maps.model.LatLng latLng =
                    new com.google.android.gms.maps.model.LatLng(path.get(i).lat, path.get(i).lng);

            line.add(latLng);
            latLngBuilder.include(latLng);
        }

        line.width(ViewUtils.dpToPx(6)).color(Color.RED);

        return line;
    }

    private LatLngBounds getLatLngBounds(DirectionsResult result) {
        List<LatLng> path = result.routes[0].overviewPolyline.decodePath();

        LatLngBounds.Builder latLngBuilder = new LatLngBounds.Builder();

        for (int i = 0; i < path.size(); i++) {
            com.google.android.gms.maps.model.LatLng latLng =
                    new com.google.android.gms.maps.model.LatLng(path.get(i).lat, path.get(i).lng);

            latLngBuilder.include(latLng);
        }

        return latLngBuilder.build();
    }

    private void initRoute() {
        ((MapActivity) getActivity())
                .getGoogleMap().clear();
        for (int i = 0; i < places.size(); i++)
            addMarkerToMap(places.get(i));


        DirectionsResult result = null;
        try {

            Log.d("TAG", "onPrepareResult");

            LatLng[] latLngs = new LatLng[places.size()];
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

            if (result == null)
                return;

            CameraUpdate track =
                    CameraUpdateFactory.newLatLngBounds(
                            getLatLngBounds(result),
                            ScreenUtils.getScreenWidth(getContext()),
                            ScreenUtils.getScreenWidth(getContext()), 8);

            if (((MapActivity) getActivity()).getGoogleMap() != null) {
                ((MapActivity) getActivity()).getGoogleMap().moveCamera(track);
                ((MapActivity) getActivity()).getGoogleMap().addPolyline(getLine(result));

//                distanceTxtv.setText(getDistance(result));
//                timeTxtv.setText(getTime(result));
//
//                showResultContainer();


            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        }
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

    private void initMapRoute(Order order) {
        places = new ArrayList<>();


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

            if (((MapActivity) getActivity()).getGoogleMap() != null) {


                ((MapActivity) getActivity()).getGoogleMap().moveCamera(track);
//                googleMap.setMaxZoomPreference(4);
                ((MapActivity) getActivity()).getGoogleMap().addPolyline(getLine(result));

            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        }

    }

    private void initReadyRoute(Order order, Location currentLocation) {
        readyRoutePlace = new ArrayList<>();

        if (currentLocation.getLatitude() == null)
            return;

        readyRoutePlace.add(getLatLngFromAddress(order.getLoadingPlaces().get(0).getName()));
        readyRoutePlace.add(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));


        DirectionsResult result = null;
        try {


            com.google.maps.model.LatLng[] latLngs = new com.google.maps.model.LatLng[readyRoutePlace.size()];
            for (int i = 0; i < readyRoutePlace.size(); i++) {
                latLngs[i] = readyRoutePlace.get(i);
            }

            result = DirectionsApi.newRequest(getGeoContext())
                    .mode(TravelMode.DRIVING)
                    .language("ru")
                    .origin(places.get(0))
                    .destination(readyRoutePlace.get(1))
                    .waypoints(latLngs)
                    .await();

            CameraUpdate track =
                    CameraUpdateFactory.newLatLngBounds(
                            getLatLngBounds(result), 100);

            if (((MapActivity) getActivity()).getGoogleMap() != null) {


                ((MapActivity) getActivity()).getGoogleMap().moveCamera(track);
//                googleMap.setMaxZoomPreference(4);
                ((MapActivity) getActivity()).getGoogleMap().addPolyline(getRedLine(result));

            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected TrackMvpPresenter createPresenter() {
        RemoteRepo remoteRepo = new RemoteRepoImpl();
        PrefRepo preferencesHelper = new PrefRepoImpl(getContext());
        LocalRepo localRepo = new LocalRepoImpl(getContext());
        DataManager dataManager = new DataManagerImpl(remoteRepo, preferencesHelper, localRepo);

        TrackMvpPresenter presenter = new TrackPresenter(dataManager, AndroidSchedulers.mainThread());
        presenter.onAttach(this);

        return presenter;
    }

    private void setupPoint(int pos, String placeId) {
        Log.d(TAG, "setupPoint: ");

        geoDataClient.getPlaceById(placeId).addOnCompleteListener(task -> {
            com.google.android.gms.maps.model.LatLng latLng = task.getResult().get(0).getLatLng();
            LatLng latLng1 = new LatLng(latLng.latitude, latLng.longitude);
            if (places.size() > pos) {
                places.set(pos, latLng1);
            } else {
                places.add(pos, latLng1);
            }
            if (pos != 0)
                initRoute();
        });
    }

    @Override
    public void hideKeyboard() {
        super.hideKeyboard();
        View current = getActivity().getCurrentFocus();
        if (current != null) current.clearFocus();
    }

    @Override
    public void onTrackOrder(Location location) {
        Log.e(TAG, "onTrackOrder: " + location);
        initReadyRoute(order, location);
    }

    @Override
    public void onGetOrder(Order order) {
        getPresenter().trackOrder(order.getId());
        initMapRoute(order);
        this.order = order;
    }
}
