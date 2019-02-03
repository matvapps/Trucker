package com.foora.perevozkadev.ui.choose_transport;

import com.foora.perevozkadev.data.network.model.RequestBody;
import com.foora.perevozkadev.ui.base.MvpPresenter;

/**
 * Created by Alexandr.
 */
public interface ChooseTransportMvpPresenter<V extends ChooseTransportMvpView> extends MvpPresenter<V> {
    void getTransport();
    void sendRequest(int orderId, RequestBody requestBody);
}
