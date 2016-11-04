package com.example.jbbmobile.dao;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jbbmobile.model.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionRequest {
    private static final String DOWNLOAD_QUESTIONS_REQUEST_URL = "http://rogerlenke.site88.net/parser/downloadAllQuestions.php";
    private static final String DOWNLOAD_UPDATED_QUESTIONS_REQUEST_URL = "http://rogerlenke.site88.net/parser/DownloadUpdatedQuestions.php";
    private List<Question> listQuestions;
    private Response.Listener<String> listener;
    private Map<String, String> params;
    private List<Question> localQuestionList;

    public QuestionRequest(final Context context, Callback callback){
        startListener(callback);
    }

    public void requestAllQuestions(final Context context){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,DOWNLOAD_QUESTIONS_REQUEST_URL,listener,null);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public void requestUpdatedQuestions(final Context context){
        QuestionDAO questionDAO = new QuestionDAO(context);
        localQuestionList = questionDAO.findAllQuestion();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,DOWNLOAD_UPDATED_QUESTIONS_REQUEST_URL,listener,null){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                JSONArray jsonArray = new JSONArray();
                for(int i = 0; i < localQuestionList.size(); i++){
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("idQuestion", localQuestionList.get(i).getIdQuestion());
                        jsonObject.put("description", localQuestionList.get(i).getDescription());
                        jsonObject.put("correctAnswer", localQuestionList.get(i).getCorrectAnswer());
                        jsonObject.put("alternativeQuantity", localQuestionList.get(i).getAlternativeQuantity());
                        jsonObject.put("version", localQuestionList.get(i).getVersion());
                        jsonArray.put(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                params = new HashMap<>();
                Log.d("Questions", jsonArray.toString());
                params.put("questions", jsonArray.toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void startListener(final Callback callback){
        listener = new Response.Listener<String>() {
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
                        int version = jsonArray.getJSONObject(i).getInt("version");
                        listQuestions.add(new Question(idQuestion,description,correctAnswer,alternativeQuantity, version));
                    }
                    Log.d("Size", String.valueOf(listQuestions.size()));
                    callback.callbackResponse(listQuestions);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }
    public interface Callback{
        void callbackResponse(List<Question> listQuestions);
    }
}