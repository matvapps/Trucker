package com.foora.perevozkadev.ui.profile;

import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.ui.base.BasePresenter;

import io.reactivex.Scheduler;

/**
 * Created by Alexandr.
 */
public class ProfilePresenter<V extends ProfileMvpView> extends BasePresenter<V> implements ProfileMvpPresenter<V> {

    public static final String TAG = ProfilePresenter.class.getSimpleName();

    public ProfilePresenter(DataManager dataManager, Scheduler scheduler) {
        super(dataManager, scheduler);
    }

    @Override
    public void getProfile() {

    }

    @Override
    public void getMyOrders() {

    }

    @Override
    public void getMyTransport() {

    }
}
