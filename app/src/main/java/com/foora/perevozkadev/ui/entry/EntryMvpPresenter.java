package com.foora.perevozkadev.ui.entry;


import com.foora.perevozkadev.ui.base.MvpPresenter;

/**
 * Created by Alexandr.
 */
public interface EntryMvpPresenter<V extends EntryMvpView> extends MvpPresenter<V> {

    void onRegisterClick(String login, String password,
                         String phone, String userType, String group);

    void onLoginClick(String login, String password);

    void onConfirmLogin(String login, String password, String smsCode);

    void activate(int userId, String sms_code);

    void sendSms(String login, String phone);

}
