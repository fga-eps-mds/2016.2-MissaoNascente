package gov.jbb.missaonascente.dao;

import gov.jbb.missaonascente.model.Notification;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class NotificationDAO extends SQLiteOpenHelper{
    private static final String NAME_DB="JBB";
    private static final int VERSION=1;

    protected static String TABLE = "NOTIFICATION";

    protected static String COLUMN_IDNOTIFICATION = "idNotification";
    protected static String COLUMN_TITLE = "title";
    protected static String COLUMN_DESCRIPTION = "description";
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
                "CONSTRAINT " + TABLE + "_PK  PRIMARY KEY ("+ COLUMN_IDNOTIFICATION +"))");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {}

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE);
        createTableNotification(sqLiteDatabase);
    }

    @NonNull
    private ContentValues getNotificationData(Notification notification) {
        ContentValues data = new ContentValues();
        data.put(COLUMN_IDNOTIFICATION, notification.getIdNotification());
        data.put(COLUMN_TITLE, notification.getTitle());
        data.put(COLUMN_DESCRIPTION, notification.getDescription());
        data.put(COLUMN_DATE, notification.getDate());
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
        cursor = dataBase.query(TABLE, new String[] {COLUMN_IDNOTIFICATION, COLUMN_TITLE,COLUMN_DESCRIPTION,COLUMN_DATE}, COLUMN_IDNOTIFICATION + " = " + idNotification ,null, null , null ,null);

        Notification notification = new Notification();
        if(cursor.moveToFirst()){
            notification.setIdNotification(cursor.getShort(cursor.getColumnIndex(COLUMN_IDNOTIFICATION)));
            notification.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
            notification.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
            notification.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
        }else{
            throw new SQLException();
        }
        cursor.close();
        return notification;
    }

    public List<Notification> findAllNotification(){
        SQLiteDatabase dataBase = getWritableDatabase();
        Cursor cursor;
        cursor = dataBase.query(TABLE, new String[] {COLUMN_IDNOTIFICATION, COLUMN_TITLE,COLUMN_DESCRIPTION,COLUMN_DATE}, null ,null, null , null ,null);

        List<Notification> notifications = new ArrayList<>();

        while (cursor.moveToNext()){
            Notification notification = new Notification();
            notification.setIdNotification(cursor.getShort(cursor.getColumnIndex(COLUMN_IDNOTIFICATION)));
            notification.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
            notification.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
            notification.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
            notifications.add(notification);
        }
        cursor.close();

        return notifications;
    }

    public int updateNotification(Notification notification){
        SQLiteDatabase database = getWritableDatabase();

        ContentValues data = getNotificationData(notification);

        String[] parameters = {String.valueOf(notification.getIdNotification())};
        int updateReturn;
        updateReturn = database.update(TABLE, data, COLUMN_IDNOTIFICATION + " = ?", parameters);
        return updateReturn;
    }

    public int deleteNotification(int idNotification){
        SQLiteDatabase database = getWritableDatabase();

        String[] parameters = {String.valueOf(idNotification)};
        int deleteReturn;
        deleteReturn = database.delete(TABLE, COLUMN_IDNOTIFICATION + " = ?", parameters);
        return deleteReturn;
    }
}
