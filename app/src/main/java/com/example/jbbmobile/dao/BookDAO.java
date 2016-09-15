package com.example.jbbmobile.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.example.jbbmobile.model.Book;
import com.example.jbbmobile.model.Explorers;

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
        sqLiteDatabase.execSQL("CREATE TABLE BOOK (idBook integer primary key not null, nameBook varchar(45))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE BOOK");
        onCreate(sqLiteDatabase);
    }

    @NonNull
    private ContentValues getBook(Book book) {
        ContentValues data = new ContentValues();
        data.put("idBook", book.getIdBook());
        data.put("nameBook", book.getNameBook());
        return data;
    }

    public Book findBook(Book book){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c;
        c= db.query("BOOK",new String[] { "idBook" ,"nameBook" }, "idBook = " + book.getIdBook() ,null, null , null ,null);

        Book book1 = new Book();
        if(c.moveToFirst()){
            book1.setIdBook(c.getShort(c.getColumnIndex("idBook")));
            book1.setNameBook(c.getString(c.getColumnIndex("nameBook")));
        }
        c.close();
        return book1;
    }


}
