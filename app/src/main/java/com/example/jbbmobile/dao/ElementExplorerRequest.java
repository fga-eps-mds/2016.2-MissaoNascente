package com.example.jbbmobile.dao;

import android.content.Context;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElementExplorerRequest {

    private static final String ELEMENT_EXPLORER_REQUEST_URL = "http://rogerlenke.site88.net/ElementExplorer.php";
    private static final String ELEMENT_EXPLORER_RETRIVE_REQUEST_URL = "http://rogerlenke.site88.net/GetElementsExplorer.php";
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

    public ElementExplorerRequest(String email){
        this.email = email;
    }

    public void requestUpdateElements(final Context context, final Callback callback){
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

    public void requestRetriveElements(final Context context, final Callback callback){
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray= new JSONArray(response);
                    List<Element> elements = new ArrayList<>();
                    for(int i = 0; i < jsonArray.length(); i++){
                        int idElement = jsonArray.getJSONObject(i).getInt("idElement");
                        String catcDate = jsonArray.getJSONObject(i).getString("catchDate");
                        Element element = new Element(idElement, catcDate);
                        elements.add(element);

                    }
                    callback.callbackResponse(elements);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ELEMENT_EXPLORER_RETRIVE_REQUEST_URL, listener, null){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                params = new HashMap<>();
                params.put("email",email);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }

    public interface Callback{
        void callbackResponse(boolean response);
        void callbackResponse(List<Element> elements);
    }



}