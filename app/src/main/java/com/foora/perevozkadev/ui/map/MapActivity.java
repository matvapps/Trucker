package com.foora.perevozkadev.ui.map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.FrameLayout;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.base.BaseActivity;
import com.foora.perevozkadev.ui.map.calculate.CalcDistanceFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapActivity extends BaseActivity implements OnMapReadyCallback {

    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    GoogleMap googleMap;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, MapActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        setUnBinder(ButterKnife.bind(this));

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        setUp();
    }

    @Override
    protected void setUp() {
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.action_calculate:
                    Fragment fragmentA = getSupportFragmentManager().findFragmentByTag(CalcDistanceFragment.TAG);
                    if (fragmentA == null) {
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container, CalcDistanceFragment.newInstance(), CalcDistanceFragment.TAG);
                        fragmentTransaction.commit();
                        return true;
                    } else {
                        return false;
                    }
                default:
                    return false;
            }

        });
        bottomNavigationView.setSelectedItemId(R.id.action_calculate);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        Log.d("TAG", "onMapReady");
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

    public GoogleMap getGoogleMap() {
        return googleMap;
    }
}
