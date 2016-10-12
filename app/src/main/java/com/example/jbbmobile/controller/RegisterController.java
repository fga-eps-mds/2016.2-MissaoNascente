package com.example.jbbmobile.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteConstraintException;
import com.example.jbbmobile.dao.ExplorerDAO;
import com.example.jbbmobile.model.Explorer;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static android.content.Context.MODE_PRIVATE;

public class RegisterController {

    private static final String PREF_NAME = "RegisterActivityPreferences";
    private Explorer explorer;

    public RegisterController(){

    }

    public void Register (String nickname, String email, String password,String confirmPassword, Context applicationContext)throws SQLiteConstraintException{
        try {
            setExplorers(new Explorer(nickname, email, password, confirmPassword));
            ExplorerDAO explorerDAO = new ExplorerDAO(applicationContext);

            int errorRegister = -1;
            if (explorerDAO.insertExplorer(getExplorer()) == errorRegister) {
                throw new SQLiteConstraintException();
            }
        }catch (IllegalArgumentException exception){

            if((exception.getLocalizedMessage()).equals("nick")){
                throw new IllegalArgumentException("wrongNickname");
            }
            if((exception.getLocalizedMessage()).equals("password")){
                throw new IllegalArgumentException("wrongPassword");
            }
            if((exception.getLocalizedMessage()).equals("confirmPassword")){
                throw new IllegalArgumentException("wrongConfirmPassword");
            }
            if((exception.getLocalizedMessage()).equals("email")){
                throw new IllegalArgumentException("wrongEmail");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void Register(String nickname, String email, Context context) {
        setExplorers(new Explorer());
        getExplorer().googleExplorer(nickname, email);
        ExplorerDAO explorerDAO = new ExplorerDAO(context);

        try {
            explorerDAO.insertExplorer(getExplorer());
        } catch (SQLiteConstraintException e){
            e.getMessage();
        }
    }

    public void registerError(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("registerError", "registerError");
        editor.apply();
    }

    public void checkIfHasError(Context context) throws Exception {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String error;
        LoginController login = new LoginController();
        login.loadFile(context);

        error = sharedPreferences.getString("registerError", null);

        if (error != null) {
            new ExplorerDAO(context).deleteLocalExplorer(login.getExplorer());
            editor.remove("registerError");
            throw new Exception();
        }

    }

    public Explorer getExplorer() {
        return explorer;
    }

    private void setExplorers(Explorer explorer) {
        this.explorer = explorer;
    }
}
