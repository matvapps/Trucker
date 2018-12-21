package com.foora.perevozkadev.ui.entry;


import com.foora.perevozkadev.ui.base.MvpView;

/**
 * Created by Alexandr.
 */
public interface EntryMvpView extends MvpView {

    void showLoginFragment();

    void showRegisterFragment();

    void showConfirmFragment();

    void showOtpcodeFragment(String fromType);

    void onReceiveUserId(int userId);

    void openMainActivity();

    void onConfirmAuth(String phone);

    void onSmsSend();

}
