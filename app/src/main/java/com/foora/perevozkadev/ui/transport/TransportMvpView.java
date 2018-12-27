package com.foora.perevozkadev.ui.transport;

import com.foora.perevozkadev.ui.base.MvpView;
import com.foora.perevozkadev.ui.my_transport.model.Transport;

/**
 * Created by Alexandr.
 */
public interface TransportMvpView extends MvpView {
    void onGetTransport(Transport transport);
}
