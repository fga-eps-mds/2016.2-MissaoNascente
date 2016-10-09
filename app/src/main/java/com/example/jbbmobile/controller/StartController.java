package com.example.jbbmobile.controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.jbbmobile.dao.HelperDAO;

import static android.content.Context.MODE_PRIVATE;

public class StartController {

    public StartController(SharedPreferences settings, Context context) {
        if (settings.getString("appFirstTime", null)==null) {
            HelperDAO helperDAO = new HelperDAO(context);
            helperDAO.getWritableDatabase();

            helperDAO.close();

            new ElementsController().createElement(context);

            SharedPreferences sharedPreferences = context.getSharedPreferences("appFirstTime", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("appFirstTime", "Init");
            editor.apply();
        }
    }
}
