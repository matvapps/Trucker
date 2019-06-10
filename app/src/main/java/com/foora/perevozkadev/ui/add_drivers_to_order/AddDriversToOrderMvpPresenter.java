package com.foora.perevozkadev.ui.add_drivers_to_order;

import com.foora.perevozkadev.ui.base.MvpPresenter;

import java.util.List;

/**
 * Created by Alexandr.
 */
public interface AddDriversToOrderMvpPresenter<V extends AddDriversToOrderMvpView> extends MvpPresenter<V> {

    void getStaff();
    void getMyOrders();
    void getOrder(int id);

    void addDriversToOrder(int orderId, List<Integer> driverIds);

}
