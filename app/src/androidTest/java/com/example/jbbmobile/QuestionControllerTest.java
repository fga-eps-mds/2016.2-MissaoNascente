package com.example.jbbmobile;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.example.jbbmobile.controller.QuestionController;
import com.example.jbbmobile.dao.QuestionDAO;
import com.example.jbbmobile.model.Question;

import org.junit.Before;
import org.junit.Test;

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
        controller.downloadAllQuestions(context);
        while(!controller.isAction()){}
        Question question = questionDAO.findQuestion(1);
        assertEquals(1,question.getIdQuestion());
    }
}