package com.foora.perevozkadev.ui.my_order_info;

import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.base.MvpView;

/**
 * Created by Alexandr.
 */
public interface MyOrderInfoMvpView extends MvpView {
    void onGetOrder(Order order);
}
