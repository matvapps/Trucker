package com.foora.perevozkadev.ui.add_drivers_to_order.order_drivers;

import com.foora.perevozkadev.ui.base.MvpPresenter;

import java.util.List;

/**
 * Created by Alexandr.
 */
public interface OrderDriversMvpPresenter<V extends OrderDriversMvpView> extends MvpPresenter<V> {

    void getOrder(int id);

    void removeDriversFromOrder(int orderId, List<Integer> driverIds);
    void getDriversFromOrder(int orderId);

    void getDriverProfile(int userId);

}
