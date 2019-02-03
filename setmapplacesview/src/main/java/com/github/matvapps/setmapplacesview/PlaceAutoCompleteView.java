package com.github.matvapps.setmapplacesview;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.github.matvapps.setmapplacesview.adapter.AutoCompleteArrayAdapter;

/**
 * Created by Alexandr.
 */
public class PlaceAutoCompleteView extends android.support.v7.widget.AppCompatAutoCompleteTextView {

    public PlaceAutoCompleteView(@NonNull Context context) {
        super(context);
        init();
    }

    public PlaceAutoCompleteView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlaceAutoCompleteView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setThreshold(2);
        AutoCompleteArrayAdapter adapter = new AutoCompleteArrayAdapter(getContext());
        setAdapter(adapter);
        setBackgroundColor(Color.TRANSPARENT);
    }



}
