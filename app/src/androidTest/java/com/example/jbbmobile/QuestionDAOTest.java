package com.example.jbbmobile;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.support.test.InstrumentationRegistry;
import com.example.jbbmobile.dao.BookDAO;
import com.example.jbbmobile.dao.ElementDAO;
import com.example.jbbmobile.dao.ExplorerDAO;
import com.example.jbbmobile.dao.QuestionDAO;
import com.example.jbbmobile.model.Book;
import com.example.jbbmobile.model.Question;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class QuestionDAOTest {

    private QuestionDAO questionDAO;
    Map alternatives;
    String description;

    @Before
    public void setup(){
        alternatives =  new HashMap<String, String>();
        alternatives.put("a", "Sul");
        alternatives.put("b", "Sudeste");
        alternatives.put("c", "Centro-Oeste");
        alternatives.put("d", "Norte");
        alternatives.put("e", "Nordeste");
        description = "O cerrrado é um bioma característico de qual região do Brasil?";
        Context context = InstrumentationRegistry.getTargetContext();
        questionDAO = new QuestionDAO(context);
        questionDAO.onUpgrade(questionDAO.getReadableDatabase(),1,1);
    }

    @Test
    public void testIfInsertQuestionIsSuccessful() throws Exception{
        Question question = new Question(1, description, alternatives, "c", 2);
        int insertValue = questionDAO.insertQuestion(question);

        assertNotEquals(-1, insertValue);
    }

    @Test
    public void testIfFindQuestionIsSuccessful() throws Exception{
        Question question = new Question(1, description, alternatives, "c", 2);
        questionDAO.insertQuestion(question);
        Question responseQuestion = questionDAO.findQuestion(1);

        assertEquals(question.getIdQuestion(), responseQuestion.getIdQuestion());
    }

    @Test
    public void testIfUpdateQuestionIsSuccessful() throws Exception{
        Question question = new Question(1, description, alternatives, "c", 2);
        questionDAO.insertQuestion(question);
        question.setCorrectAnswer("d");
        int updateSuccessful= questionDAO.updateQuestion(question);

        assertEquals(1, updateSuccessful);
    }

    @Test
    public void testIfQuestionsWhereCounted() throws Exception{
        Question question = new Question(1, description, alternatives, "c", 2);
        questionDAO.insertQuestion(question);
        question = new Question(2, description, alternatives, "c", 2);
        questionDAO.insertQuestion(question);
        question = new Question(3, description, alternatives, "c", 2);
        questionDAO.insertQuestion(question);
        int numberOfQuestions = questionDAO.countAllQuestions();

        assertEquals(3, numberOfQuestions);
    }

    @Test
    public void testIfInsertQuestionIsNotSuccessful() throws Exception{
        Question question = new Question(1, null, alternatives, "c", 2);
        int insertNoSuccessful = questionDAO.insertQuestion(question);

        assertEquals(-1, insertNoSuccessful);
    }

    @Test
    public void testIfFindQuestionIsNotSuccessful() throws Exception{
        boolean invalid = false;
        try{
            questionDAO.findQuestion(1);

        }catch (SQLException e){
            invalid = true;
        }
        assertTrue(invalid);
    }

    @Test
    public void testIfUpdateQuestionsIsNotSuccessful() throws Exception{
        Question question = new Question(1, description, alternatives, "c", 2);
        int updateIsNotSuccessful = questionDAO.updateQuestion(question);
        assertEquals(0, updateIsNotSuccessful);
    }
}
