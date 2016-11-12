package gov.jbb.missaonascente.dao;

import android.content.Context;

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

public class RegisterRequest{
    private static final String REGISTER_REQUEST_URL = "http://rogerlenke.site88.net/Register.php";
    private Map<String,String> params;
    private String email;
    private String password;
    private String nickname;

    public RegisterRequest(String nickname, String password, String email){
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public void request(Context context, final Callback callback) {
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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_REQUEST_URL, listener, null){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                params = new HashMap<>();
                params.put("nickname", nickname);
                params.put("email", email);
                params.put("pass", password);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }

    public interface Callback{
        void callbackResponse(boolean success);
    }
}
