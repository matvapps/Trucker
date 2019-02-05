package com.foora.perevozkadev.ui.docs;

import com.foora.perevozkadev.ui.base.MvpPresenter;

import okhttp3.MultipartBody;
import retrofit2.http.Multipart;

/**
 * Created by Alexandr.
 */
public interface DocsMvpPresenter<V extends DocsMvpView> extends MvpPresenter<V> {
    void addFileToOrder(int orderId, MultipartBody.Part file);
    void getOrderFiles(int orderId);
}
