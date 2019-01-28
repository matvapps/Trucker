package com.foora.perevozkadev.ui.map.calculate;

import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.ui.base.BasePresenter;

import io.reactivex.Scheduler;

/**
 * Created by Alexandr.
 */
public class CalcDistancePresenter<V extends CalcDistanceMvpView> extends BasePresenter<V> implements CalcDistanceMvpPresenter<V> {

    public static final String TAG = CalcDistancePresenter.class.getSimpleName();

    public CalcDistancePresenter(DataManager dataManager, Scheduler scheduler) {
        super(dataManager, scheduler);
    }
}
