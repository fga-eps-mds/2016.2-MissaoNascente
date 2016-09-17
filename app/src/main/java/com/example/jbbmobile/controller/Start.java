package com.example.jbbmobile.controller;

import android.content.Context;
import android.content.SharedPreferences;

public class Start {

    public Start(SharedPreferences settings, Context context) {
        if (settings.getBoolean("appFirstTime", true)) {
            new Login().tablesCreate(context);
            new Element().createElement(context);

            settings.edit().putBoolean("appFirstTime", false).commit();
        }
    }
}
