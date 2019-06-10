package com.foora.perevozkadev.ui.profile.profile_settings.use_2fa;

import com.foora.perevozkadev.ui.base.MvpView;
import com.foora.perevozkadev.ui.profile.model.Profile;

/**
 * Created by Alexandr.
 */
public interface EnableSmsMvpView extends MvpView {

    void onGetProfile(Profile profile);
    void onIs2FaChanged();

}
