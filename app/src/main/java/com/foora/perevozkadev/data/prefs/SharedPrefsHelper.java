package com.foora.perevozkadev.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPrefsHelper implements PreferencesHelper {

    private static final String PREF_KEY_USER_LOGGED = "PREF_KEY_USER_LOGGED";
    private static final String PREF_USER_TOKEN = "PREF_USER_TOKEN";

    private final SharedPreferences prefs;

    public SharedPrefsHelper(Context context) {
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
    public void clear() {
        prefs.edit().clear().apply();
    }

}
