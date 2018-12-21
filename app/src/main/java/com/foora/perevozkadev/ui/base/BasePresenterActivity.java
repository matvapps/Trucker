package com.foora.perevozkadev.ui.base;

import android.support.annotation.NonNull;

/**
 * Created by Alexandr
 */
public abstract class BasePresenterActivity<T extends MvpPresenter> extends BaseActivity {

    public static final String TAG = BasePresenterActivity.class.getSimpleName();


    private T presenter;


    @NonNull
    protected T getPresenter() {
        if (presenter == null)
            presenter = createPresenter();
        if (presenter == null) {
            throw new IllegalStateException("createPresenter() implementation returns null!");
        }
        return presenter;
    }

    protected abstract T createPresenter();

}
