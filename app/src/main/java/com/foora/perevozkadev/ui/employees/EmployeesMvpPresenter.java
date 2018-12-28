package com.foora.perevozkadev.ui.employees;

import com.foora.perevozkadev.ui.base.MvpPresenter;

/**
 * Created by Alexandr.
 */
public interface EmployeesMvpPresenter<V extends EmployeesMvpView> extends MvpPresenter<V> {

    void getEmployees();

}
