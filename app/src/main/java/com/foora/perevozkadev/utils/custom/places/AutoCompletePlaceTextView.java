package com.foora.perevozkadev.utils.custom.places;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;

import com.foora.foora.perevozkadev.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;


/**
 * Created by Alexander Matvienko on 17.01.2019.
 */
public class AutoCompletePlaceTextView extends FrameLayout {

    private AutoCompleteTextView autoCompleteTextView;
    private PlaceArrayAdapter placeArrayAdapter;


    public AutoCompletePlaceTextView(@NonNull Context context) {
        super(context);
        init();
    }

    public AutoCompletePlaceTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AutoCompletePlaceTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        View view = inflate(getContext(), R.layout.auto_complete_view, this);

        autoCompleteTextView = view.findViewById(R.id.auto_complete_text_view);


        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();

            LatLngBounds latLngBounds = new LatLngBounds(
                    new LatLng(longitude, latitude), new LatLng(longitude, latitude));

            placeArrayAdapter = new PlaceArrayAdapter(
                    getContext(), android.R.layout.simple_list_item_1, latLngBounds, null);

            autoCompleteTextView.setAdapter((placeArrayAdapter));

        }



    }

}
