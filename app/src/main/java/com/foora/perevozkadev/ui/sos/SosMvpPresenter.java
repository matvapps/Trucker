package com.foora.perevozkadev.ui.sos;

import com.foora.perevozkadev.ui.base.MvpPresenter;

/**
 * Created by Alexandr.
 */
public interface SosMvpPresenter<V extends SosMvpView> extends MvpPresenter<V> {

    void acceptSos(int sos_id);
    void getProfile(int id);

}
