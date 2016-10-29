package jbbmobile.example.com.elementparser;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class QuestionTest {

    Map alternatives;
    String description;

    @Before
    public void setUp(){
        alternatives =  new HashMap<String, String>();
        alternatives.put("a", "Sul");
        alternatives.put("b", "Sudeste");
        alternatives.put("c", "Centro-Oeste");
        alternatives.put("d", "Norte");
        alternatives.put("e", "Nordeste");

        description = "O cerrrado é um bioma característico de qual região do Brasil?";
    }

    @Test
    public void testIfQuestionIsCreated() throws Exception{
        Question question = new Question(1, description, alternatives, "c");

        assertEquals(question.getId(), 1);
    }

    @Test
    public void testIfQuestionDescriptionIsNotBlank() throws Exception{
        Question question = new Question(1, description, alternatives, "c");

        assertNotEquals(question.getDescription(), "");
    }

    @Test
    public void testIfQuestionDescriptionIsBlank() throws Exception{
        try {
            Question question = new Question(1, "", alternatives, "c");
        }catch (IllegalArgumentException blankDescription) {
            assertEquals(blankDescription.getMessage(), "blank description");
        }
    }

    @Test
    public void testIfQuestionCorrectAnswerIsNotBlank() throws Exception{
        Question question = new Question(1, description, alternatives, "c");

        assertNotEquals(question.getCorrectAnswer(), "");
    }

    @Test
    public void testIfQuestionCorrectAnswerIsBlank() throws Exception{
        try {
            Question question = new Question(1, description, alternatives, "");
        }catch (IllegalArgumentException blankCorrectAnswer) {
            assertEquals(blankCorrectAnswer.getMessage(), "blank correct answer");
        }
    }

    @Test
    public void testIfAnAlternativeIsNotBlank() throws Exception{
        Question question = new Question(1, description, alternatives, "c");

        assertNotEquals(question.getAlternatives().get("a"), "");
    }

    @Test
    public void testIfAnAlternativeIsBlank() throws Exception{
        try {
            alternatives.put("a", "");
            Question question = new Question(1, description, alternatives, "c");
        }catch (IllegalArgumentException blankAlternative) {
            assertEquals(blankAlternative.getMessage(), "blank 'a' alternative");
        }
    }

    @Test
    public void testIfAlternativesIsNotNull() throws Exception{
        Question question = new Question(1, description, alternatives, "c");

        assertNotEquals(question.getAlternatives(), null);
    }

    @Test
    public void testIfAlternativesIsNull() throws Exception{
        try {
            alternatives = null;
            Question question = new Question(1, description, alternatives, "");
        }catch (IllegalArgumentException nullAlternatives) {
            assertEquals(nullAlternatives.getMessage(), "null alternatives");
        }
    }

    @Test
    public void testIfQuestionIdIsNotNegative() throws Exception{
        Question question = new Question(1, description, alternatives, "c");

        assert question.getId() >= 0;
    }

    @Test
    public void testIfQuestionIdIsNegative() throws Exception{
        try {
            Question question = new Question(-1, description, alternatives, "c");
        }catch (IllegalArgumentException negativeId) {
            assertEquals(negativeId.getMessage(), "negative id");
        }
    }

    @Test
    public void testIfCorrectAnswerIsIntoQuestionAlternatives() throws Exception{
        Question question = new Question(1, description, alternatives, "c");

        assert alternatives.containsKey(question.getCorrectAnswer());
    }

    @Test
    public void testIfCorrectAnswerIsNotIntoQuestionAlternatives() throws Exception{
        Question question = new Question(1, description, alternatives, "f");

        assertFalse(alternatives.containsKey(question.getCorrectAnswer()));
    }
}
