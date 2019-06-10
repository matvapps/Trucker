package com.foora.perevozkadev.data.prefs;


public interface PrefRepo {

    boolean getUserLogged();
    void setUserLogged(boolean logged);

    String getUserToken();
    void setUserToken(String token);

    void setUserName(String name);
    String getUserName();

    void setUserRole(String role);
    String getUserRole();


    boolean getIs2FaEnabled();
    void set2FaEnabled(boolean enabled);

    void clear();

}
