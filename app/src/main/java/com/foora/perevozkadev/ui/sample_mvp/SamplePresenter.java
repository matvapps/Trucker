package com.foora.perevozkadev.ui.sample_mvp;

import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.ui.base.BasePresenter;

import io.reactivex.Scheduler;

/**
 * Created by Alexandr.
 */
public class SamplePresenter<V extends SampleMvpView> extends BasePresenter<V> implements SampleMvpPresenter<V> {

    public static final String TAG = SamplePresenter.class.getSimpleName();

    public SamplePresenter(DataManager dataManager, Scheduler scheduler) {
        super(dataManager, scheduler);
    }
}
