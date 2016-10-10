package com.example.jbbmobile.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.jbbmobile.model.Explorer;


import org.json.JSONException;
import org.json.JSONObject;

public class ExplorerDAO extends SQLiteOpenHelper{
    private static final String NAME_DB="JBB";
    private static final int VERSION=1;

    private static String COLUMN_NICKNAME ="nickname";
    static String COLUMN_EMAIL ="email";
    private static String COLUMN_PASSWORD ="password";

    static String TABLE ="EXPLORER";
    private Context context;

    public ExplorerDAO(Context context) {
        super(context,NAME_DB, null, VERSION);
        this.context = context;
    }

    public static void createTableExplorer(SQLiteDatabase sqLiteDatabase){
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
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE);
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

        insertExplorerOnOnlineDatabase(explorer);
        return  insertReturn;
    }

    private void insertExplorerOnOnlineDatabase(Explorer explorer){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean sucess = jsonObject.getBoolean("success");
                    if(!sucess){
                        Log.i("Erro JSON", "Erro");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        RegisterRequest registerRequest = new RegisterRequest(explorer.getNickname(), explorer.getPassword(),  explorer.getEmail(), responseListener );
        RequestQueue queue = Volley.newRequestQueue(this.context);
        queue.add(registerRequest);
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

    public int updateExplorer(Explorer explorer) throws SQLiteConstraintException{
        SQLiteDatabase dataBase = getWritableDatabase();
        ContentValues data = getExplorerData(explorer);

        String[] parameters = {explorer.getEmail()};

        int updateReturn;

        updateReturn = dataBase.update(TABLE, data, COLUMN_EMAIL + " = ?", parameters);

        return updateReturn;
    }

    public int deleteExplorer(Explorer explorer){
        SQLiteDatabase dataBase = getWritableDatabase();
        String[] parameters = {explorer.getEmail()};

        int deleteReturn;
        deleteReturn = dataBase.delete(TABLE, COLUMN_EMAIL + " = ?", parameters);

        return deleteReturn;
    }

}
