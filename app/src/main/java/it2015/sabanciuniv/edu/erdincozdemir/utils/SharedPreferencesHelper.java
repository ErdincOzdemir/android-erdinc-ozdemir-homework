package it2015.sabanciuniv.edu.erdincozdemir.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Erdinc on 12.07.2015.
 */
public class SharedPreferencesHelper {

    private SharedPreferences sharedPreferences;

    public SharedPreferencesHelper(Context context) {
        this.sharedPreferences = context.getSharedPreferences(Config.sharedPreferencesName, Activity.MODE_PRIVATE);;
    }

    public String getToken() {
        String token = null;
        try {
            token = sharedPreferences.getString(Config.sharedPreferencesTokenKey, null);
        } catch (Exception ex) {

        }
        return token;
    }

    public void setToken(String token) {
        try {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Config.sharedPreferencesTokenKey, token);
            editor.apply();
        } catch (Exception ignored) {
        }
    }
}
