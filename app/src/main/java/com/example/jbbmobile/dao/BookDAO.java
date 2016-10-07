package com.example.jbbmobile.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;


import com.example.jbbmobile.model.Book;

public class BookDAO extends SQLiteOpenHelper {
    private static final String NAME_DB="JBB";
    private static final int VERSION=1;

    public static String COLUMN_IDBOOK = "idBook";
    public static String COLUMN_NAMEBOOK = "nameBook";
    public static String TABLE = "BOOK";

    public BookDAO(Context context) {
        super(context,NAME_DB, null, VERSION);
    }

    public void createTable(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("CREATE TABLE  IF NOT EXISTS " + TABLE + " (" +
                COLUMN_IDBOOK + " INTEGER NOT NULL, " +
                COLUMN_NAMEBOOK + " VARCHAR(45) NOT NULL, " +
                " CONSTRAINT " + TABLE + "_PK PRIMARY KEY (" + COLUMN_IDBOOK + "))");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE);
        createTable(sqLiteDatabase);
    }

    @NonNull
    private ContentValues getBook(Book book) {
        ContentValues data = new ContentValues();
        data.put(COLUMN_NAMEBOOK, book.getNameBook());
        data.put(COLUMN_IDBOOK, book.getIdBook());
        return data;
    }

    public int insertBook(Book book) throws SQLiteConstraintException {
        SQLiteDatabase dataBase = getWritableDatabase();
        int insertReturn;
        ContentValues data = getBook(book);

        insertReturn = (int) dataBase.insert(TABLE, null, data);

        return  insertReturn;
    }

    public Book findBook(int idBook){
        SQLiteDatabase dataBase = getWritableDatabase();
        Cursor cursor;

        cursor = dataBase.query(TABLE, new String[] {COLUMN_IDBOOK, COLUMN_NAMEBOOK}, COLUMN_IDBOOK + " = " + idBook ,null, null , null ,null);
        Book book = new Book();

        if(cursor.moveToFirst()){
            book.setIdBook(cursor.getShort(cursor.getColumnIndex(COLUMN_IDBOOK)));
            book.setNameBook(cursor.getString(cursor.getColumnIndex(COLUMN_NAMEBOOK)));
        }

        cursor.close();
        return book;
    }
}