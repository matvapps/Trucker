package com.foora.perevozkadev.ui.profile;

import com.foora.perevozkadev.ui.base.MvpPresenter;
import com.foora.perevozkadev.ui.profile.model.Profile;

/**
 * Created by Alexandr.
 */
public interface ProfileMvpPresenter<V extends ProfileMvpView> extends MvpPresenter<V> {

    void changeProfile(Profile profile);
    void getProfile();
    void getMyOrders();
    void getMyTransport();

}
