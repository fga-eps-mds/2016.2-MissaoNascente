
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

import java.util.HashMap;
import java.util.Map;


public class UpdateScoreRequest {

    private static final String UPDATE_REQUEST_URL = "http://rogerlenke.site88.net/UpdateScore.php";
    private Map<String,String> params;
    private int score;
    private String email;

    public UpdateScoreRequest(int score,String email){
        this.score=score;
        this.email=email;

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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATE_REQUEST_URL, listener, null){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                params = new HashMap<>();

                params.put("score", String.valueOf(score));
                params.put("email",email);


               // Log.i("Email:",email);
                Log.i("IntScore",""+score);
                Log.i("StringScore",String.valueOf(score));
                Log.i("Email",email);
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
