package com.example.jbbmobile.dao;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UpdateRequest extends StringRequest{
    private static final String UPDATE_REQUEST_URL = "http://rogerlenke.site88.net/Update.php";
    private Map<String,String> params;

    public UpdateRequest(String nickname, String email, Response.Listener<String> listener){
        super(Method.POST, UPDATE_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("nickname", nickname);
        params.put("email", email);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}


