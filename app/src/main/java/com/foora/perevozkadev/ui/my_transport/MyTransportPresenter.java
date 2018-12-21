package com.foora.perevozkadev.ui.my_transport;

import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.ui.base.BasePresenter;

import io.reactivex.Scheduler;

/**
 * Created by Alexandr.
 */
public class MyTransportPresenter<V extends MyTransportMvpView> extends BasePresenter<V> implements MyTransportMvpPresenter<V> {

    public static final String TAG = MyTransportPresenter.class.getSimpleName();

    public MyTransportPresenter(DataManager dataManager, Scheduler scheduler) {
        super(dataManager, scheduler);
    }
}
