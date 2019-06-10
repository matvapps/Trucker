package com.foora.perevozkadev.ui.employee;

import com.foora.perevozkadev.ui.base.MvpView;
import com.foora.perevozkadev.ui.profile.model.Profile;

/**
 * Created by Alexandr.
 */
public interface EmployeeMvpView extends MvpView {

    void onRemoveUser();
    void onGetProfile(Profile profile);

}
