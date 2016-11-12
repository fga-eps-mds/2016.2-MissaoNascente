package gov.jbb.missaonascente.dao;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import gov.jbb.missaonascente.model.Notification;

import org.json.JSONArray;
import org.json.JSONException;

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
                    for (int i = 0; i < jsonArray.length() ; i++){
                        int idNotification = jsonArray.getJSONObject(i).getInt("idNotification");
                        String title = jsonArray.getJSONObject(i).getString("title");
                        String description = jsonArray.getJSONObject(i).getString("description");
                        String date = jsonArray.getJSONObject(i).getString("date");
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
