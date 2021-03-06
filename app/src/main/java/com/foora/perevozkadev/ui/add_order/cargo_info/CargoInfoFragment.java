package com.foora.perevozkadev.ui.add_order.cargo_info;

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
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.base.BaseFragment;
import com.foora.perevozkadev.utils.ViewUtils;
import com.foora.perevozkadev.utils.custom.CustomSpinner;
import com.foora.perevozkadev.utils.custom.SpinnerArrayAdapter;
import com.foora.perevozkadev.utils.custom.tnvd.CargoAutoCompleteTextView;
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
public class CargoInfoFragment extends BaseFragment implements DateRangePickerDialog.Callback {

    public static final String TAG = CargoInfoFragment.class.getSimpleName();

//    @BindView(R.id.date_range_container)
//    View dateRangeContainer;

    private DateRangePickerDialog dateRangePickerDialog;
    private Callback listener;

    @BindView(R.id.txtv_dates)
    TextView datesTxtv;
    @BindView(R.id.spinner_mass_from)
    AppEditText massFrom;
    //    @BindView(R.id.spinner_mass_to)
//    AppEditText massTo;
    @BindView(R.id.spinner_volume_from)
    AppEditText volumeFrom;
    //    @BindView(R.id.spinner_volume_to)
//    AppEditText volumeTo;
    @BindView(R.id.spinner_container)
    LinearLayout spinnerContainer;
    @BindView(R.id.transport_spinner)
    CustomSpinner transportSpinner;
    @BindView(R.id.payment_type)
    CustomSpinner paymentSpinner;
    @BindView(R.id.add_transport)
    View addTransport;
    @BindView(R.id.edtxt_cost)
    AppEditText costEdtxt;
    @BindView(R.id.spinner_currency)
    CustomSpinner currencySpinner;
    @BindView(R.id.edtxt_car_quantity)
    AppEditText carQuantEdtxt;
    @BindView(R.id.edtxt_width)
    AppEditText widthEdtxt;
    @BindView(R.id.edtxt_height)
    AppEditText heightEdtxt;
    @BindView(R.id.edtxt_depth)
    AppEditText depthEdtxt;
    @BindView(R.id.additional_info)
    AppEditText addInfoEdtxt;
    @BindView(R.id.edtxt_docs)
    AppEditText edtxtDocs;
    @BindView(R.id.cargo_type)
    CargoAutoCompleteTextView cargoAutoCompleteTextView;


    private SpinnerArrayAdapter transportArrayAdapter;
    private SpinnerArrayAdapter costArrayAdapter;
    private SpinnerArrayAdapter paymentTypeAdapter;
    private ArrayList<String> transports;

    public static CargoInfoFragment newInstance() {
        Bundle args = new Bundle();
        CargoInfoFragment fragment = new CargoInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private String dateStart;
    private String dateEnd;
    private float massFromNum;
    //    private float massToNum;
    private float volumeFromNum;
    //    private float volumeToNum;
    private List<String> transportType;
    private float cost;
    private String currency;
    private int carQuant;
    private float width;
    private float height;
    private float depth;
    private String paymentType;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cargo_info, container, false);
        setUnBinder(ButterKnife.bind(this, rootView));

        hideKeyboard();

