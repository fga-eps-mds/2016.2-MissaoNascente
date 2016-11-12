package gov.jbb.missaonascente;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import gov.jbb.missaonascente.dao.AlternativeDAO;
import gov.jbb.missaonascente.model.Alternative;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AlternativeDAOTest {

    private final String ALTERNATIVE_DESCRIPTION = "Description";
    private final String ALTERNATIVE_LETTER = "c";
    private final int ALTERNATIVE_ID = 1;
    private final int QUESTION_ID = 1;
    private final int ALTERNATIVE_2_ID = 2;
    private Alternative alternative;
    private AlternativeDAO alternativeDAO;

    @Before
    public void setup(){
        Context context = InstrumentationRegistry.getTargetContext();

        alternative = new Alternative(ALTERNATIVE_ID, ALTERNATIVE_LETTER, ALTERNATIVE_DESCRIPTION, QUESTION_ID);
        alternativeDAO = new AlternativeDAO(context);
        alternativeDAO.onUpgrade(alternativeDAO.getWritableDatabase(), 1,1);
    }

    @Test
    public void testIfInsertAlternativeWasSuccessful()throws Exception{
        int insertSucessful = alternativeDAO.insertAlternative(alternative);
        assertNotSame(-1, insertSucessful);
    }

    @Test
    public void testIfInsertAlternativeWasNotSuccessful()throws Exception{
        int insertNotSuccessful = alternativeDAO.insertAlternative(new Alternative());
        assertEquals(-1, insertNotSuccessful);
    }

    @Test
    public void testIfDeleteAlternativeWasSuccessful()throws Exception{
        alternativeDAO.insertAlternative(alternative);
        int deleteSuccessful = alternativeDAO.deleteAlternative(alternative);

        assertEquals(1, deleteSuccessful);
    }

    @Test
    public void testIfFindAlternativeWasSuccessful()throws Exception{
        alternativeDAO.insertAlternative(alternative);
        Alternative foundAlternative = alternativeDAO.findAlternative(alternative.getIdAlternative());

        assertEquals(alternative.getIdAlternative(), foundAlternative.getIdAlternative());
    }

    @Test(expected = Exception.class)
    public void testIfFindAlternativeWasNotSuccessful() throws Exception{
        alternativeDAO.findAlternative(alternative.getIdAlternative());
    }

    @Test
    public void testIfFindAlternativeFromQuestionWasSucessful() throws Exception{
        Alternative alternative2 = new Alternative(ALTERNATIVE_2_ID, ALTERNATIVE_LETTER,
                ALTERNATIVE_DESCRIPTION, QUESTION_ID);
        alternativeDAO.insertAlternative(alternative2);
        alternativeDAO.insertAlternative(alternative);

        List<Alternative> alternativeList = alternativeDAO.findQuestionAlternatives(QUESTION_ID);

        assertEquals(ALTERNATIVE_2_ID, alternativeList.get(1).getIdAlternative());
        assertEquals(ALTERNATIVE_ID, alternativeList.get(0).getIdAlternative());
    }
}
