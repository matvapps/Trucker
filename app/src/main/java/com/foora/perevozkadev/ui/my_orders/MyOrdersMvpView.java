package com.foora.perevozkadev.ui.my_orders;

import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.base.MvpView;

import java.util.List;

/**
 * Created by Alexandr.
 */
public interface MyOrdersMvpView extends MvpView {

    void onGetUserOrders(List<Order> orders);
    void onGetExecutorOrders(List<Order> orders);

}
