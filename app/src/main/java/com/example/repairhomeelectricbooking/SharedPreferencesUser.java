package com.example.repairhomeelectricbooking;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUser {
    private static final String SHARED_PREFERENCES_USER = "SHARED_PREFERENCES_USER";
    private Context myContext;

    public SharedPreferencesUser(Context myContext) {
        this.myContext = myContext;
    }

    public void putBooleanValue(String key,boolean value){
        SharedPreferences sharedPreferences = myContext.getSharedPreferences(SHARED_PREFERENCES_USER,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }

    public boolean getBooleanValue(String key){
        SharedPreferences sharedPreferences = myContext.getSharedPreferences(SHARED_PREFERENCES_USER,0);
        return sharedPreferences.getBoolean(key,false);
    }
}
