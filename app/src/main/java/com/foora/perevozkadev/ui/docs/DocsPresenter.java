package com.foora.perevozkadev.ui.docs;

import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.ui.base.BasePresenter;

import io.reactivex.Scheduler;

/**
 * Created by Alexandr.
 */
public class DocsPresenter<V extends DocsMvpView> extends BasePresenter<V> implements DocsMvpPresenter<V> {

    public static final String TAG = DocsPresenter.class.getSimpleName();

    public DocsPresenter(DataManager dataManager, Scheduler scheduler) {
        super(dataManager, scheduler);
    }
}
