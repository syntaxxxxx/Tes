package fendri.inovatif.latihan.helper;

import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    String ID = "ID";
    String USER_EMAIL = "EMAIL";
    String PREF_NAME = "LOGIN_USER";
    String IS_LOGIN = "IS_LOGIN";

    // constructor
    public SessionManager(Context ctx){
        preferences= ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public String getID() {
        return preferences.getString(ID, null);
    }

    public void setID(String usrID) {
        editor = preferences.edit();
        editor.putString(ID, usrID).apply();
    }

    public String getUSER_EMAIL() {
        return preferences.getString(USER_EMAIL, null);
    }

    public void setUSER_EMAIL(String usrEmail) {
        editor = preferences.edit();
        editor.putString(USER_EMAIL, usrEmail).apply();
    }

    public boolean isLogin() {
        return preferences.getBoolean(IS_LOGIN, false);
    }

    public void setLogin(boolean isLogin) {
        editor = preferences.edit();
        editor.putBoolean(IS_LOGIN, isLogin).apply();
    }

    public void logout() {
        editor = preferences.edit();
        editor.clear().apply();
    }
}
