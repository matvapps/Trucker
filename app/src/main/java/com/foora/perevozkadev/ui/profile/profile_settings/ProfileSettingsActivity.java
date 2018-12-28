package com.foora.perevozkadev.ui.profile.profile_settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.base.BaseActivity;
import com.foora.perevozkadev.ui.profile.profile_settings.change_password.ChangePasswordActivity;
import com.foora.perevozkadev.ui.profile.profile_settings.change_phone.ChangePhoneActivity;
import com.foora.perevozkadev.ui.profile.profile_settings.general_info.GeneralInfoActivity;
import com.foora.perevozkadev.ui.profile.profile_settings.register_info.RegisterInfoActivity;
import com.foora.perevozkadev.ui.profile.profile_settings.your_profile.YourProfileActivity;

public class ProfileSettingsActivity extends BaseActivity implements View.OnClickListener{

    public static final String TAG = ProfileSettingsActivity.class.getSimpleName();

    private View btnBack;

    private View generalInfo;
    private View registerInfo;
    private View yourProfile;
    private View yourPocket;
    private View langAndCurrency;
    private View smsNotification;
    private View changePassword;
    private View changePhone;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, ProfileSettingsActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);

        generalInfo = findViewById(R.id.general_info);
        registerInfo = findViewById(R.id.register_info);
        yourProfile = findViewById(R.id.your_profile);
        yourPocket = findViewById(R.id.your_pocket);
        langAndCurrency = findViewById(R.id.lang_and_currency);
        smsNotification = findViewById(R.id.sms_notification);
        changePassword = findViewById(R.id.change_password);
        changePhone = findViewById(R.id.change_phone);

        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());

        generalInfo.setOnClickListener(this);
        registerInfo.setOnClickListener(this);
        yourProfile.setOnClickListener(this);
        yourPocket.setOnClickListener(this);
        langAndCurrency.setOnClickListener(this);
        smsNotification.setOnClickListener(this);
        changePassword.setOnClickListener(this);
        changePhone.setOnClickListener(this);
    }


    @Override
    protected void setUp() {


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.general_info:
                GeneralInfoActivity.start(this);
                break;
            case R.id.register_info:
                RegisterInfoActivity.start(this);
                break;
            case R.id.your_profile:
                YourProfileActivity.start(this);
                break;
            case R.id.your_pocket:

                break;
            case R.id.lang_and_currency:

                break;
            case R.id.sms_notification:

                break;
            case R.id.change_password:
                ChangePasswordActivity.start(this);
                break;
            case R.id.change_phone:
                ChangePhoneActivity.start(this);
                break;
        }
    }
}
