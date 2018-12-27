package com.foora.perevozkadev.ui.my_transport;

import com.foora.perevozkadev.ui.base.MvpView;
import com.foora.perevozkadev.ui.my_transport.model.Transport;

import java.util.List;

/**
 * Created by Alexandr.
 */
public interface MyTransportMvpView extends MvpView {

    void onGetTransports(List<Transport> transports);

}
