package com.foora.perevozkadev.utils.custom.places;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;

import com.foora.foora.perevozkadev.R;
import com.google.android.gms.location.places.AutocompleteFilter;


/**
 * Created by Alexander Matvienko on 17.01.2019.
 */
public class PlaceAutoCompleteTextView extends FrameLayout {

    private AutoCompleteTextView autoCompleteTextView;
    private TextInputLayout textInputLayout;
    private PlaceArrayAdapter adapter;
    private int filterType = AutocompleteFilter.TYPE_FILTER_CITIES;

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
        adapter = new PlaceArrayAdapter(getContext());
        adapter.setFilterType(filterType);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        if (!autoCompleteTextView.getText().toString().isEmpty()) {
            setActivated(true);
        }

        autoCompleteTextView.setOnFocusChangeListener((v, hasFocus) -> {
            setActivated(true);
            textInputLayout.setDefaultHintTextColor(
                    ContextCompat.getColorStateList(getContext(),
                            R.color.orange_yellow));

            if (!hasFocus && autoCompleteTextView.getText().toString().isEmpty()) {
                setActivated(false);
                textInputLayout.setDefaultHintTextColor(
                        ContextCompat.getColorStateList(getContext(),
                                R.color.colorTextHint));
            }
        });
    }

    public int getFilterType() {
        return filterType;
    }

    public void setFilterType(int filterType) {
        this.filterType = filterType;
        adapter.setFilterType(filterType);
    }

    public void setHint(String hint) {
        textInputLayout.setHint(hint);
    }

    public void setText(String text) {
        autoCompleteTextView.setText(text);
        setActivated(true);
        textInputLayout.setDefaultHintTextColor(
                ContextCompat.getColorStateList(getContext(),
                        R.color.orange_yellow));
    }

    public String getText() {
        return autoCompleteTextView.getText().toString();
    }

}
