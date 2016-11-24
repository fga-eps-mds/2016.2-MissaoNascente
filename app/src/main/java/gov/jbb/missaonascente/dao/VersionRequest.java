package gov.jbb.missaonascente.dao;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static gov.jbb.missaonascente.BuildConfig.URL_WEBSERVICE;

public class VersionRequest {
    private static final String VERSION_REQUEST_URL = URL_WEBSERVICE + "parser/VersionRequest.php";

    public void request(final Context context, final Callback callback){
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    callback.callbackResponse(jsonObject.getDouble("version"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        StringRequest stringRequest = new StringRequest(Request.Method.POST, VERSION_REQUEST_URL, listener, null);

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);

    }

    public interface Callback{
        void callbackResponse(double response);
    }
}


