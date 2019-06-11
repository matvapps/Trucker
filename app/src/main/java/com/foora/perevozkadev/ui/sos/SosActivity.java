package com.foora.perevozkadev.ui.sos;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
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
import com.foora.perevozkadev.ui.base.BasePresenterActivity;
import com.foora.perevozkadev.utils.ViewUtils;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class SosActivity extends BasePresenterActivity<SosMvpPresenter> implements SosMvpView, OnMapReadyCallback {

    public static final String TAG = SosActivity.class.getSimpleName();


    @BindView(R.id.mapView)
    MapView mapView;

    private Button btnAccept;

    private List<LatLng> places = new ArrayList<>();
    private List<LatLng> readyRoutePlace = new ArrayList<>();
    private GoogleMap googleMap;
    private LatLng sosLocation;
    private Marker userMarker;
    private Marker placeMarker;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, SosActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);

        setUnBinder(ButterKnife.bind(this));

        btnAccept = findViewById(R.id.btn_accept);

        int sos_request_id = getIntent().getIntExtra("sos_request_id", 0);
        double lng = getIntent().getDoubleExtra("longitude", 0);
        double lat = getIntent().getDoubleExtra("latitude", 0);
        int id = getIntent().getIntExtra("user_id", 0);

        sosLocation = new LatLng(lat, lng);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        btnAccept.setOnClickListener(v -> {
            getPresenter().acceptSos(sos_request_id);
            btnAccept.setVisibility(View.GONE);
            Toast.makeText(this, "Пользователь ожидает вас", Toast.LENGTH_SHORT).show();
        });

//        showLoading();

        setUp();
    }

    @Override
    protected void setUp() {

        LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, mLocationListener);


        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {

                LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                initMapRoute(currentLocation, sosLocation);
            }
        }

    }


    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            hideLoading();

            LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
            Log.d(TAG, "onLocationChanged: " + location);

            if (userMarker != null) {
                animateMarker(userMarker,
                        new com.google.android.gms.maps.model.LatLng(currentLocation.lat, currentLocation.lng),
                        false);

            } else {
                initMapRoute(currentLocation, sosLocation);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Override
    protected SosMvpPresenter createPresenter() {
        RemoteRepo remoteRepo = new RemoteRepoImpl();
        PrefRepo preferencesHelper = new PrefRepoImpl(this);
        LocalRepo localRepo = new LocalRepoImpl(this);
        DataManager dataManager = new DataManagerImpl(remoteRepo, preferencesHelper, localRepo);

        SosMvpPresenter presenter = new SosPresenter(dataManager, AndroidSchedulers.mainThread());
        presenter.onAttach(this);

        return presenter;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        Log.d("TAG", "onMapReady");

        addMarkerToMap(sosLocation);
        googleMap.setMyLocationEnabled(true);

    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private GeoApiContext getGeoContext() {
        return new GeoApiContext.Builder()
                .apiKey("AIzaSyCeq76Uh6AckX9RUiTwITjAakze2d4rpNM")
                .build();
    }

    private void addMarkerToMap(LatLng point) {

        googleMap
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

        line.width(ViewUtils.dpToPx(4)).color(ContextCompat.getColor(this, R.color.color_map_route));

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

    private com.google.maps.model.LatLng getLatLngFromAddress(String address) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
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

    private void initMapRoute(LatLng currentLocation, LatLng sosLocation) {


        places = new ArrayList<>();

        places.add(currentLocation);
        places.add(sosLocation);

        userMarker = googleMap
                .addMarker(
                        new MarkerOptions()
                                .position(new com.google.android.gms.maps.model.LatLng(currentLocation.lat, currentLocation.lng)));


        placeMarker = googleMap
                .addMarker(
                        new MarkerOptions()
                                .position(new com.google.android.gms.maps.model.LatLng(sosLocation.lat, sosLocation.lng)));


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

            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        }

    }

    public void animateMarker(final Marker marker, final com.google.android.gms.maps.model.LatLng toPosition,
                              final boolean hideMarker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = googleMap.getProjection();
        Point startPoint = proj.toScreenLocation(marker.getPosition());
        final com.google.android.gms.maps.model.LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 500;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new com.google.android.gms.maps.model.LatLng(lat, lng));

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
    }


}
