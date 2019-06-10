package com.foora.perevozkadev.ui.map.calculate;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.base.BasePresenterFragment;
import com.foora.perevozkadev.ui.map.MapActivity;
import com.foora.perevozkadev.ui.nav.NavDrawerFragment;
import com.foora.perevozkadev.utils.ScreenUtils;
import com.foora.perevozkadev.utils.ViewUtils;
import com.github.florent37.viewanimator.ViewAnimator;
import com.github.matvapps.setmapplacesview.MapPlacesView;
import com.github.matvapps.setmapplacesview.model.Place;
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

public class CalcDistanceFragment extends BasePresenterFragment<CalcDistanceMvpPresenter>
        implements CalcDistanceMvpView, MapPlacesView.Callback {

    public static final String TAG = CalcDistanceFragment.class.getSimpleName();

    private FloatingActionButton currentLocationFab;
    private View bottomContainer;
    private View resultContainer;
    private TextView distanceTxtv;
    private TextView timeTxtv;
    private MapPlacesView mapPlacesView;
    private GoogleApiClient googleApiClient;
    private GeoDataClient geoDataClient;

    private List<LatLng> places = new ArrayList<>();

    private GoogleMap googleMap;
    private NavDrawerFragment navDrawerFragment;

    public static CalcDistanceFragment newInstance() {
        Bundle args = new Bundle();
        CalcDistanceFragment fragment = new CalcDistanceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calc_distance, container, false);

        navDrawerFragment = NavDrawerFragment.newInstance();

        googleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .build();
        geoDataClient = Places.getGeoDataClient(getContext(), null);


        currentLocationFab = rootView.findViewById(R.id.current_location);
        distanceTxtv = rootView.findViewById(R.id.distance_txtv);
        timeTxtv = rootView.findViewById(R.id.time_txtv);
        bottomContainer = rootView.findViewById(R.id.bottom_container);
        resultContainer = rootView.findViewById(R.id.result_container);
        mapPlacesView = rootView.findViewById(R.id.places_view);

        mapPlacesView.setListener(this);
        currentLocationFab.setOnClickListener(view -> initRoute());

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

//        places.add(new LatLng(55.754724, 37.621380));
//        places.add(new LatLng(55.760133, 37.618697));
//        places.add(new LatLng(55.764753, 37.591313));
//        places.add(new LatLng(55.728466, 37.604155));


//        initRoute();

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

    private LatLngBounds getLatLngBounds(DirectionsResult result) {

        if (result.routes.length == 0) {
            showErrorMessage("Не удалось найти маршрут");
            return null;
        }

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

            result = DirectionsApi.newRequest(getGeoContext())
                    .mode(TravelMode.DRIVING)
                    .language("ru")
                    .origin(places.get(0))
                    .destination(places.get(places.size() - 1))
                    .waypoints(latLngs)
                    .await();

            if (getLatLngBounds(result) == null) {
                return;
            } else {

                CameraUpdate track =
                        CameraUpdateFactory.newLatLngBounds(
                                getLatLngBounds(result),
                                ScreenUtils.getScreenWidth(getContext()),
                                ScreenUtils.getScreenWidth(getContext()), 8);

                if (((MapActivity) getActivity()).getGoogleMap() != null) {
                    ((MapActivity) getActivity()).getGoogleMap().moveCamera(track);
                    ((MapActivity) getActivity()).getGoogleMap().addPolyline(getLine(result));

                    distanceTxtv.setText(getDistance(result));
                    timeTxtv.setText(getTime(result));

                    showResultContainer();


                }
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
    protected CalcDistanceMvpPresenter createPresenter() {
        return null;
    }

    private void showResultContainer() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            resultContainer.setVisibility(View.VISIBLE);
            ViewAnimator
                    .animate(bottomContainer)
                    .slideBottom()
                    .alpha(1, 1)
                    .duration(1000)
                    .start();
        }, 1000);
    }

    private void hideResultContainer() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            ViewAnimator
                    .animate(bottomContainer)
                    .slideTop()
                    .duration(1000)
                    .onStop(() -> resultContainer.setVisibility(View.INVISIBLE))
                    .start();
        }, 1000);
    }


    @Override
    public void onItemInserted(int pos, Place place) {
        hideKeyboard();
//        hideResultContainer();
        View current = getActivity().getCurrentFocus();
        if (current != null) current.clearFocus();
    }

    @Override
    public void onItemRemoved(int pos, Place place) {
        hideKeyboard();
//        hideResultContainer();
        if (places.size() > pos) {
            places.remove(pos);
            if (pos != 0)
                initRoute();
        }

    }

    @Override
    public void onItemSelected(int pos, Place place) {
        hideKeyboard();
//        hideResultContainer();
        setupPoint(pos, place.getPlaceId());
    }

    @Override
    public void onReverseList() {
        hideKeyboard();
//        hideResultContainer();
        initRoute();
    }

    @Override
    public void onBtnBackClick() {
        hideKeyboard();
//        hideResultContainer();
    }

    @Override
    public void onBtnMenuClick() {
        navDrawerFragment.show(getFragmentManager());
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
}
