package gov.jbb.missaonascente.dao;

import android.content.Context;
import android.database.SQLException;
import android.support.test.InstrumentationRegistry;

import gov.jbb.missaonascente.dao.NotificationDAO;
import gov.jbb.missaonascente.model.Notification;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class NotificationDAOTest {
    private NotificationDAO notificationDAO;
    private Notification notification;

    @Before
    public void setup (){
        Context context = InstrumentationRegistry.getTargetContext();
        notificationDAO = new NotificationDAO(context);
        notificationDAO.onUpgrade(notificationDAO.getWritableDatabase(),1,1);
        notification = new Notification(1, "Período 1", "Novo período", "2016-01-01");
    }

    @Test
    public void testIfInsertNotificationSuccessful(){
        int result;
        result = notificationDAO.insertNotification(notification);
        assertEquals(result, 1);
    }

    @Test(expected = SQLException.class)
    public void testIfInsertNotificationNotIsSuccessful(){
        notificationDAO.insertNotification(notification);
        notificationDAO.insertNotification(notification);
    }

    @Test
    public void testIfSelectNotificationDescription(){
        notificationDAO.insertNotification(notification);
        int findID = 1;
        Notification notificationResult;
        notificationResult = notificationDAO.findNotification(findID);
        assertEquals(notification.getDescription(), notificationResult.getDescription());
    }

    @Test(expected = SQLException.class)
    public void testIfNotSelectNotification(){
        int findID = 1;
        notificationDAO.findNotification(findID);
    }

    @Test
    public void testIfUpdateNotificationDescription(){
        notificationDAO.insertNotification(notification);
        Notification notificationUpdate;
        notificationUpdate = new Notification(1, "Período 1", "Novo período 1", "2016-01-01");
        int result;
        result = notificationDAO.updateNotification(notificationUpdate);
        assertEquals(result, 1);
    }

    @Test
    public void testIfNotUpdateNotificationDescription(){
        Notification notificationUpdate;
        notificationUpdate = new Notification(1, "Período 1", "Novo período 1", "2016-01-01");
        int result;
        result = notificationDAO.updateNotification(notificationUpdate);
        assertEquals(result, 0);
    }

    @Test
    public void testIfDeleteNotificationID(){
        notificationDAO.insertNotification(notification);
        int result;
        int deleteID = notification.getIdNotification();
        result = notificationDAO.deleteNotification(deleteID);
        assertEquals(result, 1);
    }

    @Test
    public void testNotIfDeleteNotificationID(){
        int result;
        int deleteID = notification.getIdNotification();
        result = notificationDAO.deleteNotification(deleteID);
        assertEquals(result, 0);
    }
}
