package com.example.jbbmobile;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.example.jbbmobile.controller.QuestionController;
import com.example.jbbmobile.dao.QuestionDAO;
import com.example.jbbmobile.model.Question;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class QuestionControllerTest {
    private Context context;
    private QuestionController controller;
    private QuestionDAO questionDAO;

    @Before
    public void setup() throws Exception{
        Context context = InstrumentationRegistry.getTargetContext();
        this.context = context;
        controller = new QuestionController();
        questionDAO = new QuestionDAO(context);
        questionDAO.onUpgrade(questionDAO.getReadableDatabase(),2,2);
    }

    @Test
    public void testIfQuestionsWereDownloaded() throws Exception{
        controller.downloadQuestionsFromDatabase(context);
        while(!controller.isAction());
        Question question = questionDAO.findQuestion(1);
        assertEquals(1,question.getIdQuestion());
    }

    @Test
    public void testIfUpdatedQuestionsWereDownloaded() throws Exception{
        boolean valid = true;
        Question question = new Question(1, "Descrição legal" , "c", 2, 1);
        questionDAO.insertQuestion(question);
        Question question2 = new Question(2, "Descrição legal" , "c", 2, 1);
        questionDAO.insertQuestion(question2);
        controller.downloadQuestionsFromDatabase(context);
        while(!controller.isAction());
        List<Question> questionList = controller.getListQuestions();

        for(int i = 0; i < questionList.size(); i++){
            if(question.getIdQuestion() == questionList.get(i).getIdQuestion() ||
                    question2.getIdQuestion() == questionList.get(i).getIdQuestion()){
                valid = false;
            }
        }

        assertTrue(valid);
    }
}