package com.foora.perevozkadev.ui.messages_info;

import com.foora.perevozkadev.ui.base.MvpPresenter;

/**
 * Created by Alexandr.
 */
public interface MessagesInfoMvpPresenter<V extends MessagesInfoMvpView> extends MvpPresenter<V> {
    void getRequestInfo(int requestId);
    void getTransport(int transportId);
    void getProfile();
    void rejectRequest(int requestId);
    void confirmRequest(int requestId);
    void getOrderById(int orderId);

}
