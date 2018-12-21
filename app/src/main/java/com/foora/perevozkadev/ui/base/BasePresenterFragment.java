package com.foora.perevozkadev.ui.base;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by Alexandr.
 */
public abstract class BasePresenterFragment<T extends MvpPresenter> extends BaseFragment {

    public String TAG;

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

    @Override
    protected void setUp(View view) {

    }
}
