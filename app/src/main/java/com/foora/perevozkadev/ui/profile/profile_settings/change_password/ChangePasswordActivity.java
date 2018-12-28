package com.foora.perevozkadev.ui.profile.profile_settings.change_password;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.chaos.view.PinView;
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

import io.reactivex.android.schedulers.AndroidSchedulers;

public class ChangePasswordActivity extends BasePresenterActivity<ProfileSettingsMvpPresenter> implements ProfileSettingsMvpView, View.OnClickListener {

    public static final String TAG = ChangePasswordActivity.class.getSimpleName();

    private View btnBack;
    private View btnDone;
    private View btnSubmit;

    private EditText loginEdtxt;
    private EditText oldPasswordEdtxt;
    private EditText newPasswordEdtxt;
    private EditText repeatPasswordEdtxt;

    private View smsCodeContainer;
    private PinView smsCode;


    public static void start(Activity activity) {
        Intent intent = new Intent(activity, ChangePasswordActivity.class);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        loginEdtxt = findViewById(R.id.login);
        oldPasswordEdtxt = findViewById(R.id.old_password);
        newPasswordEdtxt = findViewById(R.id.new_password);
        repeatPasswordEdtxt = findViewById(R.id.repeat_password);
        smsCodeContainer = findViewById(R.id.sms_confirm);
        smsCode = findViewById(R.id.pin_view);

        btnBack = findViewById(R.id.btn_back);
        btnDone = findViewById(R.id.action_done);
        btnSubmit = findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(this);
        btnBack.setOnClickListener(v -> finish());
        btnDone.setOnClickListener(this);

    }

    private void submit() {
        String login = loginEdtxt.getText().toString();
        String oldPassword = oldPasswordEdtxt.getText().toString();
        String newPassword = newPasswordEdtxt.getText().toString();
        String repeatPassword = repeatPasswordEdtxt.getText().toString();

        if (login.isEmpty() || oldPassword.isEmpty() ||
                newPassword.isEmpty() || repeatPassword.isEmpty()) {
            onError("Заполните все поля");
            return;
        }

        if (!newPassword.equals(repeatPassword)) {
            onError("Пароли не совпадают");
            return;
        }

        getPresenter().changePassword(login, oldPassword, newPassword);

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
        PreferencesHelper prefs = new SharedPrefsHelper(this);

        DataManager dataManager = new DataManagerImpl(remoteRepo, prefs);

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
        showMessage("Пароль успешно изменен");
        finish();

    }

    @Override
    public void onSmsSend() {

    }

    @Override
    public void onChangePhone() {

    }

}
