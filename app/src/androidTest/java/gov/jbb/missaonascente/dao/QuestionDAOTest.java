package gov.jbb.missaonascente.dao;

import android.content.Context;
import android.database.SQLException;
import android.support.test.InstrumentationRegistry;
import gov.jbb.missaonascente.dao.QuestionDAO;
import gov.jbb.missaonascente.model.Question;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class QuestionDAOTest {

    private QuestionDAO questionDAO;
    private final String DESCRIPTION = "O cerrrado é um bioma característico de qual região do Brasil?";

    @Before
    public void setup(){
        Context context = InstrumentationRegistry.getTargetContext();
        questionDAO = new QuestionDAO(context);
        questionDAO.onUpgrade(questionDAO.getReadableDatabase(),1,1);
    }

    @Test
    public void testIfInsertQuestionIsSuccessful() throws Exception{
        Question question = new Question(1, DESCRIPTION, "c", 2, 1);
        int insertValue = questionDAO.insertQuestion(question);

        assertNotEquals(-1, insertValue);
    }

    @Test
    public void testIfFindQuestionIsSuccessful() throws Exception{
        Question question = new Question(1, DESCRIPTION, "c", 2, 1);
        questionDAO.insertQuestion(question);
        Question responseQuestion = questionDAO.findQuestion(1);

        assertEquals(question.getIdQuestion(), responseQuestion.getIdQuestion());
    }

    @Test
    public void testIfUpdateQuestionIsSuccessful() throws Exception{
        Question question = new Question(1, DESCRIPTION, "c", 2, 1);
        questionDAO.insertQuestion(question);
        question.setCorrectAnswer("d");
        int updateSuccessful= questionDAO.updateQuestion(question);

        assertEquals(1, updateSuccessful);
    }

    @Test
    public void testIfQuestionsWhereCounted() throws Exception{
        Question question = new Question(1, DESCRIPTION, "c", 2, 1);
        questionDAO.insertQuestion(question);
        question = new Question(2, DESCRIPTION, "c", 2, 1);
        questionDAO.insertQuestion(question);
        question = new Question(3, DESCRIPTION, "c", 2 ,1);
        questionDAO.insertQuestion(question);
        int numberOfQuestions = questionDAO.countAllQuestions();

        assertEquals(3, numberOfQuestions);
    }

    @Test
    public void testIfInsertQuestionIsNotSuccessful() throws Exception{
        Question question = new Question(1, null, "c", 2);
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
        Question question = new Question(1, DESCRIPTION, "c", 2);
        int updateIsNotSuccessful = questionDAO.updateQuestion(question);
        assertEquals(0, updateIsNotSuccessful);
    }

    @Test
    public void testIfFindAllQuestionsIsSuccessful() throws Exception{
        Question question = new Question(1, DESCRIPTION, "c", 2, 1);
        questionDAO.insertQuestion(question);
        question = new Question(2, DESCRIPTION, "c", 2, 1);
        questionDAO.insertQuestion(question);

        List<Question> questions = questionDAO.findAllQuestion();

        assertEquals(1, questions.get(0).getIdQuestion());
        assertEquals(2, questions.get(1).getIdQuestion());
    }

    @Test
    public void testIfFindAllQuestionsIsNotSuccessful() throws Exception{
        boolean invalid = false;
        try{
            questionDAO.findAllQuestion();
        }catch (IllegalArgumentException i){
            invalid = i.getLocalizedMessage().equals("No questions");
        }

        assertTrue(invalid);
    }

    @Test
    public void testIfDeleteQuestionIsSuccessful() throws Exception{
        Question question = new Question(1, DESCRIPTION, "c", 2);
        questionDAO.insertQuestion(question);
        int deleteSuccessful = questionDAO.deleteQuestion(question);

        assertEquals(1, deleteSuccessful);
    }

    @Test
    public void testIfDeleteQuestionIsNotSuccessful() throws Exception{
        Question question = new Question(1, DESCRIPTION, "c", 2);
        int deleteSuccessful = questionDAO.deleteQuestion(question);

        assertEquals(0, deleteSuccessful);
    }
}