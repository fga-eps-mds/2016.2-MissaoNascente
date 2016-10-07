package com.example.jbbmobile.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.jbbmobile.dao.ExplorerDAO;
import com.example.jbbmobile.dao.LoginRequest;
import com.example.jbbmobile.dao.RegisterRequest;
import com.example.jbbmobile.model.Explorer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import static android.content.Context.MODE_PRIVATE;

public class LoginController {
    private Explorer explorer;

    private static final String PREF_NAME = "MainActivityPreferences";


    public LoginController(){
        explorer = new Explorer();
    }
    public void tablesCreate(Context context){
        ExplorerDAO explorerDAO = new ExplorerDAO(context);
        explorerDAO.createExplorerTable(explorerDAO.getWritableDatabase());
    }

    //LoginController to normal register Accounts
    public boolean realizeLogin(String email, String password, Context context) {
        ExplorerDAO db = new ExplorerDAO(context);
        Explorer explorer = db.findExplorerLogin(new Explorer(email,password));
        db.close();

        if (explorer == null || explorer.getEmail() == null || explorer.getPassword() == null) {
            return false;
        }
        saveFile(explorer.getEmail(),context);
        return true;
    }

 /*   public boolean doLogin(String email, String password, Context context){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean sucess = jsonObject.getBoolean("success");
                    if(!sucess){
                    }else{
                        Log.i("Email", jsonObject.getString("nickname"));
                        explorer.setEmail(jsonObject.getString("email"));
                        explorer.setNickname(jsonObject.getString("nickname"));
                        explorer.setPassword(jsonObject.getString("pass"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        LoginRequest loginRequest = new LoginRequest(password,  email, responseListener );
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(loginRequest);
        saveFile(email, context);

        return true;
    }*/

    //LoginController to Google Accounts
    public boolean realizeLogin(String email, Context context) throws IOException{
        ExplorerDAO db = new ExplorerDAO(context);
        Explorer explorer = db.findExplorer(new Explorer(email));
        db.close();

        if (explorer == null || explorer.getEmail() == null) {
            return false;
        }

        saveFile(explorer.getEmail(),context);
        return true;
    }

    private void saveFile(String email, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.commit();
    }

    public void deleteFile(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("email");
        editor.commit();

    }

    public void loadFile(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String email;

        if ((email =  sharedPreferences.getString("email",null))!=null){
            ExplorerDAO db = new ExplorerDAO(context);
            Explorer explorer = db.findExplorer(new Explorer(email));
            db.close();
            this.explorer = new Explorer(explorer.getEmail(),explorer.getNickname(),explorer.getPassword());
        }
    }

    public void checkifGoogleHasGooglePassword(){

        try{
            getExplorer().getPassword().equals(null);
        }catch(NullPointerException i){
            throw i;
        }
    }

    public boolean checkIfUserHasGoogleNickname(){
        if(getExplorer().getNickname().equals("Placeholder")){
            return true;

        }
        return false;
    }

    public Explorer getExplorer() {
        return explorer;
    }

    public boolean remainLogin() {
        if (getExplorer().getEmail() == null) {
            return false;
        }
        return true;
    }
}