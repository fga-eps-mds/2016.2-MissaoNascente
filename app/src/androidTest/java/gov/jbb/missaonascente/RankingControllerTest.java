package gov.jbb.missaonascente;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import gov.jbb.missaonascente.controller.RankingController;
import gov.jbb.missaonascente.dao.ExplorerDAO;

import org.junit.Before;
import org.junit.Test;


public class RankingControllerTest {

    private Context context;
    private  RankingController controller;
    private ExplorerDAO explorerDAO;

    @Before
    public void setup() throws Exception{
        Context context = InstrumentationRegistry.getTargetContext();
        this.context = context;
        controller = new RankingController();
        explorerDAO = new ExplorerDAO(context);
        explorerDAO.onUpgrade(explorerDAO.getReadableDatabase(),2,2);
    }

    @Test
    public void testIfExplorerListIsCreated () throws Exception{
        try {
            controller.updateRanking(context);
        }catch (Exception explorerException){
            explorerException.printStackTrace();
        }
    }


}
