package com.example.jbbmobile.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.example.jbbmobile.dao.ExplorerDAO;
import com.example.jbbmobile.dao.LoginRequest;
import com.example.jbbmobile.model.Explorer;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLDataException;
import static android.content.Context.MODE_PRIVATE;

public class LoginController {
    private Explorer explorer;
    private boolean action = false;
    private boolean response;
    private static final String PREF_NAME = "MainActivityPreferences";

    public LoginController() {
        explorer = new Explorer();
    }

    //LoginController to normal register Accounts

    public boolean realizeLogin(String email, String password, Context context) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        ExplorerDAO db = new ExplorerDAO(context);
        Explorer explorer = new Explorer();
        String passwordDigest;
        passwordDigest = explorer.cryptographyPassword(password);
        explorer = db.findExplorerLogin(email,passwordDigest);
        db.close();

        if (explorer == null || explorer.getEmail() == null || explorer.getPassword() == null) {
            return false;
        }
        saveFile(explorer.getEmail(), context);
        return true;
    }

    public void doLogin(String email, String password, final Context context){
        Explorer explorerFromLogin = new Explorer();
        explorerFromLogin.setEmail(email);

        try {
            explorerFromLogin.setPassword(password, password);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        LoginRequest loginRequest = new LoginRequest(explorerFromLogin.getEmail(), explorerFromLogin.getPassword());
        loginRequest.request(context, new LoginRequest.Callback() {
            @Override
            public void callbackResponse(boolean response) {
                setResponse(response);
                setAction(true);
            }
        });

    }

    public void deleteUser(Context context) {
        ExplorerDAO database = new ExplorerDAO(context);
        database.deleteAllExplorers(database.getWritableDatabase());
    }

    public boolean isAction() {
        return action;
    }

    public void setAction(boolean action) {
        this.action = action;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    //LoginController to Google Accounts
    public boolean realizeLogin(String email, Context context) throws IOException {
        ExplorerDAO dataBase = new ExplorerDAO(context);
        Explorer explorer = dataBase.findExplorer(email);
        dataBase.close();

        if (explorer == null || explorer.getEmail() == null) {
            return false;
        }

        saveFile(explorer.getEmail(), context);
        return true;
    }

    private void saveFile(String email, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.apply();
    }

    public void deleteFile(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("email");
        editor.apply();
    }

    public void loadFile(Context context) throws SQLDataException {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String email;


        if ((email = sharedPreferences.getString("email", null)) != null) {
            ExplorerDAO dataBase = new ExplorerDAO(context);
            Explorer explorer = dataBase.findExplorer(email);
            dataBase.close();
            this.explorer = new Explorer(explorer.getEmail(), explorer.getNickname(), explorer.getPassword());
        }
        else {
            throw new SQLDataException();
        }

    }

    public void checkIfGoogleHasGooglePassword() {
        try {
            getExplorer().getPassword().equals(null);
        } catch (NullPointerException i) {
            throw i;
        }
    }

    public boolean checkIfUserHasGoogleNickname() {
        return getExplorer().getNickname().equals("Placeholder");
    }

    public Explorer getExplorer() {
        return explorer;
    }

    public boolean remainLogin() {
        return getExplorer().getEmail() != null;
    }


}