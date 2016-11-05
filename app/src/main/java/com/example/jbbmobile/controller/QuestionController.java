package com.example.jbbmobile.controller;

import android.content.Context;
import android.util.Log;

import com.example.jbbmobile.R;
import com.example.jbbmobile.dao.AlternativeDAO;
import com.example.jbbmobile.dao.QuestionDAO;
import com.example.jbbmobile.dao.QuestionRequest;
import com.example.jbbmobile.model.Alternative;
import com.example.jbbmobile.model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionController {
    private List<Question> listQuestions;
    private boolean action = false;

    public QuestionController(){}

    private void downloadAllQuestions(final Context context){
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

    private void downloadUpdatedQuestions(final Context context, List<Question> listQuestions){
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

        questionRequest.requestUpdatedQuestions(context,listQuestions);
    }

    public void downloadQuestionsFromDatabase(Context context){
        QuestionDAO questionDAO = new QuestionDAO(context);
        List<Question> listQuestions;
        try{
            listQuestions = questionDAO.findAllQuestion();
            downloadUpdatedQuestions(context,listQuestions);
        }catch (IllegalArgumentException e){
            downloadAllQuestions(context);
        }
    }

    private int generateRandomQuestionId(Context context){
        int randomIdQuestion;
        Random random = new Random();
        QuestionDAO questionDAO = new QuestionDAO(context);
        int maxRange = questionDAO.countAllQuestions();

        randomIdQuestion = random.nextInt(maxRange);

        if (randomIdQuestion == 0)
            randomIdQuestion = 1;

        Log.d("QuestionController", String.valueOf(randomIdQuestion));
        return randomIdQuestion;
    }

    public Question getDraftQuestion(Context context){
        QuestionDAO questionDAO = new QuestionDAO(context);
        int draftIdQuestion = generateRandomQuestionId(context);
        Question question = questionDAO.findQuestion(draftIdQuestion);
        List<Alternative> alternativeList = new ArrayList<>();

        if(question.getAlternativeQuantity() == 2){
            Alternative trueAlternative = new Alternative(0,"a",context.getString(R.string.trueAlternative),question.getIdQuestion());
            alternativeList.add(trueAlternative);
            Alternative falseAlternative = new Alternative(0,"b",context.getString(R.string.falseAlternative),question.getIdQuestion());
            alternativeList.add(falseAlternative);
        } else {
            alternativeList = getDraftAlternativesQuestion(context,draftIdQuestion);
        }

        question.setAlternativeList(alternativeList);

        return question;
    }

    private List<Alternative> getDraftAlternativesQuestion(Context context, int draftIdQuestion){
        AlternativeDAO alternativeDAO = new AlternativeDAO(context);

        return alternativeDAO.findQuestionAlternatives(draftIdQuestion);
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