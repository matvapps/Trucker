package com.foora.perevozkadev.ui;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.AutoCompleteTextView;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.utils.custom.places.PlaceArrayAdapter;
import com.foora.perevozkadev.utils.custom.places.PlaceAutoCompleteTextView;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(TestActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

        setContentView(R.layout.activity_test2);


    }
}
