package com.foora.perevozkadev.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;


public class PrefRepoImpl implements PrefRepo {

    private static final String PREF_KEY_USER_LOGGED = "PREF_KEY_USER_LOGGED";
    private static final String PREF_USER_TOKEN = "PREF_USER_TOKEN";
    private static final String PREF_USER_NAME = "PREF_USER_NAME";
    private static final String PREF_USER_ROLE = "PREF_USER_ROLE";
    private static final String PREF_IS2FAENABLED = "PREF_IS2FAENABLED";

    private final SharedPreferences prefs;

    public PrefRepoImpl(Context context) {
        prefs = context.getSharedPreferences("carriers_info", Context.MODE_PRIVATE);
    }

    @Override
    public boolean getUserLogged() {
        return prefs.getBoolean(PREF_KEY_USER_LOGGED, false);
    }

    @Override
    public void setUserLogged(boolean logged) {
        prefs.edit().putBoolean(PREF_KEY_USER_LOGGED, logged).apply();
    }

    @Override
    public String getUserToken() {
        return "token " + prefs.getString(PREF_USER_TOKEN, "");
    }

    @Override
    public void setUserToken(String token) {
        prefs.edit().putString(PREF_USER_TOKEN, token).apply();
    }

    @Override
    public void setUserName(String name) {
        prefs.edit().putString(PREF_USER_NAME, name).apply();

    }

    @Override
    public String getUserName() {
        return prefs.getString(PREF_USER_NAME, "");

    }

    @Override
    public void setUserRole(String role) {
        prefs.edit().putString(PREF_USER_ROLE, role).apply();

    }

    @Override
    public String getUserRole() {
        return prefs.getString(PREF_USER_ROLE, "");
    }

    @Override
    public boolean getIs2FaEnabled() {
        return prefs.getBoolean(PREF_IS2FAENABLED, true);
    }

    @Override
    public void set2FaEnabled(boolean enabled) {
        prefs.edit().putBoolean(PREF_IS2FAENABLED, enabled).apply();
    }

    @Override
    public void clear() {
        prefs.edit().clear().apply();
    }

}
