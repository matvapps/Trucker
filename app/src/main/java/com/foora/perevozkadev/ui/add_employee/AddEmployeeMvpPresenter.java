package com.foora.perevozkadev.ui.add_employee;

import com.foora.perevozkadev.ui.base.MvpPresenter;
import com.foora.perevozkadev.ui.profile.model.Profile;

/**
 * Created by Alexandr.
 */
public interface AddEmployeeMvpPresenter<V extends AddEmployeeMvpView> extends MvpPresenter<V> {
    void addEmployee(String login,
                     String password,
                     String email,
                     String phone,
                     String userType,
                     String firstName,
                     String lastName,
                     String group);

    void changeEmployee(Profile profile);
}
