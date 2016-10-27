package com.example.jbbmobile.dao;

import com.example.jbbmobile.model.Notification;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

public class NotificationDAO extends SQLiteOpenHelper{
    private static final String NAME_DB="JBB";
    private static final int VERSION=1;

    protected static String TABLE = "NOTIFICATION";

    protected static String COLUMN_IDNOTIFICATION = "idNotification";
    protected static String COLUMN_TITLE = "title";
    protected static String COLUMN_DESCRIPTION = "description";
    protected static String COLUMN_IMAGE = "image";
    protected static String COLUMN_DATE = "date";

    public NotificationDAO(Context context) {
        super(context,NAME_DB, null, VERSION);
    }

    public static void createTableNotification(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE +" ( " +
                               COLUMN_IDNOTIFICATION + " INTEGER NOT NULL, " +
                               COLUMN_TITLE + " VARCHAR(80) NOT NULL, " +
                               COLUMN_DATE + " DATE NOT NULL, " +
                               COLUMN_DESCRIPTION + " VARCHAR(300), " +
                               COLUMN_IMAGE + "BLOB, " +
                               "CONSTRAINT " + TABLE + "_PK  PRIMARY KEY ("+ COLUMN_IDNOTIFICATION +"))");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {}

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP DATABASE IF EXISTS " + TABLE);
        createTableNotification(sqLiteDatabase);
    }

    @NonNull
    private ContentValues getNotificationData(Notification notification) {
        ContentValues data = new ContentValues();
        data.put(COLUMN_IDNOTIFICATION, notification.getIdNotification());
        data.put(COLUMN_TITLE, notification.getTitle());
        data.put(COLUMN_DESCRIPTION, notification.getDescription());
        data.put(COLUMN_DATE, notification.getDate());
        data.put(COLUMN_IMAGE, notification.getImage());
        return data;
    }

    public int insertNotification(Notification notification){
        SQLiteDatabase dataBase = getWritableDatabase();
        int insertReturn;

        ContentValues data = getNotificationData(notification);

        insertReturn = (int) dataBase.insertOrThrow(TABLE, null, data);

        return insertReturn;
    }

    public Notification findNotification(int idNotification){
        SQLiteDatabase dataBase = getWritableDatabase();
        Cursor cursor;
        cursor = dataBase.query(TABLE, new String[] {COLUMN_IDNOTIFICATION, COLUMN_TITLE,COLUMN_DESCRIPTION,COLUMN_DATE,COLUMN_IMAGE}, COLUMN_IDNOTIFICATION + " = " + idNotification ,null, null , null ,null);

        Notification notification = new Notification();
        if(cursor.moveToFirst()){
            notification.setIdNotification(cursor.getShort(cursor.getColumnIndex(COLUMN_IDNOTIFICATION)));
            notification.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
            notification.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
            notification.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
            notification.setImage(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE)));
        }else{
            throw new SQLException();
        }
        return notification;
    }
}
