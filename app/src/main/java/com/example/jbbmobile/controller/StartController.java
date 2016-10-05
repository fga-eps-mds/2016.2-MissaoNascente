package com.example.jbbmobile.controller;

import android.content.Context;
import android.content.SharedPreferences;

public class StartController {

    public StartController(SharedPreferences settings, Context context) {
        if (settings.getBoolean("appFirstTime", true)) {
            new LoginController(context).tablesCreate();
            new ElementsController().createElement(context);

            settings.edit().putBoolean("appFirstTime", false).commit();
        }
    }
}
