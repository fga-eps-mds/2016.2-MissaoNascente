package com.example.jbbmobile.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;


import com.example.jbbmobile.model.Elements;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by ronyell on 14/09/16.
 */

public class ElementDAO extends SQLiteOpenHelper {
    private static final String NAME_DB="JBB";
    private static final int VERSION=1;

    public ElementDAO(Context context) {
        super(context, NAME_DB, null, VERSION);
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL("CREATE TABLE ELEMENT (idElement integer primary key, " +
                    "qrCodeNumber integer, " +
                    "elementScore integer, " +
                    "defaultImage varchar(200), " +
                    "nameElement varchar(50), " +
                    "userImage varchar(200)," +
                    "idBook integer," +
                    "FOREIGN KEY (idBook) REFERENCES ELEMENT(idBook) )");

        }catch(SQLException e){

        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE ELEMENT");
        onCreate(sqLiteDatabase);
    }


    @NonNull
    private ContentValues getElementData(Elements element) {
        ContentValues data = new ContentValues();
        data.put("idElement", element.getIdElement());
        data.put("qrCodeNumber", element.getQrCodeNumber());
        data.put("elementScore", element.getElementScore());
        data.put("defaultImage",element.getDefaultImage());
        data.put("nameElement",element.getNameElement());
        data.put("userImage",element.getUserImage());
        data.put("idBook",element.getIdBook());


        return data;
    }

    public int insertElement(Elements element) throws SQLiteConstraintException{
        SQLiteDatabase db = getWritableDatabase();
        int insertReturn = 0;
        ContentValues data = getElementData(element);
        insertReturn = (int) db.insert("ELEMENT", null, data);

        return  insertReturn;
    }

    public Elements findElement(Elements element){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c;
        c= db.query("ELEMENT",new String[] { "idElement" ,"qrCodeNumber", "elementScore", "defaultImage","nameElement","userImage","idBook" }, "idElement = " + element.getIdElement() ,null, null , null ,null);

        Elements element1 = new Elements();
        if(c.moveToFirst()){
            element1.setIdElement(c.getShort(c.getColumnIndex("idElement")));
            element1.setQrCodeNumber(c.getShort(c.getColumnIndex("qrCodeNumber")));
            element1.setElementScore(c.getShort(c.getColumnIndex("elementScore")));
            element1.setNameElement(c.getString(c.getColumnIndex("nameElement")));
            element1.setDefaultImage(c.getString(c.getColumnIndex("defaultImage")));
            element1.setUserImage(c.getString(c.getColumnIndex("userImage")));
            element1.setIdBook(c.getShort(c.getColumnIndex("idBook")));

        }
        c.close();
        return element1;
    }

    public Elements findElementByBook(Elements element){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c;
        c= db.query("ELEMENT",new String[] { "idElement" ,"qrCodeNumber", "elementScore", "defaultImage","nameElement","userImage","idBook" }, "idElement = " +
                element.getIdElement() + " AND idBook = " +element.getIdBook(), null, null, null, null);

        Elements element1 = new Elements();
        if(c.moveToFirst()){
            element1.setIdElement(c.getShort(c.getColumnIndex("idElement")));
            element1.setQrCodeNumber(c.getShort(c.getColumnIndex("qrCodeNumber")));
            element1.setElementScore(c.getShort(c.getColumnIndex("elementScore")));
            element1.setNameElement(c.getString(c.getColumnIndex("nameElement")));
            element1.setDefaultImage(c.getString(c.getColumnIndex("defaultImage")));
            element1.setUserImage(c.getString(c.getColumnIndex("userImage")));
            element1.setIdBook(c.getShort(c.getColumnIndex("idBook")));

        }
        c.close();
        return element1;
    }

    public List<Elements> findElementsBook(Elements element){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c;
        c= db.query("ELEMENT",new String[] { "idElement" ,"qrCodeNumber", "elementScore", "defaultImage","nameElement","userImage","idBook" }, "idBook = " + element.getIdBook() ,null, null , null ,null);

        List<Elements> elements = new ArrayList<Elements>();

        while(c.moveToFirst()){

            Elements element1 = new Elements();

            element1.setIdElement(c.getShort(c.getColumnIndex("idElement")));
            element1.setQrCodeNumber(c.getShort(c.getColumnIndex("qrCodeNumber")));
            element1.setElementScore(c.getShort(c.getColumnIndex("elementScore")));
            element1.setNameElement(c.getString(c.getColumnIndex("nameElement")));
            element1.setDefaultImage(c.getString(c.getColumnIndex("defaultImage")));
            element1.setUserImage(c.getString(c.getColumnIndex("userImage")));
            element1.setIdBook(c.getShort(c.getColumnIndex("idBook")));

            elements.add(element1);

        }
        c.close();
        return elements;
    }

    public void updateElement(Elements element) throws SQLiteConstraintException{
        SQLiteDatabase db = getWritableDatabase();

        ContentValues data = getElementData(element);

        String[] params = {String.valueOf(element.getIdElement())};

        db.update("ELEMENT", data, "idElement = ?", params);
    }

    public void deleteElement(Elements element){
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {String.valueOf(element.getIdElement())};
        db.delete("ELEMENT", "idElement = ?", params);

    }

    public List<Elements> findElements() {
        String sql = "SELECT * FROM ELEMENT;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Elements> elements = new ArrayList<Elements>();
        while (c.moveToNext()) {
            Elements element = new Elements();

            element.setIdElement(c.getShort(c.getColumnIndex("idElement")));
            element.setQrCodeNumber(c.getShort(c.getColumnIndex("qrCodeNumber")));
            element.setElementScore(c.getShort(c.getColumnIndex("elementScore")));
            element.setNameElement(c.getString(c.getColumnIndex("nameElement")));
            element.setDefaultImage(c.getString(c.getColumnIndex("defaultImage")));
            element.setUserImage(c.getString(c.getColumnIndex("userImage")));
            element.setIdBook(c.getShort(c.getColumnIndex("idBook")));

            elements.add(element);
        }
        c.close();

        return elements;
    }
}