package gov.jbb.missaonascente;

import gov.jbb.missaonascente.model.Alternative;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static junit.framework.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AlternativeTest {

    private final String ALTERNATIVE_DESCRIPTION = "Description";
    private final String ALTERNATIVE_LETTER = "c";
    private final int ALTERNATIVE_ID = 1;
    private final int QUESTION_ID = 1;
    private Alternative alternative;
    @Before
    public void setup(){
        alternative = new Alternative(ALTERNATIVE_ID, ALTERNATIVE_LETTER, ALTERNATIVE_DESCRIPTION, QUESTION_ID);
    }

    @Test
    public void testIfAlternativeWasCreatedWithId(){
        assertEquals(ALTERNATIVE_ID, alternative.getIdAlternative());
    }

    @Test
    public void testIfAlternativeWasCreatedWithLetter(){
        assertEquals(ALTERNATIVE_LETTER, alternative.getAlternativeLetter());
    }

    @Test
    public void testIfAlternativeWasCreatedWithDescription(){
        assertEquals(ALTERNATIVE_DESCRIPTION, alternative.getAlternativeDescription());
    }

    @Test
    public void testIfAlternativeWasCreatedWithQuestionId(){
        assertEquals(QUESTION_ID, alternative.getIdQuestion());
    }
}