package com.foora.perevozkadev.ui.transport;

import com.foora.perevozkadev.ui.base.MvpPresenter;

/**
 * Created by Alexandr.
 */
public interface TransportMvpPresenter<V extends TransportMvpView> extends MvpPresenter<V> {

    void getTransport(int id);

}
