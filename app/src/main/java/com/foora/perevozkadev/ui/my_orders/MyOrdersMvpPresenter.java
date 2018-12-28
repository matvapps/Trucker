package com.foora.perevozkadev.ui.my_orders;

import com.foora.perevozkadev.ui.base.MvpPresenter;

/**
 * Created by Alexandr.
 */
public interface MyOrdersMvpPresenter<V extends MyOrdersMvpView> extends MvpPresenter<V> {

    void getUserOrders();

}
