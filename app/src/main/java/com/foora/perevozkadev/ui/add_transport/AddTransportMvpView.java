package com.foora.perevozkadev.ui.add_transport;

import com.foora.perevozkadev.ui.base.MvpView;
import com.foora.perevozkadev.ui.my_transport.model.Transport;

/**
 * Created by Alexandr.
 */
public interface AddTransportMvpView extends MvpView {
    void onAddTransport(Transport transport);
    void onAddPhoto();
}
