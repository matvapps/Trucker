package com.foora.perevozkadev.ui.add_transport;

import com.foora.perevozkadev.ui.base.MvpPresenter;
import com.foora.perevozkadev.ui.my_transport.model.Transport;

import java.io.File;

/**
 * Created by Alexandr.
 */
public interface AddTransportMvpPresenter<V extends AddTransportMvpView> extends MvpPresenter<V> {

    void addTransport(Transport transport);
    void addPhotoToTransport(File photo, int transportId);

}
