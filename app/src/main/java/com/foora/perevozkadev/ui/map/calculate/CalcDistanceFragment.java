package com.foora.perevozkadev.ui.map.calculate;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
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
import com.foora.perevozkadev.ui.messages.MessagesFragment;
import com.foora.perevozkadev.utils.ScreenUtils;
import com.foora.perevozkadev.utils.ViewUtils;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
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

public class CalcDistanceFragment extends BasePresenterFragment<CalcDistanceMvpPresenter> implements CalcDistanceMvpView {

    public static final String TAG = CalcDistanceFragment.class.getSimpleName();

    private FloatingActionButton currentLocationFab;
    private TextView distanceTxtv;
    private TextView timeTxtv;

    private List<LatLng> places = new ArrayList<>();

    private GoogleMap googleMap;

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

        currentLocationFab = rootView.findViewById(R.id.current_location);
        distanceTxtv = rootView.findViewById(R.id.distance_txtv);
        timeTxtv = rootView.findViewById(R.id.time_txtv);


        currentLocationFab.setOnClickListener(view -> function());

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

        places.add(new LatLng(55.754724, 37.621380));
        places.add(new LatLng(55.760133, 37.618697));
        places.add(new LatLng(55.764753, 37.591313));
        places.add(new LatLng(55.728466, 37.604155));


//        function();

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

        line.width(ViewUtils.dpToPx(4)).color(ContextCompat.getColor(getContext(), R.color.color_app_blue));

        return line;
    }

    private String getDistance(DirectionsResult result) {
        return result.routes[0].legs[0].distance.humanReadable;
    }

    private String getTime(DirectionsResult result) {
        return result.routes[0].legs[0].duration.humanReadable;
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

    private void function() {

        for (int i = 0; i < places.size(); i++)
            addMarkerToMap(places.get(i));


        DirectionsResult result = null;
        try {

            Log.d("TAG", "onPrepareResult");

            result = DirectionsApi.newRequest(getGeoContext())
                    .mode(TravelMode.DRIVING)
                    .language("ru")
                    .origin(places.get(0))
                    .destination(places.get(places.size() - 1))
                    .waypoints(places.get(1), places.get(2)).await();


//            List<LatLng> path = result.routes[0].overviewPolyline.decodePath();
//
//            PolylineOptions line = new PolylineOptions();
//            LatLngBounds.Builder latLngBuilder = new LatLngBounds.Builder();
//
//            for (int i = 0; i < path.size(); i++) {
//                com.google.android.gms.maps.model.LatLng latLng =
//                        new com.google.android.gms.maps.model.LatLng(path.get(i).lat, path.get(i).lng);
//
//                line.add(latLng);
//                latLngBuilder.include(latLng);
//            }
//
//            line.width(ViewUtils.dpToPx(4)).color(ContextCompat.getColor(getContext(), R.color.color_app_blue));
//
//            LatLngBounds latLngBounds = latLngBuilder.build();


            CameraUpdate track =
                    CameraUpdateFactory.newLatLngBounds(
                            getLatLngBounds(result),
                            ScreenUtils.getScreenWidth(getContext()),
                            ScreenUtils.getScreenWidth(getContext()), 25);


            if (((MapActivity) getActivity()).getGoogleMap() != null) {
                ((MapActivity) getActivity()).getGoogleMap().moveCamera(track);
                ((MapActivity) getActivity()).getGoogleMap().addPolyline(getLine(result));

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

    @Override
    protected CalcDistanceMvpPresenter createPresenter() {
        return null;
    }


}
