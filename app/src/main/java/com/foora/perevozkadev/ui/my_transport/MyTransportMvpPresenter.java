package com.foora.perevozkadev.ui.my_transport;

import com.foora.perevozkadev.ui.base.MvpPresenter;

/**
 * Created by Alexandr.
 */
public interface MyTransportMvpPresenter<V extends MyTransportMvpView> extends MvpPresenter<V> {

    void getUserTransport();
    void addTransportToArchive(int transportId);
    void removeTransportFromArchive(int transportId);
    void getTransportArchive();

}
