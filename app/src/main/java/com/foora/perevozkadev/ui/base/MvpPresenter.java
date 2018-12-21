package com.foora.perevozkadev.ui.base;


/**
 * Created by Alexandr
 */
public interface MvpPresenter<V extends MvpView> {

    void onAttach(V mvpView);

    void onDetach();

    void onResume();

    void handleApiError();

    void setUserAsLoggedOut();

}