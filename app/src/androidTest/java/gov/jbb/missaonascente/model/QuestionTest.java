package gov.jbb.missaonascente.model;
import gov.jbb.missaonascente.model.Alternative;
import gov.jbb.missaonascente.model.Question;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class QuestionTest {
    private final String DESCRIPTION = "O cerrrado é um bioma característico de qual região do Brasil?";

    @Test
    public void testIfQuestionIsCreated() throws Exception{
        Question question = new Question(1, DESCRIPTION, "c", 2);
        assertEquals(question.getIdQuestion(), 1);
    }

    @Test
    public void testIfQuestionDescriptionIsNotBlank() throws Exception{
        Question question = new Question(1, DESCRIPTION, "c", 2);
        assertNotEquals(question.getDescription(), "");
    }

    @Test
    public void testIfQuestionDescriptionIsBlank() throws Exception{
        try {
            Question question = new Question(1, "", "c", 2);
        }catch (IllegalArgumentException blankDescription) {
            assertEquals(blankDescription.getMessage(), "blank description");
        }
    }

    @Test
    public void testIfQuestionCorrectAnswerIsNotBlank() throws Exception{
        Question question = new Question(1, DESCRIPTION, "c", 2);
        assertNotEquals(question.getCorrectAnswer(), "");
    }

    @Test
    public void testIfQuestionCorrectAnswerIsBlank() throws Exception{
        try {
            Question question = new Question(1, DESCRIPTION, "", 2);
        }catch (IllegalArgumentException blankCorrectAnswer) {
            assertEquals(blankCorrectAnswer.getMessage(), "blank correct answer");
        }
    }

    @Test
    public void testIfQuestionIdIsNotNegative() throws Exception{
        Question question = new Question(1, DESCRIPTION, "c", 2);
        assertTrue(question.getIdQuestion() >= 0);
    }

    @Test
    public void testIfQuestionIdIsNegative() throws Exception{
        try {
            Question question = new Question(-1, DESCRIPTION, "c", 2);
        }catch (IllegalArgumentException negativeId) {
            assertEquals(negativeId.getMessage(), "negative Question id");
        }
    }

    @Test
    public void testIfAlternativeQuantityIsGreaterThan1() throws Exception{
        Question question = new Question(1, DESCRIPTION, "c", 2);
        assertTrue(question.getAlternativeQuantity() > 1);
    }

    @Test
    public void testIfAlternativeQuantityIsNotGreaterThan1() throws Exception{
        try {
            Question question = new Question(2, DESCRIPTION, "c", 1);
        }catch (IllegalArgumentException invalidAlternativeQuantity) {
            assertEquals(invalidAlternativeQuantity.getMessage(), "invalid alternative quantity");
        }
    }

    @Test
    public void testIfAlternativeListIsGreaterThan1() throws Exception{
        Question question = new Question(1, DESCRIPTION, "c", 2);
        ArrayList alternatives = new ArrayList();
        Alternative alternative = new Alternative(1,"Teste","a",1);
        alternatives.add(alternative);
        alternative = new Alternative(2,"Teste","a",1);
        alternatives.add(alternative);
        question.setAlternativeList(alternatives);

        assertTrue(question.getAlternativeList().size() > 1);
    }

    @Test
    public void testIfAlternativeListIsNotGreaterThan1() throws Exception{
        try {
            Question question = new Question(1, DESCRIPTION, "c", 2);
            ArrayList alternatives = new ArrayList();
            question.setAlternativeList(alternatives);
        }catch (IllegalArgumentException invalidAlternativeList) {
            assertEquals(invalidAlternativeList.getMessage(), "insufficient number of alternatives");
        }
    }
}
