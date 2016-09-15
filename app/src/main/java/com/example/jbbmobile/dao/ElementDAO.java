package com.example.jbbmobile.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.example.jbbmobile.model.Element;
import com.example.jbbmobile.model.Explorers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ronyell on 14/09/16.
 */

public class ElementDAO extends SQLiteOpenHelper {
    private static final String NAME_DB="JBB";
    private static final int VERSION=1;

    public ElementDAO(Context context) {
        super(context,NAME_DB, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE ELEMENT (idElement integer primary key, " +
                "qrCodeNumber integer not null, " +
                "elementScore integer not null, " +
                "defaultImage varchar(200) not null, " +
                "nameElement varchar(50) not null, " +
                "userImage varchar(200)," +
                "idBook integer," +
                "FOREIGN KEY (idBook) REFERENCES ELEMENT(idBook) )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE ELEMENT");
        onCreate(sqLiteDatabase);
    }

    @NonNull
    private ContentValues getElementData(Element element) {
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

    public int insertElement(Element element) throws SQLiteConstraintException{
        SQLiteDatabase db = getWritableDatabase();
        int insertReturn = 0;
        ContentValues data = getElementData(element);

        insertReturn = (int) db.insert("ELEMENT", null, data);

        return  insertReturn;
    }

    public Element findElement(Element element){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c;
        c= db.query("Element",new String[] { "idElement" ,"qrCodeNumber", "elementScore", "defaultImage","nameElement","userImage","idBook" }, "idElement = " + element.getIdElement() ,null, null , null ,null);

        Element element1 = new Element();
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

    public List<Element> findElementsBook(Element element){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c;
        c= db.query("Element",new String[] { "idElement" ,"qrCodeNumber", "elementScore", "defaultImage","nameElement","userImage","idBook" }, "idBook = " + element.getIdBook() ,null, null , null ,null);

        List<Element> elements = new ArrayList<Element>();

        while(c.moveToFirst()){

                Element element1 = new Element();

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

    public void updateElement(Element element) throws SQLiteConstraintException{
        SQLiteDatabase db = getWritableDatabase();

        ContentValues data = getElementData(element);

        String[] params = {String.valueOf(element.getIdElement())};

        db.update("ELEMENT", data, "idElement = ?", params);
    }

    public void deleteElement(Element element){
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {String.valueOf(element.getIdElement())};
        db.delete("ELEMENT", "idElement = ?", params);

    }

    public List<Element> findElements() {
        String sql = "SELECT * FROM ELEMENT;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Element> elements = new ArrayList<Element>();
        while (c.moveToNext()) {
            Element element = new Element();

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
