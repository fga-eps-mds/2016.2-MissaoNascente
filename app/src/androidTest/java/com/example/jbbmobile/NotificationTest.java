package com.example.jbbmobile;


import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class NotificationTest {

    @Test
    public testIfNotificationIsCreate() throws Exception{
        Notification notification = new Notification("Novo período", "Período 1", "/image", "2016-01-01");
        String descriptionCreate = "Novo período";
        assertEquals(descriptionCreate, notification.getDescription());
    }

    @Test
    public testIfTitleIsEquals0() throws Exception{
        boolean result = false;
        try {
            Notification notification = new Notification("Novo período", "", "/image", "2016-01-01");
        }catch (IllegalArgumentException exception){
            result = exception.getMessage().equals("Invalid title");
        }
        assertTrue(result);
    }

    @Test
    public testIfTitleIsMore80() throws Exception{
        boolean result = false;
        try {
            Notification notification = new Notification("Novo período", "Período 1 - Período 1 - Período 1 - Período 1 - Período 1 Período 1 - Período 1 - Período 1 - Período 1 - Período 1", "/image", "2016-01-01");
        }catch (IllegalArgumentException exception){
            result = exception.getMessage().equals("Invalid title");
        }
        assertTrue(result);
    }

    @Test
    public testIfDateIsValid() throws Exception{
        Date dateValid = "2016-01-01";
        try {
            Notification notification = new Notification("Novo período", "Período 1", "/image", "2016-01-01");
        }catch (IllegalArgumentException exception){
            exception.getMessage().equals("Invalid Date");
        }
        assertEquals(notification.getDate(), dateValid);
    }

    @Test
    public testIfDateIsNotValid() throws Exception{
        int result = false;
        try {
            Notification notification = new Notification("Novo período", "Período 1", "/image", "2016-13-01");
        }catch (IllegalArgumentException exception){
            result = exception.getMessage().equals("Invalid Date");
        }
        assertTrue(result);
    }

}
