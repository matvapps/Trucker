package com.foora.perevozkadev.ui.choose_transport;

import com.foora.perevozkadev.ui.base.MvpView;
import com.foora.perevozkadev.ui.my_transport.model.Transport;

import java.util.List;

/**
 * Created by Alexandr.
 */
public interface ChooseTransportMvpView extends MvpView {
    void onGetUserTransports(List<Transport> transports);
    void onRequestSuccess();
}
