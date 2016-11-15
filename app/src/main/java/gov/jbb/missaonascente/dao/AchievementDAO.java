package gov.jbb.missaonascente.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import gov.jbb.missaonascente.model.Achievement;
import gov.jbb.missaonascente.model.Alternative;
import gov.jbb.missaonascente.model.Notification;

public class AchievementDAO extends SQLiteOpenHelper {
    private static final String NAME_DB = "JBB";
    private static final int VERSION = 1;

    protected static String TABLE = "ACHIEVEMENT";

    protected static String COLUMN_ID_ACHIEVEMENT = "idAchievement";
    protected static String COLUMN_NAME_ACHIEVEMENT = "nameAchievement";
    protected static String COLUMN_DESCRIPTION_ACHIEVEMENT = "descriptionAchievement";
    protected static String COLUMN_QUANTITY = "quantity";
    protected static String COLUMN_KEYS = "keys";

    public static void createTableAchievement(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE + " (" +
                                COLUMN_ID_ACHIEVEMENT + " INTEGER NOT NULL, " +
                                COLUMN_NAME_ACHIEVEMENT + " VARCHAR(255) NOT NULL, " +
                                COLUMN_DESCRIPTION_ACHIEVEMENT + " VARCHAR(511) NOT NULL, " +
                                COLUMN_QUANTITY + " INTEGER NOT NULL, " +
                                COLUMN_KEYS + " INTEGER NOT NULL, " +
                "CONSTRAINT " + TABLE + "_PK  PRIMARY KEY ("+ COLUMN_ID_ACHIEVEMENT +"))");
    }

    public AchievementDAO(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE);
        createTableAchievement(sqLiteDatabase);
    }

    private ContentValues getAchievementData(Achievement achievement){
        ContentValues data = new ContentValues();
        data.put(COLUMN_ID_ACHIEVEMENT, achievement.getIdAchievement());
        data.put(COLUMN_NAME_ACHIEVEMENT, achievement.getNameAchievement());
        data.put(COLUMN_DESCRIPTION_ACHIEVEMENT, achievement.getDescriptionAchievement());
        data.put(COLUMN_QUANTITY, achievement.getQuantity());
        data.put(COLUMN_KEYS, achievement.getKeys());

        return data;
    }

    public int insertAchievement(Achievement achievement){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues data = getAchievementData(achievement);

        int insertReturn = (int) sqLiteDatabase.insert(TABLE, null, data);

        return insertReturn;
    }

    public int updateAchievement(Achievement achievement){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues data = getAchievementData(achievement);
        String [] parameters = {String.valueOf(achievement.getIdAchievement())};

        int updateReturn = sqLiteDatabase.update(TABLE, data, COLUMN_ID_ACHIEVEMENT + " = ?", parameters);

        return updateReturn;
    }

    public int deleteAchievement(Achievement achievement){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String [] parameters = {String.valueOf(achievement.getIdAchievement())};

        int deleteReturn = sqLiteDatabase.delete(TABLE, COLUMN_ID_ACHIEVEMENT + " = ?", parameters);

        return deleteReturn;
    }

    public List<Achievement> findAllAchievements(){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        String[] columns = {COLUMN_ID_ACHIEVEMENT, COLUMN_NAME_ACHIEVEMENT,
                COLUMN_DESCRIPTION_ACHIEVEMENT, COLUMN_QUANTITY, COLUMN_KEYS};

        Cursor cursor;
        cursor = sqLiteDatabase.query(TABLE, columns, null ,null, null , null ,null);

        List<Achievement> achievements = new ArrayList<>();

        while (cursor.moveToNext()){
            Achievement achievement = new Achievement();
            achievement.setIdAchievement(cursor.getInt(cursor.getColumnIndex(COLUMN_ID_ACHIEVEMENT)));
            achievement.setNameAchievement(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ACHIEVEMENT)));
            achievement.setDescriptionAchievement(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION_ACHIEVEMENT)));
            achievement.setQuantity(cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY)));
            achievement.setKeys(cursor.getInt(cursor.getColumnIndex(COLUMN_KEYS)));

            achievements.add(achievement);
        }
        cursor.close();

        return achievements;
    }
}
