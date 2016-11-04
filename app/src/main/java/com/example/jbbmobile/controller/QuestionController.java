package com.example.jbbmobile.controller;

import android.content.Context;
import android.util.Log;

import com.example.jbbmobile.dao.QuestionDAO;
import com.example.jbbmobile.dao.QuestionRequest;
import com.example.jbbmobile.model.Question;

import java.util.List;

public class QuestionController {
    private List<Question> listQuestions;
    private boolean action = false;

    public QuestionController(){}

    public void downloadAllQuestions(final Context context){
        QuestionRequest questionRequest = new QuestionRequest(new QuestionRequest.Callback() {
            @Override
            public void callbackResponse(List<Question> listQuestions) {
                if (listQuestions.size() != 0) {
                    setListQuestions(listQuestions);
                    QuestionDAO questionDAO = new QuestionDAO(context);
                    for (int i = 0; i < listQuestions.size(); i++) {
                        int insertResponse = questionDAO.insertQuestion(listQuestions.get(i));
                        Log.d("QuestionRequest", String.valueOf(insertResponse));
                    }
                    setAction(true);
                }
            }
        });

        questionRequest.requestAllQuestions(context);
    }

    public void downloadUpdatedQuestions(final Context context){
        setAction(false);
        QuestionRequest questionRequest = new QuestionRequest(new QuestionRequest.Callback() {
            @Override
            public void callbackResponse(List<Question> listQuestions) {
                Log.d("Entrou", "Aqui");
                if (listQuestions.size() != 0) {
                    setListQuestions(listQuestions);
                    QuestionDAO questionDAO = new QuestionDAO(context);
                    for (int i = 0; i < listQuestions.size(); i++) {
                        questionDAO.deleteQuestion(listQuestions.get(i));
                        int insertQuestion = questionDAO.insertQuestion(listQuestions.get(i));
                        Log.d("Insert Question Request", String.valueOf(insertQuestion));
                    }
                    setAction(true);
                }
            }
        });

        questionRequest.requestUpdatedQuestions(context);
    }

    public List<Question> getListQuestions() {
        return listQuestions;
    }

    public void setListQuestions(List<Question> listQuestions) {
        this.listQuestions = listQuestions;
    }

    public boolean isAction() {
        return action;
    }

    public void setAction(boolean action) {
        this.action = action;
    }
}