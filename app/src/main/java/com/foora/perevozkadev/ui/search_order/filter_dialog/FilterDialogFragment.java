package com.foora.perevozkadev.ui.search_order.filter_dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.add_order.cargo_info.DateRangePickerDialog;
import com.foora.perevozkadev.ui.add_order.model.Place;
import com.foora.perevozkadev.ui.add_order.route.model.RouteItem;
import com.foora.perevozkadev.ui.base.BaseDialog;
import com.foora.perevozkadev.ui.search_order.filter.FilterFragment;
import com.foora.perevozkadev.ui.search_order.filter.model.Filter;
import com.foora.perevozkadev.utils.ViewUtils;
import com.foora.perevozkadev.utils.custom.CustomSpinner;
import com.foora.perevozkadev.utils.custom.SpinnerArrayAdapter;
import com.foora.perevozkadev.utils.custom.places.PlaceAutoCompleteTextView;
import com.github.matvapps.AppEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Alexandr.
 */
public class FilterDialogFragment extends BaseDialog implements DateRangePickerDialog.Callback {

    public static final String TAG = FilterDialogFragment.class.getSimpleName();

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

    private Filter filter;

    private List<Place> loadingPlaces;
    private List<Place> unloadingPlaces;
    private List<String> transportType;

    private ArrayList<String> transports;


    public static FilterDialogFragment newInstance() {
        FilterDialogFragment fragment = new FilterDialogFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @OnClick(R.id.action_cancel)
    void onCancel() {
        dismissDialog();
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
//        if (listener != null) {

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
                String dateEnd = dates[1].replaceAll("\\.", "-");

                dateStart = dateStart.replaceAll("\\s", "");
                dateEnd = dateEnd.replaceAll("\\s", "");

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

            //TODO:
//            listener.onCreateNewFilter(filter);
//        }
    }

    @OnClick(R.id.date_range_container)
    void onDateRangeClick() {
        showDateRangePicker();
    }


    void showDateRangePicker() {
        dateRangePickerDialog.show(getFragmentManager(), DateRangePickerDialog.TAG);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // the content
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setGravity(Gravity.END);
            dialog.getWindow().setWindowAnimations(
                    R.style.dialog_right_animation_slide);
            dialog.getWindow().setLayout(
                    ViewUtils.dpToPx(300),
                    ViewGroup.LayoutParams.MATCH_PARENT);
        }
        dialog.setCanceledOnTouchOutside(true);

        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_filter, container, false);

        setUnBinder(ButterKnife.bind(this, view));

        return view;
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    public void dismissDialog() {
        super.dismissDialog(TAG);
    }

    @Override
    protected void setUp(View view) {
        dateRangePickerDialog = new DateRangePickerDialog();
        dateRangePickerDialog.setListener(this);

        transportType = new ArrayList<>();
        loadingPlaces = new ArrayList<>();
        unloadingPlaces = new ArrayList<>();
        transports = new ArrayList<String>();

        transports.add("Любой");
        transports.add("Рефрижератор");
        transports.add("Тент");
        transports.add("Изотерм");
        transports.add("Автосцепка");
        transports.add("Jumbo");
        transports.add("Контейнеровоз");
        transports.add("Открытая бортовая платформа");
        transports.add("Открытая платформа");
        transports.add("Автоцистерна");
        transports.add("Микроавтобус");
        transports.add("Автовоз");
        transports.add("Зерновоз");
        transports.add("Самосвал");
        transports.add("Лесовоз");

        filter = new Filter();

        loadList.addView(getPlaceAutoCompleteTxtv(RouteItem.Type.LOADING_PLACE));
        unloadList.addView(getPlaceAutoCompleteTxtv(RouteItem.Type.UNLOADING_PLACE));

        Drawable transparentDrawable = new ColorDrawable(Color.TRANSPARENT);
        transportSpinner.setBackground(transparentDrawable);

        transportArrayAdapter = new SpinnerArrayAdapter(getContext(), transports, true);

        transportSpinner.setAdapter(transportArrayAdapter);
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
        datesTxtv.setText(String.format("%s - %s", format.format(startDate.getTime()), format.format(endDate.getTime())));
    }


}
