package gov.jbb.missaonascente.controller;

import android.content.Context;
import android.database.SQLException;
import gov.jbb.missaonascente.dao.NotificationDAO;

import gov.jbb.missaonascente.dao.NotificationRequest;
import gov.jbb.missaonascente.model.Notification;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NotificationController {

    private Notification notification;
    private boolean action = false;
    private Context context;

    public NotificationController(Context context){
        this.context = context;
    }


    public void synchronizeNotification(){
        final NotificationDAO notificationDAO = new NotificationDAO(context);
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.request(context, new NotificationRequest.Callback() {
            @Override
            public void callbackResponse(List<Notification> notificationList) {
                insertAllNotification(notificationDAO,notificationList);
                setAction(true);
            }
        });

    }


    public void insertAllNotification(NotificationDAO notificationDAO , List<Notification> notificationList){
        for( Notification notification : notificationList){
            try {
                notificationDAO.insertNotification(notification);
            }catch (SQLException exception){
                exception.printStackTrace();
            }
        }
    }

    public Notification notificationByPeriod(){
        int systemMonth = 0;
        int systemDay = 0;
        long date;
        date = System.currentTimeMillis();

        SimpleDateFormat monthSimpleDateFormat = new SimpleDateFormat("MM");
        String month = monthSimpleDateFormat.format(date);
        systemMonth = Integer.valueOf(month);

        SimpleDateFormat daySimpleDateFormat = new SimpleDateFormat("dd");
        String day = daySimpleDateFormat.format(date);
        systemDay = Integer.valueOf(day);

        NotificationDAO notificationDAO = new NotificationDAO(context);

        List <Notification> notifications =  notificationDAO.findAllNotification();

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Notification notificationReturn = null;
        for(Notification notification : notifications){
            try {
                Date dateNotification = (Date)formatter.parse(notification.getDate());

                String monthNotificationFormat = monthSimpleDateFormat.format(dateNotification);
                int monthNotification = Integer.valueOf(monthNotificationFormat);

                String dayNotificationFormat = daySimpleDateFormat.format(dateNotification);
                int dayNotification = Integer.valueOf(dayNotificationFormat);

                if(monthNotification == systemMonth && dayNotification == systemDay){
                    notificationReturn = notification;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    return notificationReturn;
    }

    public boolean isAction() {
        return action;
    }

    public void setAction(boolean action) {
        this.action = action;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}

