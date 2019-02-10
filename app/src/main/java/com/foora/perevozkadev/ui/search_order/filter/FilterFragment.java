package com.foora.perevozkadev.ui.search_order.filter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.add_order.cargo_info.DateRangePickerDialog;
import com.foora.perevozkadev.ui.add_order.model.Place;
import com.foora.perevozkadev.ui.add_order.route.model.RouteItem;
import com.foora.perevozkadev.ui.base.BaseFragment;
import com.foora.perevozkadev.ui.search_order.filter.model.Filter;
import com.foora.perevozkadev.utils.ViewUtils;
import com.foora.perevozkadev.utils.custom.CustomSpinner;
import com.foora.perevozkadev.utils.custom.SpinnerArrayAdapter;
import com.foora.perevozkadev.utils.custom.places.PlaceAutoCompleteTextView;
import com.github.matvapps.AppEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Alexandr.
 */
public class FilterFragment extends BaseFragment implements DateRangePickerDialog.Callback {

    public static final String TAG = FilterFragment.class.getSimpleName();

    @BindView(R.id.routes_list)
    LinearLayout loadList;
    @BindView(R.id.routes_list_2)
    LinearLayout unloadList;

    @BindView(R.id.txtv_dates)
    TextView datesTxtv;
    @BindView(R.id.spinner_mass_from)
    AppEditText massFrom;
    @BindView(R.id.spinner_mass_to)
    AppEditText massTo;
    @BindView(R.id.spinner_volume_from)
    AppEditText volumeFrom;
    @BindView(R.id.spinner_volume_to)
    AppEditText volumeTo;
    @BindView(R.id.spinner_container)
    LinearLayout spinnerContainer;
    @BindView(R.id.transport_spinner)
    CustomSpinner transportSpinner;
    @BindView(R.id.add_transport)
    View addTransport;


    private DateRangePickerDialog dateRangePickerDialog;
    private SpinnerArrayAdapter transportArrayAdapter;


    private Callback listener;
    private Filter filter;

    private List<Place> loadingPlaces;
    private List<Place> unloadingPlaces;
    private List<String> transportType;

    private ArrayList<String> transports;


