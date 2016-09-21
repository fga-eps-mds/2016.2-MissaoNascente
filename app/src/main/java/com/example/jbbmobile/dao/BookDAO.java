package com.example.jbbmobile.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;


import com.example.jbbmobile.model.Book;
import com.example.jbbmobile.model.Explorer;

import java.util.ArrayList;
import java.util.List;

public class BookDAO extends SQLiteOpenHelper {
    private static final String NAME_DB="JBB";
    private static final int VERSION=1;

    public BookDAO(Context context) {
        super(context,NAME_DB, null, VERSION);
    }


    public void createTable(SQLiteDatabase sqLiteDatabase){

        sqLiteDatabase.execSQL("CREATE TABLE  IF NOT EXISTS BOOK (idBook integer  primary key autoincrement, nameBook varchar(45), email text not null, " +
                    "FOREIGN KEY (email) REFERENCES EXPLORER(email) )");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE BOOK");
        onCreate(sqLiteDatabase);
    }

    @NonNull
    private ContentValues getBook(Book book) {
        ContentValues data = new ContentValues();
        data.put("nameBook", book.getNameBook());
        data.put("email", book.getExplorer().getEmail());
        return data;
    }

    public int insertBook(Book book) {

        SQLiteDatabase db = getWritableDatabase();
        int insertReturn = 0;
        ContentValues data = getBook(book);
        try{
            insertReturn = (int) db.insert("BOOK", null, data);
        }catch (SQLiteConstraintException e){

        }

        return  insertReturn;
    }

    public Book findBook(Book book){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c;

        c= db.query("BOOK",new String[] { "idBook" ,"nameBook","email"}, "idBook = " + book.getIdBook() ,null, null , null ,null);
        Book book1 = new Book();

        if(c.moveToFirst()){
            book1.setIdBook(c.getShort(c.getColumnIndex("idBook")));
            book1.setNameBook(c.getString(c.getColumnIndex("nameBook")));
            book1.setExplorer(new Explorer(c.getString(c.getColumnIndex("email"))));
        }
        c.close();
        return book1;
    }

    public List<Book> findBookExplorer(String email){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c;

        c= db.query("BOOK",new String[] { "idBook" ,"nameBook","email"}, "email = '" + email +"'" ,null, null , null ,null);
        List<Book> book = new ArrayList<Book>();

        while(c.moveToNext()){
            Book book1 = new Book();
            book1.setIdBook(c.getShort(c.getColumnIndex("idBook")));
            book1.setNameBook(c.getString(c.getColumnIndex("nameBook")));
            book1.setExplorer(new Explorer(c.getString(c.getColumnIndex("email"))));
            book.add(book1);
        }
        c.close();
        return book;
    }

}
