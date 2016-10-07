package com.example.jbbmobile.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import com.example.jbbmobile.model.Explorer;

public class ExplorerDAO extends SQLiteOpenHelper{
    private static final String NAME_DB="JBB";
    private static final int VERSION=1;

    public static String COLUMN_NICKNAME ="nickname";
    public static String COLUMN_EMAIL ="email";
    public static String COLUMN_PASSWORD ="password";

    public static String TABLE ="EXPLORER";

    public ExplorerDAO(Context context) {
        super(context,NAME_DB, null, VERSION);
    }

    public void createTableExplorer(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE + " (" +
                COLUMN_NICKNAME + " VARCHAR(12) UNIQUE NOT NULL, " +
                COLUMN_EMAIL + " VARCHAR(45) NOT NULL, " +
                COLUMN_PASSWORD + " VARCHAR(12) NOT NULL, " +
                "CONSTRAINT " + TABLE + "_PK PRIMARY KEY (" + COLUMN_EMAIL + "))");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE);
        createTableExplorer(sqLiteDatabase);
    }

    @NonNull
    private ContentValues getExplorerData(Explorer explorer) {
        ContentValues data = new ContentValues();
        data.put(COLUMN_NICKNAME, explorer.getNickname());
        data.put(COLUMN_EMAIL, explorer.getEmail());
        data.put(COLUMN_PASSWORD, explorer.getPassword());
        return data;
    }

    public int insertExplorer(Explorer explorer) throws SQLiteConstraintException {
        SQLiteDatabase dataBase = getWritableDatabase();
        int insertReturn;
        ContentValues data = getExplorerData(explorer);

        insertReturn = (int) dataBase.insert(TABLE, null, data);

        return  insertReturn;
    }

    public Explorer findExplorer(String email){
        SQLiteDatabase dataBase = getWritableDatabase();
        Cursor cursor;
        cursor = dataBase.query(TABLE,new String[] {COLUMN_NICKNAME,COLUMN_EMAIL,COLUMN_PASSWORD}, COLUMN_EMAIL + " ='" + email +"'",null, null , null ,null);

        Explorer explorer = new Explorer();

        if(cursor.moveToFirst()){
            explorer.setNickname(cursor.getString(cursor.getColumnIndex(COLUMN_NICKNAME)));
            explorer.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
            explorer.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
        }

        cursor.close();
        return explorer;
    }

    public Explorer findExplorerLogin(String email, String password){
        SQLiteDatabase dataBase = getWritableDatabase();
        Cursor cursor;
        cursor = dataBase.query(TABLE, new String[] {COLUMN_NICKNAME,COLUMN_EMAIL,COLUMN_PASSWORD}, COLUMN_EMAIL + " ='" + email +"' AND " + COLUMN_PASSWORD + " ='" + password +"'",null, null , null ,null);

        Explorer explorer = new Explorer();

        if(cursor.moveToFirst()){
            explorer.setNickname(cursor.getString(cursor.getColumnIndex(COLUMN_NICKNAME)));
            explorer.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
            explorer.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
        }

        cursor.close();
        return explorer;
    }

    public void updateExplorer(Explorer explorer) throws SQLiteConstraintException{
        SQLiteDatabase dataBase = getWritableDatabase();
        ContentValues data = getExplorerData(explorer);

        String[] parameters = {explorer.getEmail()};

        dataBase.update(TABLE, data, COLUMN_EMAIL + " = ?", parameters);
    }

    public void deleteExplorer(Explorer explorer){
        SQLiteDatabase dataBase = getWritableDatabase();
        String[] parameters = {explorer.getEmail()};

        dataBase.delete(TABLE, COLUMN_EMAIL + " = ?", parameters);
    }
}