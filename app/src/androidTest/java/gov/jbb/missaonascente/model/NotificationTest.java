package gov.jbb.missaonascente.model;


import gov.jbb.missaonascente.model.Notification;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class NotificationTest {

    @Test
    public void testIfNotificationIsCreate() throws Exception{
        Notification notification = new Notification(1, "Período 1", "Novo período", "2016-01-01");
        String descriptionCreate = "Novo período";
        assertEquals(descriptionCreate, notification.getDescription());
    }

    @Test
    public void testIfTitleIsEquals0() throws Exception{
        boolean result = false;
        try {
            Notification notification = new Notification(1, "", "Novo período",  "2016-01-01" );
        }catch (IllegalArgumentException exception){
            result = exception.getMessage().equals("Invalid title");
        }
        assertTrue(result);
    }

    @Test
    public void testIfTitleIsMore80() throws Exception{
        boolean result = false;
        try {
            Notification notification = new Notification(1, "Período 1 - Período 1 - Período 1 - Período 1 - Período 1 Período 1 - Período 1 - Período 1 - Período 1 - Período 1", "Novo período", "2016-01-01");
        }catch (IllegalArgumentException exception){
            result = exception.getMessage().equals("Invalid title");
        }
        assertTrue(result);
    }

    @Test
    public void testIfDateIsValid() throws Exception{
        String dateValid = "2016-01-01";
        Notification notification = null;
        try {
            notification = new Notification(1, "Período 1", "Novo período", "2016-01-01");
        }catch (IllegalArgumentException exception){
            exception.getMessage().equals("Invalid date");
        }
        assertEquals(notification.getDate(), dateValid);
    }

    @Test
    public void testIfDateIsNotValid() throws Exception{
        boolean result = false;
        Notification notification = null;
        try {
            notification = new Notification( 1, "Período 1", "Novo período", "2016-13-01");
        }catch (IllegalArgumentException exception){
            result = exception.getMessage().equals("Invalid date");
        }
        assertTrue(result);
    }

}
