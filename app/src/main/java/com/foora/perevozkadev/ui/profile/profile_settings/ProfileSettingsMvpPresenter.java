package com.foora.perevozkadev.ui.profile.profile_settings;

import com.foora.perevozkadev.ui.base.MvpPresenter;
import com.foora.perevozkadev.ui.profile.model.Profile;

import java.io.File;

/**
 * Created by Alexandr.
 */
public interface ProfileSettingsMvpPresenter<V extends ProfileSettingsMvpView> extends MvpPresenter<V> {

    void getProfile();
    void changeProfile(Profile profile);
    void changePassword(String login, String password, String newPassword);
    void sendSms(String login, String phone);
    void changePhone(String login, String password,
                     String phone, String newPhone,
                     String smsCode);
    void uploadPhoto(ProfileSettingsPresenter.PhotoType type, File file);

}
