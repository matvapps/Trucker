package com.foora.perevozkadev.ui.add_drivers_to_order.order_drivers;

import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.base.MvpView;
import com.foora.perevozkadev.ui.profile.model.Profile;

import java.util.List;

/**
 * Created by Alexandr.
 */
public interface OrderDriversMvpView extends MvpView {

    void onGetOrder(Order order);

    void onRemoveDriversFromOrder();
    void onGetDriversFromOrder(List<Integer> driverIds);

    void onGetDriverProfile(Profile user);

}
