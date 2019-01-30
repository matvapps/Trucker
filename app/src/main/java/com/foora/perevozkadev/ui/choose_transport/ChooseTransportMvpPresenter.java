package com.foora.perevozkadev.ui.choose_transport;

import com.foora.perevozkadev.ui.base.MvpPresenter;

/**
 * Created by Alexandr.
 */
public interface ChooseTransportMvpPresenter<V extends ChooseTransportMvpView> extends MvpPresenter<V> {
    void getTransport();
}
