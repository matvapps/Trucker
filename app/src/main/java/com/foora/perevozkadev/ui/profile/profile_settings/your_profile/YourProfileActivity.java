package com.foora.perevozkadev.ui.profile.profile_settings.your_profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
import com.foora.perevozkadev.ui.profile.profile_settings.ProfileEditCloseFragment;
import com.foora.perevozkadev.ui.profile.profile_settings.ProfileSettingsMvpPresenter;
import com.foora.perevozkadev.ui.profile.profile_settings.ProfileSettingsMvpView;
import com.foora.perevozkadev.ui.profile.profile_settings.ProfileSettingsPresenter;
import com.foora.perevozkadev.ui.profile.profile_settings.general_info.GeneralInfoActivity;
import com.github.matvapps.AppEditText;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class YourProfileActivity extends BasePresenterActivity<ProfileSettingsMvpPresenter>
        implements ProfileSettingsMvpView, View.OnClickListener, ProfileEditCloseFragment.Callback {

    public static final String TAG = GeneralInfoActivity.class.getSimpleName();

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, YourProfileActivity.class);
        activity.startActivity(intent);
    }

    private View btnBack;
    private View btnDone;

    private AppEditText profileName;
    private AppEditText descriptionTxtv;

    private Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_profile);

        btnBack = findViewById(R.id.btn_back);
        btnDone = findViewById(R.id.action_done);

        profileName = findViewById(R.id.name_edtxt);
        descriptionTxtv = findViewById(R.id.description_profile);

        btnBack.setOnClickListener(v -> ProfileEditCloseFragment.newInstance().show(getSupportFragmentManager()));
        btnDone.setOnClickListener(this);

        getPresenter().getProfile();

    }

    public void done() {
        String description = descriptionTxtv.getText().toString();
        String name = profileName.getText();

        if (!description.isEmpty()) {
            profile.setDescription(description);
        }

        if (!name.isEmpty())
            profile.setProfileName(name);

        getPresenter().changeProfile(profile);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
        descriptionTxtv.setText(profile.getDescription());
        profileName.setText(profile.getProfileName());

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

    @Override
    public void onFileUploaded(ProfileSettingsPresenter.PhotoType type, File file) {

    }


    @Override
    public void onBackPressed() {
        ProfileEditCloseFragment.newInstance().show(getSupportFragmentManager());
    }

    @Override
    public void onSaveAndExit() {
        finish();
    }

    @Override
    public void onDiscardAndExit() {
        done();
    }

}
