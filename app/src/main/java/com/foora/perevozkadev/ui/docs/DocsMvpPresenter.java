package com.foora.perevozkadev.ui.docs;

import com.foora.perevozkadev.ui.base.MvpPresenter;

import java.io.File;

/**
 * Created by Alexandr.
 */
public interface DocsMvpPresenter<V extends DocsMvpView> extends MvpPresenter<V> {
    void addFileToOrder(int orderId, File file);
    void getOrderFiles(int orderId);
}
