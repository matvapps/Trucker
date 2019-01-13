package com.foora.perevozkadev.data.prefs;


public interface PrefRepo {

    boolean getUserLogged();
    void setUserLogged(boolean logged);

    String getUserToken();
    void setUserToken(String token);

    void setUserName(String name);
    String getUserName();

    void clear();

}
