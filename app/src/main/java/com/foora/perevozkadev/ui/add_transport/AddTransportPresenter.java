package com.foora.perevozkadev.ui.add_transport;

import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.ui.base.BasePresenter;

import io.reactivex.Scheduler;

/**
 * Created by Alexandr.
 */
public class AddTransportPresenter<V extends AddTransportMvpView> extends BasePresenter<V> implements AddTransportMvpPresenter<V> {

    public static final String TAG = AddTransportPresenter.class.getSimpleName();

    public AddTransportPresenter(DataManager dataManager, Scheduler scheduler) {
        super(dataManager, scheduler);
    }
}
