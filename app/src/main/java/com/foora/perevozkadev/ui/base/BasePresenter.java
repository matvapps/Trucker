package com.foora.perevozkadev.ui.base;

import com.foora.perevozkadev.data.DataManager;

import java.lang.ref.WeakReference;

import io.reactivex.Scheduler;

/**
 * Created by Alexandr
 */
public abstract class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private static final String TAG = BasePresenter.class.getSimpleName();

    private final DataManager dataManager;
    private final Scheduler scheduler;
    private WeakReference<V> viewRef;

//    private V mvpView;

    public BasePresenter(DataManager dataManager,
                         Scheduler scheduler) {
        this.dataManager = dataManager;
        this.scheduler = scheduler;
    }

    @Override
    public void onAttach(V mvpView) {
        viewRef = new WeakReference<>(mvpView);
    }

    @Override
    public void onDetach() {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }

    @Override
    public void onResume() {

    }

    public boolean isViewAttached() {
        return viewRef != null && viewRef.get() != null;
    }

    public V getMvpView() {
        return viewRef.get();
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    @Override
    public void handleApiError() {


    }

    @Override
    public void setUserAsLoggedOut() {
        //getDataManager().setAccessToken(null);
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.onAttach(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}
