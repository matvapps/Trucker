package com.foora.perevozkadev.ui.profile.profile_settings.register_info;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.DataManagerImpl;
import com.foora.perevozkadev.data.db.LocalRepo;
import com.foora.perevozkadev.data.db.LocalRepoImpl;
import com.foora.perevozkadev.data.network.RemoteRepo;
import com.foora.perevozkadev.data.network.RemoteRepoImpl;
import com.foora.perevozkadev.data.prefs.PrefRepo;
import com.foora.perevozkadev.data.prefs.PrefRepoImpl;
import com.foora.perevozkadev.ui.base.BasePresenterActivity;
import com.foora.perevozkadev.ui.profile.model.Profile;
import com.foora.perevozkadev.ui.profile.profile_settings.ProfileSettingsMvpPresenter;
import com.foora.perevozkadev.ui.profile.profile_settings.ProfileSettingsMvpView;
import com.foora.perevozkadev.ui.profile.profile_settings.ProfileSettingsPresenter;
import com.foora.perevozkadev.utils.custom.MyDatePickerFragment;
import com.github.matvapps.AppEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class RegisterInfoActivity extends BasePresenterActivity<ProfileSettingsMvpPresenter> implements ProfileSettingsMvpView, View.OnClickListener {

    public static final String TAG = RegisterInfoActivity.class.getSimpleName();

    private View btnBack;
    private View btnDone;

    private AppEditText certificateNum;
    private AppEditText countryRegister;
    private AppEditText licenseNum;

    private TextView dateTxtv;
    private View dateContainer;

    private Profile profile;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, RegisterInfoActivity.class);
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_info);

        certificateNum = findViewById(R.id.certificate_num);
        countryRegister = findViewById(R.id.country_register);
        licenseNum = findViewById(R.id.license_num);
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

    void done() {
        String certificate = certificateNum.getText().toString();
        String countryreg = countryRegister.getText().toString();
        String license = licenseNum.getText().toString();
        String licenseExpDate = dateTxtv.getText().toString();

        if (!certificate.isEmpty())
            profile.setRegCertificateNum(certificate);
        if (!countryreg.isEmpty())
            profile.setRegisterCountry(countryreg);
        if (!license.isEmpty())
            profile.setLicenseNum(license);
        if (!licenseExpDate.isEmpty())
            profile.setLicenseExpirationDate(licenseExpDate);

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
    protected ProfileSettingsMvpPresenter createPresenter() {
        RemoteRepo remoteRepo = new RemoteRepoImpl();
        PrefRepo prefs = new PrefRepoImpl(this);
        LocalRepo localRepo = new LocalRepoImpl(this);
        DataManager dataManager = new DataManagerImpl(remoteRepo, prefs, localRepo);

        ProfileSettingsMvpPresenter presenter = new ProfileSettingsPresenter(dataManager, AndroidSchedulers.mainThread());
        presenter.onAttach(this);

        return presenter;
    }

    @Override
    protected void setUp() {

    }

    @Override
    public void onGetProfile(Profile profile) {
        certificateNum.setText(profile.getRegCertificateNum());
        countryRegister.setText(profile.getRegisterCountry());
        licenseNum.setText(profile.getLicenseNum());
        dateTxtv.setText(profile.getLicenseExpirationDate());

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
