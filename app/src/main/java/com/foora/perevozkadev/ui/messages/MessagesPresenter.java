package com.foora.perevozkadev.ui.messages;

import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.ui.base.BasePresenter;

import io.reactivex.Scheduler;

/**
 * Created by Alexandr.
 */
public class MessagesPresenter<V extends MessagesMvpView> extends BasePresenter<V> implements MessagesMvpPresenter<V> {

    public static final String TAG = MessagesPresenter.class.getSimpleName();

    public MessagesPresenter(DataManager dataManager, Scheduler scheduler) {
        super(dataManager, scheduler);
    }
}
