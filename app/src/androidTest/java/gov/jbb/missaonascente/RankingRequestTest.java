package gov.jbb.missaonascente;

import gov.jbb.missaonascente.dao.RankingRequest;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;


public class RankingRequestTest {

    @Test
    public void testIfRequestIsCreated(){
        RankingRequest rankingRequest = new RankingRequest("nickname");
        assertEquals("nickname", rankingRequest.getNickname());
    }

}
