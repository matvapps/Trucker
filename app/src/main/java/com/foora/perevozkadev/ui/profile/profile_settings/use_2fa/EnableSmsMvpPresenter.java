package com.foora.perevozkadev.ui.profile.profile_settings.use_2fa;

import com.foora.perevozkadev.ui.base.MvpPresenter;

/**
 * Created by Alexandr.
 */
public interface EnableSmsMvpPresenter<V extends EnableSmsMvpView> extends MvpPresenter<V> {

    void set2FaEnabled(int enabled);
    void getProfile();

}
