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
import com.example.jbbmobile.model.Notification;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationRequest {



    private static final String NOTIFICATION_REQUEST_URL = "http://rogerlenke.site88.net/Notification.php";
    private Map<String,String> params;

    public Notification getNotification() {
        return notification;
    }

    private Notification notification;

    public NotificationRequest(){
        notification = new Notification();
    }

    public void request(final Context context, final NotificationRequest.Callback callback) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    List<Notification> notificationList = new ArrayList<>();
                    int i;
                    for (i = 0; i <= jsonArray.length() - 4; i+=4){
                        int idNotification = jsonArray.getInt(i);
                        String title = jsonArray.getString(i+1);
                        String description = jsonArray.getString(i+2);
                        String date = jsonArray.getString(i+3);
                        notificationList.add(new Notification(idNotification, title, description, date));
                    }
                    callback.callbackResponse(notificationList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.GET, NOTIFICATION_REQUEST_URL, responseListener, null){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                params = new HashMap<>();

                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }

    public interface Callback{
        void callbackResponse(List <Notification> notificationList);
    }
}
