package com.example.jbbmobile.dao;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jbbmobile.model.Question;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuestionRequest {
    private static final String DOWNLOAD_QUESTIONS_REQUEST_URL = "http://rogerlenke.site88.net/parser/downloadAllQuestions.php";
    private List<Question> listQuestions;

    public QuestionRequest(){}

    public void requestAllQuestions(final Context context, final Callback callback){
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("QuestionResponse",response);
                    JSONArray jsonArray = new JSONArray(response);
                    listQuestions = new ArrayList<>();

                    for(int i = 0;i<jsonArray.length();i++){
                        int idQuestion = jsonArray.getJSONObject(i).getInt("idQuestion");
                        String description = jsonArray.getJSONObject(i).getString("description");
                        String correctAnswer = jsonArray.getJSONObject(i).getString("correctAnswer");
                        int alternativeQuantity = jsonArray.getJSONObject(i).getInt("alternativeQuantity");
                        listQuestions.add(new Question(idQuestion,description,correctAnswer,alternativeQuantity));
                    }

                    callback.callbackResponse(listQuestions);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.POST,DOWNLOAD_QUESTIONS_REQUEST_URL,listener,null);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public interface Callback{
        void callbackResponse(List<Question> listQuestions);
    }
}