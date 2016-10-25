package com.example.jbbmobile.dao;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ElementExplorerRequest {

    private static final String ELEMENT_EXPLORER_REQUEST_URL = "http://rogerlenke.site88.net/ElementExplorer.php";
    private Map<String,String> params;
    private String email;
    private int idElement;
    //private String userImage;
    private String catchDate;


    public ElementExplorerRequest(String email, int idElement,String userImage, String catchDate){
        this.email=email;
        this.idElement=idElement;
        //  this.userImage =userImage;
        this.catchDate=catchDate;
    }

    public void request(final Context context, final Callback callback){
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    callback.callbackResponse(jsonObject.getBoolean("success"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ELEMENT_EXPLORER_REQUEST_URL, listener, null){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                params = new HashMap<>();

                params.put("idElement",String.valueOf(idElement));
                params.put("email",email);
                //params.put("userImage", userImage);
                params.put("catchDate",String.valueOf(catchDate));

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