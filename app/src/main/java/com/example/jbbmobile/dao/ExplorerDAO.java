package com.example.jbbmobile.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.example.jbbmobile.model.Explorers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ronyell on 09/09/16.
 */
public class ExplorerDAO extends SQLiteOpenHelper{
    private static final String NAME_DB="JBB";
    private static final int VERSION=1;


    public ExplorerDAO(Context context) {
        super(context,NAME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE EXPLORER (nickname text primary key not null, email text not null, password text not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE EXPLORER");
        onCreate(sqLiteDatabase);
    }

    @NonNull
    private ContentValues getExplorerData(Explorers explorer) {
        ContentValues data = new ContentValues();
        data.put("nickname", explorer.getNickname());
        data.put("email", explorer.getEmail());
        data.put("password", explorer.getPassword());
        return data;
    }

    public void insertExplorer(Explorers explorer) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues data = getExplorerData(explorer);

        db.insert("EXPLORER", null, data);
    }

    public List<Explorers> findExplorers() {
        String sql = "SELECT * FROM EXPLORER;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Explorers> explorers = new ArrayList<Explorers>();
        while (c.moveToNext()) {
            Explorers explorer = new Explorers();

            explorer.setNickname(c.getString(c.getColumnIndex("nickname")));
            explorer.setEmail(c.getString(c.getColumnIndex("email")));
            explorer.setPassword(c.getString(c.getColumnIndex("password")));


            explorers.add(explorer);
        }
        c.close();

        return explorers;
    }
}
