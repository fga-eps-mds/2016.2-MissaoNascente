package com.example.jbbmobile.dao;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by roger on 06/10/16.
 */

public class LoginRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://rogerlenke.site88.net/Login.php";
    private Map<String,String> params;

    public LoginRequest(String password, String email, Response.Listener<String> listener){
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("pass", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
