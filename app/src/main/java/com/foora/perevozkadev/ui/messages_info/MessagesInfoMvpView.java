package com.foora.perevozkadev.ui.messages_info;

import com.foora.perevozkadev.data.network.model.OrderRequest;
import com.foora.perevozkadev.ui.base.MvpView;
import com.foora.perevozkadev.ui.my_transport.model.Transport;
import com.foora.perevozkadev.ui.profile.model.Profile;

/**
 * Created by Alexandr.
 */
public interface MessagesInfoMvpView extends MvpView {
    void onGetRequestInfo(OrderRequest orderRequest);
    void onGetTransport(Transport transport);
    void onGetProfile(Profile profile);
    void onRejectRequest();
    void onConfirmRequest();
}
