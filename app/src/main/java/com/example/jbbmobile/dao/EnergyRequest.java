package com.example.jbbmobile.dao;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EnergyRequest {
    private static final String ENERGY_REQUEST_URL = "http://rogerlenke.site88.net/energy/GetEnergy.php";
    private Map<String,String> params;
    private int explorerEnergy;
    private String email;

    public EnergyRequest(String email){
        this.email = email;
    }

    public void request(final Context context, final EnergyRequest.Callback callback){
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    explorerEnergy = Integer.valueOf(jsonObject.getString("energy"));
                    Log.d("REMOTE_ENERGY","Energy receive in JSON: " + explorerEnergy);
                    boolean success = jsonObject.getBoolean("success");
                    callback.callbackResponse(success);

                } catch (JSONException e) {
                    callback.callbackResponse(false);
                }
            }
        };

        Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ENERGY_REQUEST_URL, listener, error){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                params = new HashMap<>();
                params.put("email", email);
                return params;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }

    public interface Callback{
        void callbackResponse(boolean response);
    }

    public int getExplorerEnergy(){
        return explorerEnergy;
    }
}
