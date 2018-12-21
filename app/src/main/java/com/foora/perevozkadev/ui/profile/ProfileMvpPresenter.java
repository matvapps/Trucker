package com.foora.perevozkadev.ui.profile;

import com.foora.perevozkadev.ui.base.MvpPresenter;

/**
 * Created by Alexandr.
 */
public interface ProfileMvpPresenter<V extends ProfileMvpView> extends MvpPresenter<V> {

    void getProfile();
    void getMyOrders();
    void getMyTransport();

}
