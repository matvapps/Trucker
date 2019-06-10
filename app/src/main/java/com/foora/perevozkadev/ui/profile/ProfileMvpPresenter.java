package com.foora.perevozkadev.ui.profile;

import com.foora.perevozkadev.ui.base.MvpPresenter;
import com.foora.perevozkadev.ui.profile.model.Profile;

import java.io.File;

/**
 * Created by Alexandr.
 */
public interface ProfileMvpPresenter<V extends ProfileMvpView> extends MvpPresenter<V> {

    void changeProfile(Profile profile);
    void getProfile();
    void getMyOrders();
    void getMyTransport();
    void verifyUser(File file);

}
