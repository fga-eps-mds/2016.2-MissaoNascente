package gov.jbb.missaonascente.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import gov.jbb.missaonascente.model.Achievement;
import gov.jbb.missaonascente.model.Explorer;

public class AchievementDAO extends SQLiteOpenHelper {
    private static final String NAME_DB = "JBB";
    private static final int VERSION = 1;

    protected static String TABLE = "ACHIEVEMENT";
    protected static String TABLE_ASSOCIATE = "ACHIEVEMENT_EXPLORER";

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
                "CONSTRAINT " + TABLE + "_PK  PRIMARY KEY (" + COLUMN_ID_ACHIEVEMENT + "))");
    }

    public static void createAchievementExplorer(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ASSOCIATE + " (" +
                COLUMN_ID_ACHIEVEMENT + " INTEGER NOT NULL, " +
                ExplorerDAO.COLUMN_EMAIL + " VARCHAR(255) NOT NULL, " +
                "CONSTRAINT " + TABLE_ASSOCIATE + "_PK  PRIMARY KEY ("+ COLUMN_ID_ACHIEVEMENT + " , " + ExplorerDAO.COLUMN_EMAIL +"), " +
                "CONSTRAINT " + TABLE +"_FK FOREIGN KEY (" + COLUMN_ID_ACHIEVEMENT + ") REFERENCES " + TABLE + " , " +
                "CONSTRAINT " + ExplorerDAO.TABLE + "_FK FOREIGN KEY (" + ExplorerDAO.COLUMN_EMAIL + ") REFERENCES " + ExplorerDAO.TABLE + ")");

        Log.d("RASENSHURIKEN", "CREATE TABLE IF NOT EXISTS " + TABLE_ASSOCIATE + " (" +
                COLUMN_ID_ACHIEVEMENT + " INTEGER NOT NULL, " +
                ExplorerDAO.COLUMN_EMAIL + " VARCHAR(255) NOT NULL, " +
                "CONSTRAINT " + TABLE_ASSOCIATE + "_PK  PRIMARY KEY ("+ COLUMN_ID_ACHIEVEMENT + " , " + ExplorerDAO.COLUMN_EMAIL +"), " +
                "CONSTRAINT " + TABLE +"_FK FOREIGN KEY (" + COLUMN_ID_ACHIEVEMENT + ") REFERENCES " + TABLE + " , " +
                "CONSTRAINT " + ExplorerDAO.TABLE + "_FK FOREIGN KEY (" + ExplorerDAO.COLUMN_EMAIL + ") REFERENCES " + ExplorerDAO.TABLE + ")");
    }

    public AchievementDAO(Context context) {
            super(context,NAME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSOCIATE);

        createTableAchievement(sqLiteDatabase);
        createAchievementExplorer(sqLiteDatabase);
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

    private ContentValues getAchievementExplorerData(int idAchievement, String email){
        ContentValues data = new ContentValues();
        data.put(COLUMN_ID_ACHIEVEMENT, idAchievement);
        data.put(ExplorerDAO.COLUMN_EMAIL, email);

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

    public Achievement findAchievement(int idAchievement){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        String[] columns = {COLUMN_ID_ACHIEVEMENT, COLUMN_NAME_ACHIEVEMENT,
                COLUMN_DESCRIPTION_ACHIEVEMENT, COLUMN_QUANTITY, COLUMN_KEYS};

        Cursor cursor;
        cursor = sqLiteDatabase.query(TABLE, columns, null ,null, null , null ,null);

        Achievement achievement = new Achievement();

        if (cursor.moveToFirst()){
            achievement.setIdAchievement(cursor.getInt(cursor.getColumnIndex(COLUMN_ID_ACHIEVEMENT)));
            achievement.setNameAchievement(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ACHIEVEMENT)));
            achievement.setDescriptionAchievement(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION_ACHIEVEMENT)));
            achievement.setQuantity(cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY)));
            achievement.setKeys(cursor.getInt(cursor.getColumnIndex(COLUMN_KEYS)));

        }
        cursor.close();

        return achievement;
    }

    public int insertAchievementExplorer(int idAchievement, String email){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues data = getAchievementExplorerData(idAchievement, email);

        int insertReturn = (int) sqLiteDatabase.insert(TABLE_ASSOCIATE, null, data);

        return insertReturn;
    }

    public int deleteAchievementExplorer(Achievement achievement, Explorer explorer){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String [] parameters = {String.valueOf(achievement.getIdAchievement()), explorer.getEmail()};

        int deleteReturn = sqLiteDatabase.delete(TABLE_ASSOCIATE, COLUMN_ID_ACHIEVEMENT +  " = ? AND " + ExplorerDAO.COLUMN_EMAIL + " = ?",parameters);

        return deleteReturn;
    }

    public List<Achievement> findAllExplorerAchievements(String email){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        String[] columns = {COLUMN_ID_ACHIEVEMENT};
        String query = ExplorerDAO.COLUMN_EMAIL + " ='" + email + "'";

        Cursor cursor;
        cursor = sqLiteDatabase.query(TABLE, columns, query ,null, null , null ,null);

        List<Achievement> achievements = new ArrayList<>();

        while (cursor.moveToNext()){
            int idAchievement = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_ACHIEVEMENT));
            Achievement achievement = findAchievement(idAchievement);
            achievements.add(achievement);
        }

        cursor.close();

        return achievements;
    }

    public List<Achievement> findRemainingExplorerAchievements(Explorer explorer){
        Collection<Achievement> achievements = findAllAchievements();
        Collection<Achievement> explorerAchievements = findAllExplorerAchievements(explorer.getEmail());
        achievements.removeAll(explorerAchievements);

        return new ArrayList<>(achievements);
    }

    public void deleteAllAchievementsFromAchievementExplorer(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("DELETE FROM " + AchievementDAO.TABLE_ASSOCIATE);
    }
}
