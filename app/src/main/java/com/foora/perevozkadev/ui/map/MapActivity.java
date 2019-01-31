package com.foora.perevozkadev.ui.map;

import android.os.PersistableBundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    GoogleMap googleMap;

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
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, CalcDistanceFragment.newInstance(), CalcDistanceFragment.TAG);
        fragmentTransaction.commit();
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
        mapView.onDestroy();
    }

    public GoogleMap getGoogleMap() {
        return googleMap;
    }
}
