package com.foora.perevozkadev.ui.profile.profile_settings.change_phone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.chaos.view.PinView;
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
import com.github.matvapps.AppEditText;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class ChangePhoneActivity  extends BasePresenterActivity<ProfileSettingsMvpPresenter> implements ProfileSettingsMvpView, View.OnClickListener {

    public static final String TAG = ChangePhoneActivity.class.getSimpleName();

    private View btnBack;
    private View btnDone;
    private View btnSubmit;

    private AppEditText loginEdtxt;
    private AppEditText passwordEdtxt;
    private AppEditText phoneEdtxt;
    private AppEditText newPhoneEdtxt;

    private View smsCodeContainer;
    private PinView smsCode;

    private String login;
    private String password;
    private String phone;
    private String newPhone;
    private String sms;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, ChangePhoneActivity.class);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone);

        loginEdtxt = findViewById(R.id.login);
        passwordEdtxt = findViewById(R.id.password);
        phoneEdtxt = findViewById(R.id.phone);
        newPhoneEdtxt = findViewById(R.id.new_phone);
        smsCodeContainer = findViewById(R.id.sms_confirm);
        smsCode = findViewById(R.id.pin_view);

        btnBack = findViewById(R.id.btn_back);
        btnDone = findViewById(R.id.action_done);
        btnSubmit = findViewById(R.id.btn_submit);

        smsCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                sms = editable.toString();
            }
        });

        btnSubmit.setOnClickListener(this);
        btnBack.setOnClickListener(v -> finish());
        btnDone.setOnClickListener(this);

    }

    private void submit() {
        if (smsCodeContainer.getVisibility() == View.GONE) {

            login = loginEdtxt.getText().toString();
            password = passwordEdtxt.getText().toString();
            phone = phoneEdtxt.getText().toString();
            newPhone = newPhoneEdtxt.getText().toString();

            if (login.isEmpty() || password.isEmpty() ||
                    phone.isEmpty() || newPhone.isEmpty()) {
                onError("Заполните все поля");
                return;
            }

            getPresenter().sendSms(login, phone);
        } else {
            if (sms.length() == 4) {
                getPresenter().changePhone(login, password, phone, newPhone, sms);
            } else {
                onError("Неверный формат СМС");
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_done:
                break;
            case R.id.btn_submit:
                submit();
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

    }

    @Override
    public void onChangeProfile() {

    }

    @Override
    public void onChangePassword(String response) {

    }

    @Override
    public void onSmsSend() {
        smsCodeContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onChangePhone() {
        showMessage("Телефон успешно изменен");
        finish();
    }

}
