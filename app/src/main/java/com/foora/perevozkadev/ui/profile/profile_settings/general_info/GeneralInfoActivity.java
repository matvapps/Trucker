package com.foora.perevozkadev.ui.profile.profile_settings.general_info;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.DataManagerImpl;
import com.foora.perevozkadev.data.network.RemoteRepo;
import com.foora.perevozkadev.data.network.RemoteRepoImpl;
import com.foora.perevozkadev.data.prefs.PreferencesHelper;
import com.foora.perevozkadev.data.prefs.SharedPrefsHelper;
import com.foora.perevozkadev.ui.base.BasePresenterActivity;
import com.foora.perevozkadev.ui.profile.model.Profile;
import com.foora.perevozkadev.ui.profile.profile_settings.ProfileSettingsMvpPresenter;
import com.foora.perevozkadev.ui.profile.profile_settings.ProfileSettingsMvpView;
import com.foora.perevozkadev.ui.profile.profile_settings.ProfileSettingsPresenter;
import com.foora.perevozkadev.utils.custom.MyDatePickerFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class GeneralInfoActivity extends BasePresenterActivity<ProfileSettingsMvpPresenter> implements ProfileSettingsMvpView, View.OnClickListener {

    public static final String TAG = GeneralInfoActivity.class.getSimpleName();

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, GeneralInfoActivity.class);
        activity.startActivity(intent);
    }

    private View btnBack;
    private View btnDone;

    private EditText nameEdtxt;
    private EditText surnameEdtxt;
    private EditText countryEdtxt;
    private EditText passportNumEdtxt;
    private TextView dateTxtv;
    private View dateContainer;

    private Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_info);

        nameEdtxt = findViewById(R.id.name_edtxt);
        surnameEdtxt = findViewById(R.id.surname_edtxt);
        countryEdtxt = findViewById(R.id.country_edtxt);
        passportNumEdtxt = findViewById(R.id.passport_num_edtxt);
        dateTxtv = findViewById(R.id.date);
        dateContainer = findViewById(R.id.date_expired);

        btnBack = findViewById(R.id.btn_back);
        btnDone = findViewById(R.id.action_done);

        btnBack.setOnClickListener(v -> finish());
        btnDone.setOnClickListener(this);
        dateContainer.setOnClickListener(this);
        dateTxtv.setOnClickListener(this);

        getPresenter().getProfile();
    }

    @Override
    protected void setUp() {

    }

    void onDateContainerClick() {

        MyDatePickerFragment datePickerFragment = new MyDatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(), "DATE");
        datePickerFragment.setDateChangeListener(datePicker -> {


            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, datePicker.getYear());
            calendar.set(Calendar.MONTH, datePicker.getMonth());
            calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());

            SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd", Locale.getDefault());
            String dateStr = format.format(calendar.getTime());
            dateTxtv.setText(dateStr);
        });
    }

    @Override
    protected ProfileSettingsMvpPresenter createPresenter() {
        RemoteRepo remoteRepo = new RemoteRepoImpl();
        PreferencesHelper prefs = new SharedPrefsHelper(this);

        DataManager dataManager = new DataManagerImpl(remoteRepo, prefs);

        ProfileSettingsMvpPresenter presenter = new ProfileSettingsPresenter(dataManager, AndroidSchedulers.mainThread());
        presenter.onAttach(this);

        return presenter;
    }

    private void done() {
        String name = nameEdtxt.getText().toString();
        String surname = surnameEdtxt.getText().toString();
        String country = countryEdtxt.getText().toString();
        String passportNum = passportNumEdtxt.getText().toString();
        String date = dateTxtv.getText().toString();

        if (!name.isEmpty()) {
            profile.setFirstName(name);
        }

        if (!surname.isEmpty()) {
            profile.setLastName(surname);
        }

        if (!country.isEmpty()) {
            profile.setCountry(country);
        }

        if (!passportNum.isEmpty()) {
            profile.setPassportNum(passportNum);
        }

        if (!date.isEmpty()) {
            profile.setPassportExpirationDate(date);
        }

        Log.d(TAG, "done: " + profile.toString());

        getPresenter().changeProfile(profile);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.date:
                onDateContainerClick();
                break;
            case R.id.action_done:
                done();
                break;
        }
    }

    @Override
    public void onGetProfile(Profile profile) {
        nameEdtxt.setText(profile.getFirstName());
        surnameEdtxt.setText(profile.getLastName());
        countryEdtxt.setText(profile.getCountry());
        passportNumEdtxt.setText(profile.getPassportNum());
        dateTxtv.setText(profile.getPassportExpirationDate());

        profile.setUserType(null);

        this.profile = profile;

    }

    @Override
    public void onChangeProfile() {
        showMessage("Профиль успешно изменен");
        finish();
    }

    @Override
    public void onChangePassword(String response) {

    }

    @Override
    public void onSmsSend() {

    }

    @Override
    public void onChangePhone() {

    }

}