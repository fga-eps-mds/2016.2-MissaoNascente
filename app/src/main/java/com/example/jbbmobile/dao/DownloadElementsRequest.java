package com.example.jbbmobile.dao;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jbbmobile.model.Element;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DownloadElementsRequest {

    private static final String DOWNLOAD_ELEMENTS_REQUEST_URL = "http://rogerlenke.site88.net/parser/DownloadElement.php";

    public void request(final Context context,final Callback callback){
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    List<Element> elements = new ArrayList<>();
                    for (int i = 0 ; i<jsonArray.length() ; i++){
                        int idElement = jsonArray.getJSONObject(i).getInt("idElement");
                        int qrCodeNumber = jsonArray.getJSONObject(i).getInt("qrCodeNumber");
                        int elementScore = jsonArray.getJSONObject(i).getInt("elementScore");
                        String defaultImage = jsonArray.getJSONObject(i).getString("defaultImage");
                        int idBook = jsonArray.getJSONObject(i).getInt("idBook");
                        String nameElement = jsonArray.getJSONObject(i).getString("nameElement");
                        String textDescription = jsonArray.getJSONObject(i).getString("textDescription");

                        elements.add(new Element(idElement,qrCodeNumber,elementScore,defaultImage,nameElement,idBook,textDescription));
                    }

                    callback.callbackResponse(elements);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                                                        DOWNLOAD_ELEMENTS_REQUEST_URL,
                                                        listener,
                                                        null);

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }

    public interface Callback{
        void callbackResponse(List<Element> elements);
    }

}
