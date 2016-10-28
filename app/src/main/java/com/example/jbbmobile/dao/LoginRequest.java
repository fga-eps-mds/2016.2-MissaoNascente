package com.example.jbbmobile.dao;

import android.content.Context;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jbbmobile.controller.LoginController;
import com.example.jbbmobile.model.Explorer;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginRequest  {
    private static final String LOGIN_REQUEST_URL = "http://rogerlenke.site88.net/Login.php";
    private Map<String,String> params;
    private String password;
    private String email;
    private int score;

    public LoginRequest(String email, String password){
        this.email = email;
        this.password = password;
    }

    public void request(final Context context, final Callback callback){
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Explorer explorer = new Explorer();
                    JSONObject jsonObject = new JSONObject(response);
                    explorer.setEmail(jsonObject.getString("email"));
                    explorer.setPassword(jsonObject.getString("pass"));
                    explorer.setNickname(jsonObject.getString("nickname"));
                    explorer.setScore(jsonObject.getInt("score"));
                    ExplorerDAO database = new ExplorerDAO(context);
                    database.deleteAllExplorers(database.getWritableDatabase());
                    database.insertExplorer(explorer);
                    new LoginController().realizeLogin(explorer.getEmail(), context);
                    boolean success = jsonObject.getBoolean("success");
                    callback.callbackResponse(success);

                } catch (JSONException | IOException e) {
                    callback.callbackResponse(false);
                }

            }
        };

        Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_REQUEST_URL, listener, error){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                params = new HashMap<>();
                params.put("email", email);
                params.put("pass", password);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }

    public interface Callback{
        void callbackResponse(boolean response);
    }
}
