package gov.jbb.missaonascente;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import gov.jbb.missaonascente.controller.AlternativeController;
import gov.jbb.missaonascente.dao.AlternativeDAO;
import gov.jbb.missaonascente.model.Alternative;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AlternativeControllerTest {
    private Context context;
    private AlternativeController controller;
    private AlternativeDAO alternativeDAO;

    @Before
    public void setup() throws Exception{
        Context context = InstrumentationRegistry.getTargetContext();
        this.context = context;
        controller = new AlternativeController();
        alternativeDAO = new AlternativeDAO(context);
        alternativeDAO.onUpgrade(alternativeDAO.getReadableDatabase(),2,2);
    }

    @Test
    public void testIfQuestionsWereDownloaded() throws Exception{
        controller.downloadAllAlternatives(context);
        while(!controller.isAction());
        Alternative alternative = alternativeDAO.findAlternative(1);
        assertEquals(1,alternative.getIdAlternative());
    }
}