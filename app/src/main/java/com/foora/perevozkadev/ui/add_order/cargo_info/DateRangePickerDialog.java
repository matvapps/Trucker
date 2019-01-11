package com.foora.perevozkadev.ui.add_order.cargo_info;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;
import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.base.BaseDialog;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Alexandr.
 */
public class DateRangePickerDialog extends BaseDialog {

    public static final String TAG = DateRangePickerDialog.class.getSimpleName();

    @BindView(R.id.calendar)
    DateRangeCalendarView dateRangeCalendarView;

    @BindView(R.id.btn_choose)
    Button btnChoose;


    private Callback listener;

    private Calendar start;
    private Calendar end;

    public static DateRangePickerDialog newInstance() {
        DateRangePickerDialog fragment = new DateRangePickerDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_date_range_picker, container, false);

        setUnBinder(ButterKnife.bind(this, view));

        return view;
    }

    @Override
    protected void setUp(View view) {
        dateRangeCalendarView.setCalendarListener(new DateRangeCalendarView.CalendarListener() {
            @Override
            public void onFirstDateSelected(Calendar startDate) {

            }

            @Override
            public void onDateRangeSelected(Calendar startDate, Calendar endDate) {
                start = startDate;
                end = endDate;
            }
        });
    }

    @OnClick(R.id.btn_choose)
    void onClick() {
        if (start != null && end != null) {
            listener.onRangeSelected(start, end);
            dismiss();
        } else {
            showErrorMessage("Нужно выбрать две даты");
        }

        start = null; end = null;
    }

    public interface Callback {
        void onRangeSelected(Calendar startDate, Calendar endDate);
    }

    public Callback getListener() {
        return listener;
    }

    public void setListener(Callback listener) {
        this.listener = listener;
    }
}
