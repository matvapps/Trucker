package com.foora.perevozkadev.ui.staff;

import com.foora.perevozkadev.ui.base.MvpView;
import com.foora.perevozkadev.ui.profile.model.Profile;

import java.util.List;

/**
 * Created by Alexandr.
 */
public interface EmployeesMvpView extends MvpView {

    void onReceiveEmployees(List<Profile> profiles);

}
