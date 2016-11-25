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

import static gov.jbb.missaonascente.BuildConfig.URL_WEBSERVICE;

public class UpdateNumbersQuestionsRequest {
    private static final String UPDATE_REQUEST_URL = URL_WEBSERVICE + "UpdateQuestionCounter.php";
    private Map<String,String> params;
    private int questionAnswered;
    private  int correctQuestion;
    private String email;

    public UpdateNumbersQuestionsRequest(int questionAnswered, int correctQuestion,String email){
        this.questionAnswered = questionAnswered;
        this.correctQuestion = correctQuestion;
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

                params.put("questionAnswered", String.valueOf(questionAnswered));
                params.put("correctQuestion", String.valueOf(correctQuestion));
                params.put("email",email);

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