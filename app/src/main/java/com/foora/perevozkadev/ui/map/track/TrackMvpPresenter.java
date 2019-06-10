package com.foora.perevozkadev.ui.map.track;

import com.foora.perevozkadev.ui.base.MvpPresenter;

/**
 * Created by Alexandr.
 */
public interface TrackMvpPresenter<V extends TrackMvpView> extends MvpPresenter<V> {

    void trackOrder(int orderId);
    void getOrderById(int orderId);

}
