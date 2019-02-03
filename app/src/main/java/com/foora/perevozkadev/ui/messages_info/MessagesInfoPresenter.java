package com.foora.perevozkadev.ui.messages_info;

import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.ui.base.BasePresenter;

import io.reactivex.Scheduler;

/**
 * Created by Alexandr.
 */
public class MessagesInfoPresenter<V extends MessagesInfoMvpView> extends BasePresenter<V> implements MessagesInfoMvpPresenter<V> {

    public static final String TAG = MessagesInfoPresenter.class.getSimpleName();

    public MessagesInfoPresenter(DataManager dataManager, Scheduler scheduler) {
        super(dataManager, scheduler);
    }
}
