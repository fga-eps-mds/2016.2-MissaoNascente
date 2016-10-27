package com.example.jbbmobile;

import android.content.Context;
import android.database.SQLException;
import android.support.test.InstrumentationRegistry;

import com.example.jbbmobile.dao.NotificationDAO;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class NotificationDAOTest {
    private NotificationDAO notificationDAO;

    @Before
    public void setup (){
        Context context = InstrumentationRegistry.getTargetContext();
        notificationDAO = new NotificationDAO(context);
        notificationDAO.onUpgrade(notificationDAO.getWritableDatabase(),1,1);
    }

    @Test
    public void testIfInsertNotificationSuccessful(){
        Notification notification = new Notification(description, title, image, date);
        int result;
        result = notificationDAO.insertNotification(notification);
        assertEquals(result, 1);
    }

    @Test(expected = SQLException.class)
    public void testIfInsertNotificationNotIsSuccessful(){
        Notification notification = new Notification(description, title, image, date);
        int result;
        result = notificationDAO.insertNotification(notification);
    }

    @Test
    public void testIfSelectNotificationDescription(){
        Notification notification = new Notification(description, title, image, date);
        notificationDAO.insert(notification);
        Date findDate = "2016-10-01";
        Notification notificationResult;
        notificationResult = notificationDAO.findNotification(findDate);
        assertEquals(notification.getDescription(), notificationResult.getDescription());
    }

    @Test(expected = SQLException.class)
    public void testIfNotSelectNotification(){
        Notification notification = new Notification(description, title, image, date);
        Date findDate = "2016-10-01";;
        Notification notificationResult;
        notificationResult = notificationDAO.findNotification(findDate);
    }

    @Test
    public void testIfUpdateNotificationDescription(){
        Notification notification = new Notification(description, title, image, date);
        notificationDAO.insert(notification);
        Notification notificationUpdate;
        notificationUpdate = new Notification(descriptionUpdate, title, image, date);
        int result;
        result = notificationDAO.updateNotification(notificationUpdate);
        assertEquals(result, 1);
    }

    @Test
    public void testIfNotUpdateNotificationDescription(){
        Notification notificationUpdate;
        notificationUpdate = new Notification(descriptionUpdate, title, image, date);
        int result;
        result = notificationDAO.updateNotification(notificationUpdate);
        assertEquals(result, 0);
    }

    @Test
    public void testIfDeleteNotificationDescription(){
        Notification notification = new Notification(description, title, image, date);
        notificationDAO.insert(notification);
        int result;
        String titleDelete = notifiction.getTitle();
        result = notificationDAO.deleteNotification(titleDelete);
        assertEquals(result, 1);
    }

    @Test
    public void testIfDeleteNotificationDescription(){
        int result;
        String titleDelete = notifiction.getTitle();
        result = notificationDAO.deleteNotification(titleDelete);
        assertEquals(result, 0);
    }
}
