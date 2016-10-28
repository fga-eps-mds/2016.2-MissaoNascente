package com.example.jbbmobile.dao;

import android.content.Context;
import android.database.SQLException;
import android.util.Log;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DownloadElementsRequest {

    private static final String DOWNLOAD_ELEMENTS_REQUEST_URL = "http://rogerlenke.site88.net/parser/DownloadElement.php";
    private static final String DOWNLOAD_SPECIFIC_ELEMENTS_URL = "http://rogerlenke.site88.net/parser/DownloadSpecificElements.php";
    private Map<String, String> params;
    private List<Element> elements;

    public DownloadElementsRequest(){

    }

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

    public void requestSpecificElements(final Context context, final Callback callback) throws IllegalArgumentException{
        ElementDAO database = new ElementDAO(context);
        elements = database.findAllElements();

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    List<Element> elements = new ArrayList<>();
                    for(int i = 0; i < jsonArray.length(); i++) {
                        int idElement = jsonArray.getJSONObject(i).getInt("idElement");
                        int qrCodeNumber = jsonArray.getJSONObject(i).getInt("qrCodeNumber");
                        int elementScore = jsonArray.getJSONObject(i).getInt("elementScore");
                        String defaultImage = jsonArray.getJSONObject(i).getString("defaultImage");
                        int idBook = jsonArray.getJSONObject(i).getInt("idBook");
                        String nameElement = jsonArray.getJSONObject(i).getString("nameElement");
                        String textDescription = jsonArray.getJSONObject(i).getString("textDescription");
                        float x = (float) jsonArray.getJSONObject(i).getDouble("x");
                        float y = (float) jsonArray.getJSONObject(i).getDouble("y");
                        float version = (float) jsonArray.getJSONObject(i).getDouble("version");
                        elements.add((new Element(idElement,qrCodeNumber,elementScore,
                                defaultImage,nameElement,idBook,textDescription,y ,x, version)));
                    }
                    callback.callbackResponse(elements);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWNLOAD_SPECIFIC_ELEMENTS_URL,
                listener, null){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                JSONArray jsonArray = new JSONArray();

                for(int i = 0; i < elements.size(); i++) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("idElement", elements.get(i).getIdElement());
                        jsonObject.put("version", elements.get(i).getVersion());
                        jsonObject.put("nameElement", elements.get(i).getNameElement());
                        jsonObject.put("textDescription", elements.get(i).getTextDescription());
                        jsonObject.put("idBook", elements.get(i).getIdBook());
                        jsonObject.put("qrCodeNumber", elements.get(i).getQrCodeNumber());
                        jsonObject.put("y", elements.get(i).getSouthCoordinate());
                        jsonObject.put("x", elements.get(i).getWestCoordinate());
                        jsonObject.put("defaultImage", elements.get(i).getDefaultImage());
                        jsonObject.put("elementScore", elements.get(i).getElementScore());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    jsonArray.put(jsonObject);

                }

                params = new HashMap<>();
                params.put("elements", jsonArray.toString());

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);

    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    public interface Callback{
        void callbackResponse(List<Element> elements);
    }

}
