package com.dota.pearl17;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

/**
 * Created by ADMIN on 7.3.17.
 */

public class SessionManager {

    public static final String PREFS_NAME = "Cart details";

    public static int getVersion(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME,0);
        int version = preferences.getInt("version", -1);
        return version;
    }

    public static int setVersion(Context context, int version) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME,0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("version", version);
        return version;
    }
}
