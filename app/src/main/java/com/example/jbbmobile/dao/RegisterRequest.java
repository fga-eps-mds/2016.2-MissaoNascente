package com.example.jbbmobile.dao;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by igor on 06/10/16.
 */

public class RegisterRequest extends StringRequest{
    private static final String REGISTER_REQUEST_URL = "http://rogerlenke.site88.net/Register.php";
    private Map<String,String> params;

    public RegisterRequest(String nickname, String password, String email, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("nickname", nickname);
        params.put("email", email);
        params.put("pass", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
