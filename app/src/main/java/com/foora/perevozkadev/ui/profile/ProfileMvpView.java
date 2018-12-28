package com.foora.perevozkadev.ui.profile;

import com.foora.perevozkadev.ui.profile.model.Profile;
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.base.MvpView;
import com.foora.perevozkadev.ui.my_transport.model.Transport;

import java.util.List;

/**
 * Created by Alexandr.
 */
public interface ProfileMvpView extends MvpView {

    void onGetProfile(Profile profile);
    void onGetUserOrders(List<Order> orderList);
    void onGetUserTransport(List<Transport> transports);

}
