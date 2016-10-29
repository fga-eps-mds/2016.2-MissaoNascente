package com.example.jbbmobile;

import android.annotation.TargetApi;
import android.content.Context;
import java.text.SimpleDateFormat;
import android.support.test.InstrumentationRegistry;

import com.example.jbbmobile.controller.NotificationController;
import com.example.jbbmobile.dao.NotificationDAO;
import com.example.jbbmobile.model.Notification;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class NotificationControllerTest {
    private Context context;
    private NotificationDAO  notificationDAO;

    @Before
    public void setup(){
        context = InstrumentationRegistry.getTargetContext();
        notificationDAO = new NotificationDAO(context);
        notificationDAO.onUpgrade(notificationDAO.getWritableDatabase(),1,1);
    }

    @Test
    public void testIfNotificationByPeriodIsNotRegister(){
        NotificationController notificationController = new NotificationController(context);
        Notification notification = notificationController.notificationByPeriod();
        assertEquals(notification,null);
    }


    @Test
    public void testIfNotificationByPeriodIsRegister(){
        long date = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateNotification = simpleDateFormat.format(date);
        Notification notificationRegister = new Notification(1, "Período 1", "Novo período", dateNotification);
        notificationDAO.insertNotification(notificationRegister);
        NotificationController notificationController = new NotificationController(context);
        Notification notification = notificationController.notificationByPeriod();
        assertEquals(notification.toString(),notificationRegister.toString());
    }
}
