package com.example.jbbmobile.dao;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jbbmobile.model.Alternative;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class AlternativeRequest {
    private static final String DOWNLOAD_ALTERNATIVES_REQUEST_URL = "http://rogerlenke.site88.net/parser/downloadAllAlternatives.php";
    private Response.Listener<String> listener;
    private List<Alternative> listAlternative;

    public AlternativeRequest(Callback callback){
        startListener(callback);
    }

    public void requestAllAlternatives(final Context context){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,DOWNLOAD_ALTERNATIVES_REQUEST_URL,listener,null);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void startListener(final Callback callback){
        listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("AlternativeRequest",response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    listAlternative = new ArrayList<>();

                    for(int i = 0;i<jsonArray.length();i++){
                        int idAlternative = jsonArray.getJSONObject(i).getInt("idAlternative");
                        String alternativeLetter = jsonArray.getJSONObject(i).getString("alternativeLetter");
                        String alternativeDescription = jsonArray.getJSONObject(i).getString("alternativeDescription");
                        int idQuestion = jsonArray.getJSONObject(i).getInt("idQuestion");
                        listAlternative.add(new Alternative(idAlternative,alternativeLetter,alternativeDescription,idQuestion));
                    }
                    callback.callbackResponse(listAlternative);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public interface Callback{
        void callbackResponse(List<Alternative> listAlternative);
    }
}