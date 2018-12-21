package com.foora.perevozkadev.data.prefs;


public interface PreferencesHelper {

    boolean getUserLogged();
    void setUserLogged(boolean logged);

    String getUserToken();
    void setUserToken(String token);

    void clear();

}
