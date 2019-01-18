package com.foora.perevozkadev.utils.custom.places;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;

import com.foora.foora.perevozkadev.R;


/**
 * Created by Alexander Matvienko on 17.01.2019.
 */
public class PlaceAutoCompleteTextView extends FrameLayout {

    private AutoCompleteTextView autoCompleteTextView;
    private TextInputLayout textInputLayout;

    public PlaceAutoCompleteTextView(@NonNull Context context) {
        super(context);
        init();
    }

    public PlaceAutoCompleteTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlaceAutoCompleteTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        View view = inflate(getContext(), R.layout.auto_complete_view, this);

        autoCompleteTextView = view.findViewById(R.id.auto_complete_text_view);
        textInputLayout = view.findViewById(R.id.text_input_layout);

        autoCompleteTextView.setThreshold(2);
        PlaceArrayAdapter adapter = new PlaceArrayAdapter(getContext());
        autoCompleteTextView.setAdapter(adapter);

        if (!autoCompleteTextView.getText().toString().isEmpty()) {
            setActivated(true);
        }

        autoCompleteTextView.setOnFocusChangeListener((v, hasFocus) -> {
            setActivated(true);
            textInputLayout.setDefaultHintTextColor(ContextCompat.getColorStateList(getContext(), com.github.matvapps.R.color.orange_yellow));

            if (!hasFocus && autoCompleteTextView.getText().toString().isEmpty()) {
                setActivated(false);
                textInputLayout.setDefaultHintTextColor(ContextCompat.getColorStateList(getContext(), com.github.matvapps.R.color.colorTextHint));
            }
        });
    }

    public String getText() {
        return autoCompleteTextView.getText().toString();
    }

}
