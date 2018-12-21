package com.foora.perevozkadev.ui.add_order;


import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.base.MvpPresenter;

/**
 * Created by Alexandr.
 */
public interface AddOrderMvpPresenter<V extends AddOrderMvpView> extends MvpPresenter<V> {
    void addOrder(Order order);
}
