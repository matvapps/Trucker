package com.foora.perevozkadev.ui.messages;

import com.foora.perevozkadev.data.network.model.OrderRequest;
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.base.MvpView;

import java.util.List;

/**
 * Created by Alexandr.
 */
public interface MessagesMvpView extends MvpView {
    void onGetUserRequests(List<OrderRequest> orderRequests);
    void onGetOrderByRequest(OrderRequest orderRequest, Order order);
}
