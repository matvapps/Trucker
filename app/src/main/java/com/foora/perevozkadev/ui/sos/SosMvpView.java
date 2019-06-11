package com.foora.perevozkadev.ui.sos;

import com.foora.perevozkadev.ui.base.MvpView;
import com.foora.perevozkadev.ui.profile.model.Profile;

/**
 * Created by Alexandr.
 */
public interface SosMvpView extends MvpView {

    void onGetProfile(Profile profile);

}
