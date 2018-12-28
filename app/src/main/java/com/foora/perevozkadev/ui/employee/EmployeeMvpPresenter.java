package com.foora.perevozkadev.ui.employee;

import com.foora.perevozkadev.ui.base.MvpPresenter;

/**
 * Created by Alexandr.
 */
public interface EmployeeMvpPresenter<V extends EmployeeMvpView> extends MvpPresenter<V> {

    void removeUser(int userId);

}
