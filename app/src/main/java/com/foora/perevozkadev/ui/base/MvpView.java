package com.foora.perevozkadev.ui.base;

import android.support.annotation.StringRes;

/**
 * Created by Alexandr
 */
public interface MvpView {
    void showLoading();

    void hideLoading();

    void openActivityOnTokenExpire();

    void onError(@StringRes int resId);

    void showErrorMessage(String message);

    void onError(String message);

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    boolean isNetworkConnected();

    void hideKeyboard();
}
