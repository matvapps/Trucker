package com.foora.perevozkadev.ui.messages;

import com.foora.perevozkadev.data.network.model.OrderRequest;
import com.foora.perevozkadev.ui.base.MvpPresenter;

/**
 * Created by Alexandr.
 */
public interface MessagesMvpPresenter<V extends MessagesMvpView> extends MvpPresenter<V> {
    void getUserRequests();
    void getToUserRequests();
    void getOrderByRequest(OrderRequest orderRequest);
}