    public static FilterFragment newInstance() {
        Bundle args = new Bundle();
        FilterFragment fragment = new FilterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_filter, container, false);
        setUnBinder(ButterKnife.bind(this, rootView));
        return rootView;
    }

    @OnClick(R.id.add_route)
    void onAddLoadRoute() {
        loadList.addView(getPlaceAutoCompleteTxtv(RouteItem.Type.LOADING_PLACE));
    }

    @OnClick(R.id.add_route_2)
    void onAddUnLoadRoute() {
        unloadList.addView(getPlaceAutoCompleteTxtv(RouteItem.Type.UNLOADING_PLACE));
    }

    @OnClick(R.id.add_transport)
    void addTransport() {
        CustomSpinner spinner = new CustomSpinner(getContext());
        Drawable transparentDrawable = new ColorDrawable(Color.TRANSPARENT);
        spinner.setBackground(transparentDrawable);
        spinner.setDropDownVerticalOffset(ViewUtils.dpToPx(60));
        spinner.setLayoutParams(new ViewGroup.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewUtils.dpToPx(60)));

        SpinnerArrayAdapter spinnerArrayAdapter = new SpinnerArrayAdapter(getContext(), transports, true);
        spinner.setAdapter(spinnerArrayAdapter);

        spinnerContainer.addView(spinner);
    }

    @OnClick(R.id.btn_accept)
    void onAccept() {
        if (listener != null) {

            for (int i = 0; i < loadList.getChildCount(); i++) {
                PlaceAutoCompleteTextView placeAutoCompleteTextView = (PlaceAutoCompleteTextView) loadList.getChildAt(i);

                String name = placeAutoCompleteTextView.getText();

                if (!name.isEmpty())
                    loadingPlaces.add(new Place(i, name));
            }

            for (int i = 0; i < unloadList.getChildCount(); i++) {
                PlaceAutoCompleteTextView placeAutoCompleteTextView = (PlaceAutoCompleteTextView) unloadList.getChildAt(i);

                String name = placeAutoCompleteTextView.getText();

                if (!name.isEmpty())
                    unloadingPlaces.add(new Place(i, name));
            }

            if (!datesTxtv.getText().toString().isEmpty()) {
                String[] dates = datesTxtv.getText().toString().split("-");

                //read dates
                String dateStart = dates[0].replaceAll("\\.", "-");

                String dateEnd = "";

                if (dates.length > 1)
                    dateEnd = dates[1].replaceAll("\\.", "-");

                dateStart = dateStart.replaceAll("\\s", "");

                filter.setLoadingDate(dateStart);
                filter.setUnloadingDate(dateEnd);
            }


            if (!massFrom.getText().toString().isEmpty()) {
                float massFromNum = Float.parseFloat(massFrom.getText().toString());
                filter.setWeightFrom(massFromNum);
            }
            if (!massTo.getText().toString().isEmpty()) {
                float massToNum = Float.parseFloat(massTo.getText().toString());
                filter.setWeightTo(massToNum);
            }

            if (!volumeFrom.getText().toString().isEmpty()) {
                float volumeFromNum = Float.parseFloat(volumeFrom.getText().toString());
                filter.setVolumeFrom(volumeFromNum);
            }

            if (!volumeTo.getText().toString().isEmpty()) {
                float volumeToNum = Float.parseFloat(volumeTo.getText().toString());
                filter.setVolumeTo(volumeToNum);
            }

            // read transport types
            for (int i = 0; i < spinnerContainer.getChildCount(); i++) {
                CustomSpinner spinner = (CustomSpinner) spinnerContainer.getChildAt(i);
                transportType.add((String) spinner.getSelectedItem());
            }

            filter.setLoadingPlaces(loadingPlaces);
            filter.setUnloadingPlaces(unloadingPlaces);

            // TODO:
            if (!transportType.get(0).equals("Любой"))
                filter.setTransportType(transportType.get(0));


            Log.d(TAG, "onAccept: " + filter.toString());

            getFragmentManager()
                    .beginTransaction()
                    .detach(FilterFragment.newInstance())
                    .attach(FilterFragment.newInstance())
                    .commit();

            listener.onCreateNewFilter(filter);
        }
    }

    @OnClick(R.id.date_range_container)
    void onDateRangeClick() {
        showDateRangePicker();
    }


    void showDateRangePicker() {
        dateRangePickerDialog.show(getFragmentManager(), DateRangePickerDialog.TAG);
    }

    @Override
    protected void setUp(View view) {
        dateRangePickerDialog = new DateRangePickerDialog();
        dateRangePickerDialog.setListener(this);

        transportType = new ArrayList<>();
        loadingPlaces = new ArrayList<>();
        unloadingPlaces = new ArrayList<>();

        transports = new ArrayList<String>();
        String[] transportArr = getContext().getResources().getStringArray(R.array.transport_types);
        transports.addAll(Arrays.asList(transportArr));

        filter = new Filter();

        loadList.addView(getPlaceAutoCompleteTxtv(RouteItem.Type.LOADING_PLACE));
        unloadList.addView(getPlaceAutoCompleteTxtv(RouteItem.Type.UNLOADING_PLACE));

        Drawable transparentDrawable = new ColorDrawable(Color.TRANSPARENT);
        transportSpinner.setBackground(transparentDrawable);

        transportArrayAdapter = new SpinnerArrayAdapter(getContext(), transports, true);

        transportSpinner.setAdapter(transportArrayAdapter);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (Callback) context;
    }

    private PlaceAutoCompleteTextView getPlaceAutoCompleteTxtv(RouteItem.Type type) {
        PlaceAutoCompleteTextView placeAutoCompleteTextView = new PlaceAutoCompleteTextView(getContext());
        switch (type) {
            case LOADING_PLACE:

                break;
            case UNLOADING_PLACE:

                break;
        }

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, ViewUtils.dpToPx(8), 0, 0);

        placeAutoCompleteTextView.setLayoutParams(layoutParams);

        return placeAutoCompleteTextView;
    }

    @Override
    public void onRangeSelected(Calendar startDate, Calendar endDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
        if (startDate != null && endDate != null) {
            datesTxtv.setText(String.format("%s - %s", format.format(startDate.getTime()), format.format(endDate.getTime())));
            datesTxtv.setActivated(true);
        } else if (startDate != null) {
            datesTxtv.setText(String.format("%s", format.format(startDate.getTime())));
            datesTxtv.setActivated(true);
        }
    }


    public interface Callback {
        void onCreateNewFilter(Filter filter);
    }

}
