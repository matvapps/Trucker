package com.foora.perevozkadev.ui.add_drivers_to_order;

import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.base.MvpView;
import com.foora.perevozkadev.ui.profile.model.Profile;

import java.util.List;

/**
 * Created by Alexandr.
 */
public interface AddDriversToOrderMvpView extends MvpView {

    void onGetStaff(List<Profile> users);
    void onGetMyOrders(List<Order> orders);
    void onGetOrder(Order order);

    void onAddDriversToOrder();

}
