package com.foora.perevozkadev.ui.profile.profile_settings;

import com.foora.perevozkadev.ui.base.MvpView;
import com.foora.perevozkadev.ui.profile.model.Profile;

import java.io.File;

/**
 * Created by Alexandr.
 */
public interface ProfileSettingsMvpView extends MvpView {

    void onGetProfile(Profile profile);
    void onChangeProfile();
    void onChangePassword(String response);
    void onSmsSend();
    void onChangePhone();
    void onFileUploaded(ProfileSettingsPresenter.PhotoType type, File file);

}