        return rootView;
    }

    @OnClick(R.id.txtv_dates)
    void onDateRangeClick() {
        showDateRangePicker();
    }

    @OnClick(R.id.add_transport)
    void addTransport() {
        CustomSpinner spinner = new CustomSpinner(getContext());
        Drawable transparentDrawable = new ColorDrawable(Color.TRANSPARENT);
        spinner.setBackground(transparentDrawable);
        spinner.setDropDownVerticalOffset(ViewUtils.dpToPx(60));

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewUtils.dpToPx(60));
        layoutParams.setMargins(0, ViewUtils.dpToPx(8), 0, 0);

        spinner.setLayoutParams(layoutParams);


        SpinnerArrayAdapter spinnerArrayAdapter = new SpinnerArrayAdapter(getContext(), transports, true);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                view.setActivated(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinner.setActivated(false);
            }
        });

        spinnerContainer.addView(spinner);
    }

    void showDateRangePicker() {
        dateRangePickerDialog.show(getFragmentManager(), DateRangePickerDialog.TAG);
    }

    @OnClick(R.id.btn_main)
    void onClick() {
        if (listener != null) {

            if (datesTxtv.getText().toString().isEmpty()) {
                onError("Выберите даты");
                return;
            }
            String[] dates = datesTxtv.getText().toString().split("-");

            String docsString = edtxtDocs.getText();

            //read dates
            dateStart = dates[0].replaceAll("\\.", "-");
            dateStart = dateStart.replaceAll("\\s", "");

            if (dates.length > 1) {
                dateEnd = dates[1].replaceAll("\\.", "-");
                dateEnd = dateEnd.replaceAll("\\s", "");
            }

            Log.d(TAG, "onClick: " + dateStart);
            Log.d(TAG, "onClick: " + dateEnd);


            if (massFrom.getText().isEmpty() ||
                    volumeFrom.getText().isEmpty() ||
                    costEdtxt.getText().isEmpty() ||
                    carQuantEdtxt.getText().isEmpty() ||
                    widthEdtxt.getText().isEmpty() ||
                    heightEdtxt.getText().isEmpty() ||
                    depthEdtxt.getText().isEmpty() ||
                    addInfoEdtxt.getText().isEmpty()) {

                onError("Заполните все поля");
                return;
            }

            if (cargoAutoCompleteTextView.getText().isEmpty()) {
                onError("Заполните все поля");
                return;
            }

            massFromNum = Float.parseFloat(massFrom.getText());

            volumeFromNum = Float.parseFloat(volumeFrom.getText());

            // read transport types
            for (int i = 0; i < spinnerContainer.getChildCount(); i++) {
                CustomSpinner spinner = (CustomSpinner) spinnerContainer.getChildAt(i);
                transportType.add((String) spinner.getSelectedItem());
            }

            // read cost and currency
            cost = Float.parseFloat(costEdtxt.getText().toString());
            currency = (String) currencySpinner.getSelectedItem();
            paymentType = (String) paymentSpinner.getSelectedItem();

            carQuant = Integer.parseInt(carQuantEdtxt.getText());
            width = Float.parseFloat(widthEdtxt.getText());
            height = Float.parseFloat(heightEdtxt.getText());
            depth = Float.parseFloat(depthEdtxt.getText());

            long cargoId = cargoAutoCompleteTextView.getSelectedItem().getCargoId();

            if (dateEnd == null)
                dateEnd = dateStart;

            listener.onReceiveCargoInfo(dateStart, dateEnd, massFromNum,
                    0, volumeFromNum, 0,
                    transportType, cost, currency,
                    carQuant, width, height,
                    depth, paymentType,
                    addInfoEdtxt.getText(), cargoId, docsString);

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (Callback) context;
    }

    public Callback getListener() {
        return listener;
    }

    public void setListener(Callback listener) {
        this.listener = listener;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }


    @Override
    protected void setUp(View view) {
        dateRangePickerDialog = new DateRangePickerDialog();
        dateRangePickerDialog.setListener(this);

        transportType = new ArrayList<>();
        Drawable transparentDrawable = new ColorDrawable(Color.TRANSPARENT);

        transports = new ArrayList<String>();
        String[] transportArr = getContext().getResources().getStringArray(R.array.transport_types);
        transports.addAll(Arrays.asList(transportArr));

        ArrayList<String> cost = new ArrayList<String>();
        cost.add("USD");
        cost.add("RUB");
        cost.add("EUR");

        ArrayList<String> paymentType = new ArrayList<>();
        paymentType.add("Наличные");
        paymentType.add("На карту");
        paymentType.add("После получения");

        transportArrayAdapter = new SpinnerArrayAdapter(getContext(), transports, true);
        costArrayAdapter = new SpinnerArrayAdapter(getContext(), cost, false);
        paymentTypeAdapter = new SpinnerArrayAdapter(getContext(), paymentType, false);

        transportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                view.setActivated(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                transportSpinner.setActivated(false);
            }
        });

        currencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                view.setActivated(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                currencySpinner.setActivated(false);
            }
        });

        paymentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                view.setActivated(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                paymentSpinner.setActivated(false);
            }
        });

        transportSpinner.setBackground(transparentDrawable);
        currencySpinner.setBackground(transparentDrawable);

        transportSpinner.setAdapter(transportArrayAdapter);
        currencySpinner.setAdapter(costArrayAdapter);
        paymentSpinner.setAdapter(paymentTypeAdapter);


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
        void onReceiveCargoInfo(String dateStart, String dateEnd,
                                float massFrom, float massTo,
                                float volumeFrom, float volumeTo,
                                List<String> transportTypes,
                                float cost, String currency,
                                int carQuant, float width,
                                float height, float depth,
                                String paymentType, String additionalInfo,
                                long cargoId, String docs);
    }

}
