package gov.jbb.missaonascente.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import gov.jbb.missaonascente.R;
import gov.jbb.missaonascente.dao.AlternativeDAO;
import gov.jbb.missaonascente.dao.QuestionDAO;
import gov.jbb.missaonascente.dao.QuestionRequest;
import gov.jbb.missaonascente.model.Alternative;
import gov.jbb.missaonascente.model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionController {
    private List<Question> listQuestions;
    private boolean action = false;

    private SharedPreferences preferencesTime;
    private long elapsedQuestionTime;
    public final long MIN_TIME = 30000;     // TIME TO APPEAR QUESTION AGAIN (30 SECONDS)


    public QuestionController(){}

    private void downloadAllQuestions(final Context context){
        QuestionRequest questionRequest = new QuestionRequest(new QuestionRequest.Callback() {
            @Override
            public void callbackResponse(List<Question> listQuestions) {
                if (listQuestions.size() != 0) {
                    setListQuestions(listQuestions);
                    QuestionDAO questionDAO = new QuestionDAO(context);
                    for (int i = 0; i < listQuestions.size(); i++) {
                        questionDAO.insertQuestion(listQuestions.get(i));
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
                if (listQuestions.size() != 0) {
                    setListQuestions(listQuestions);
                    QuestionDAO questionDAO = new QuestionDAO(context);
                    for (int i = 0; i < listQuestions.size(); i++) {
                        questionDAO.deleteQuestion(listQuestions.get(i));
                        questionDAO.insertQuestion(listQuestions.get(i));
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

        Log.d("FRAGM", String.valueOf(randomIdQuestion));
        return randomIdQuestion + 1;
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

        Log.d("FRAGM", String.valueOf(question.getIdQuestion()));
        Log.d("FRAGM", String.valueOf(alternativeList.size()));
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


    ///====== TIME TO ANSWER QUESTIONS

    public long getElapsedQuestionTime() {
        return elapsedQuestionTime;
    }

    public void setElapsedQuestionTime(long elapsedElementTime) {
        this.elapsedQuestionTime = elapsedElementTime;
    }

    public String getRemainingTimeInMinutes(){
        Log.d("Tempo Decorrido", String.valueOf(getElapsedQuestionTime()));
        return String.format("%.2f", (MIN_TIME/60000.0f - getElapsedQuestionTime()/60000.0f));
    }

    public void calculateElapsedQuestionTime(Context context){
            preferencesTime = PreferenceManager.getDefaultSharedPreferences(context);
            long end = System.currentTimeMillis();
            Log.d("Tempo Final", String.valueOf(end));
            long start = preferencesTime.getLong("questionTime",0);
            Log.d("Tempo Start", String.valueOf(start));
            Log.d("Tempo Calculado", String.valueOf(end-start));
            setElapsedQuestionTime(end - start);
    }

    public void addQuestionTimeOnPreferencesTime(Context context){
        preferencesTime = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferencesTime.edit();
        editor.putLong("questionTime", System.currentTimeMillis());
        editor.apply();
        Log.d("Tempo Inicial", String.valueOf(System.currentTimeMillis()));
    }




}