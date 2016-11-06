package com.example.jbbmobile;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.example.jbbmobile.controller.QuestionController;
import com.example.jbbmobile.dao.AlternativeDAO;
import com.example.jbbmobile.dao.QuestionDAO;
import com.example.jbbmobile.model.Alternative;
import com.example.jbbmobile.model.Question;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class QuestionControllerTest {
    private Context context;
    private QuestionController controller;
    private QuestionDAO questionDAO;
    private AlternativeDAO alternativeDAO;

    @Before
    public void setup() throws Exception{
        Context context = InstrumentationRegistry.getTargetContext();
        this.context = context;
        controller = new QuestionController();
        questionDAO = new QuestionDAO(context);
        alternativeDAO = new AlternativeDAO(context);
        questionDAO.onUpgrade(questionDAO.getReadableDatabase(),2,2);
        alternativeDAO.onUpgrade(alternativeDAO.getReadableDatabase(),2,2);
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

    @Test
    public void testIfARandomQuestionWasObtained() throws Exception{
        boolean successful = false;

        Question question = new Question(1, "Questão 1" , "a", 2);
        questionDAO.insertQuestion(question);

        question = new Question(2, "Questão 2" , "b", 2);
        questionDAO.insertQuestion(question);

        question = controller.getDraftQuestion(context);

        if(question.getIdQuestion() == 1 || question.getIdQuestion() == 2){
            successful = true;
        }

        assertTrue(successful);
    }

    @Test
    public void testIfARandomQuestionWasObtainedWithRightAlternatives() throws Exception{
        boolean successful = false;

        Question question = new Question(1, "Questão 1" , "a", 3);
        questionDAO.insertQuestion(question);

        question = new Question(2, "Questão 2" , "a", 3);
        questionDAO.insertQuestion(question);

        Alternative alternative = new Alternative(1,"a","Alternativa1",1);
        alternativeDAO.insertAlternative(alternative);

        alternative = new Alternative(2,"b","Alternativa2",1);
        alternativeDAO.insertAlternative(alternative);

        alternative = new Alternative(3,"c","Alternativa3",1);
        alternativeDAO.insertAlternative(alternative);

        alternative = new Alternative(4,"a","Alternativa1",2);
        alternativeDAO.insertAlternative(alternative);

        alternative = new Alternative(5,"b","Alternativa2",2);
        alternativeDAO.insertAlternative(alternative);

        alternative = new Alternative(6,"c","Alternativa3",2);
        alternativeDAO.insertAlternative(alternative);

        question = controller.getDraftQuestion(context);

        List<Alternative> alternativeList = question.getAlternativeList();

        if((alternativeList.get(0).getIdQuestion() == 1 && alternativeList.get(1).getIdQuestion() == 1 &&
        alternativeList.get(2).getIdQuestion() == 1) || (alternativeList.get(0).getIdQuestion() == 2 &&
        alternativeList.get(1).getIdQuestion() == 2 && alternativeList.get(2).getIdQuestion() == 2)){

            successful = true;
        }

        assertTrue(successful);
    }
}