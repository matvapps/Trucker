package com.foora.perevozkadev.ui.map.calculate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.base.BasePresenterFragment;
import com.foora.perevozkadev.ui.map.MapActivity;
import com.foora.perevozkadev.ui.messages.MessagesFragment;
import com.foora.perevozkadev.utils.ScreenUtils;
import com.foora.perevozkadev.utils.ViewUtils;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLngBounds;
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

    private List<LatLng> places = new ArrayList<>();

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
        currentLocationFab.setOnClickListener(view -> function());

        return rootView;

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

    private void function() {
        GeoApiContext geoApiContext = new GeoApiContext.Builder()
                .apiKey("AIzaSyDmDonr8JO8lYMl669D20RE2wG9RxBWzOk")
                .build();

        DirectionsResult result = null;
        try {
            result = DirectionsApi.newRequest(geoApiContext)
                    .mode(TravelMode.DRIVING)
                    .origin(places.get(0))
                    .destination(places.get(places.size() - 1))
                    .waypoints(places.get(1), places.get(2)).await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        }

        List<LatLng> path = result.routes[0].overviewPolyline.decodePath();


        PolylineOptions line = new PolylineOptions();

        LatLngBounds.Builder latLngBuilder = new LatLngBounds.Builder();

        for (int i = 0; i < path.size(); i++) {
            com.google.android.gms.maps.model.LatLng latLng =
                    new com.google.android.gms.maps.model.LatLng(path.get(i).lat, path.get(i).lng);

            line.add(latLng);
            latLngBuilder.include(latLng);
        }

        line.width(ViewUtils.dpToPx(2)).color(R.color.color_info_light);

        LatLngBounds latLngBounds = latLngBuilder.build();
        CameraUpdate track =
                CameraUpdateFactory.newLatLngBounds(
                        latLngBounds,
                        ScreenUtils.getScreenWidth(getContext()),
                        ScreenUtils.getScreenWidth(getContext()), 25);

        ((MapActivity) getActivity()).googleMap.moveCamera(track);
    }

    @Override
    protected CalcDistanceMvpPresenter createPresenter() {
        return null;
    }


}
