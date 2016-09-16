package com.example.jbbmobile.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.example.jbbmobile.model.Books;

/**
 * Created by ronyell on 14/09/16.
 */
public class BookDAO extends SQLiteOpenHelper {
    private static final String NAME_DB="JBB";
    private static final int VERSION=1;

    public BookDAO(Context context) {
        super(context,NAME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE BOOK (idBook integer primary key not null, nameBook integer)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE BOOK");
        onCreate(sqLiteDatabase);
    }

    @NonNull
    private ContentValues getBook(Books books) {
        ContentValues data = new ContentValues();
        data.put("idBook", books.getIdBook());
        data.put("nameBook", books.getNameBook());
        return data;
    }

    public Books findBook(Books books){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c;
        c= db.query("BOOK",new String[] { "idBook" ,"nameBook" }, "idBook = " + books.getIdBook() ,null, null , null ,null);

        Books books1 = new Books();
        if(c.moveToFirst()){
            books1.setIdBook(c.getShort(c.getColumnIndex("idBook")));
            books1.setNameBook(c.getShort(c.getColumnIndex("nameBook")));
        }
        c.close();
        return books1;
    }


}
