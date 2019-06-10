package com.foora.perevozkadev.ui.sos;

import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.ui.base.BasePresenter;

import io.reactivex.Scheduler;

/**
 * Created by Alexandr.
 */
public class SosPresenter<V extends SosMvpView> extends BasePresenter<V> implements SosMvpPresenter<V> {

    public static final String TAG = SosPresenter.class.getSimpleName();

    public SosPresenter(DataManager dataManager, Scheduler scheduler) {
        super(dataManager, scheduler);
    }
}
