package com.example.jbbmobile.dao;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hugo on 12/10/16.
 */

public class DeleteRequest extends StringRequest {
    private static final String DELETE_REQUEST_URL = "http://rogerlenke.site88.net/Delete.php";
    private Map<String,String> params;

    public DeleteRequest(String email, Response.Listener<String> listener){
        super(Method.POST, DELETE_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
