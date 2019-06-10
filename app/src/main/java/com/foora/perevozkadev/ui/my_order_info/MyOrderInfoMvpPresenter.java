package com.foora.perevozkadev.ui.my_order_info;

import com.foora.perevozkadev.ui.base.MvpPresenter;

/**
 * Created by Alexandr.
 */
public interface MyOrderInfoMvpPresenter<V extends MyOrderInfoMvpView> extends MvpPresenter<V> {
    void getOrderById(int id);
    void changeOrderStatus(int orderId, String status);
    void getProfile();
    void sendSOS(double latitude, double longitude);
}
