package com.example.jbbmobile.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.example.jbbmobile.model.Element;

import java.util.ArrayList;
import java.util.List;

public class ElementDAO extends SQLiteOpenHelper {
    private static final String NAME_DB="JBB";
    private static final int VERSION=1;

    public static String COLUMN_IDELEMENT ="idElement";
    public static String COLUMN_NAME="nameElement";
    public static String COLUMN_DEFAULTIMAGE="defaultImage";
    public static String COLUMN_ELEMENTSCORE="elementScore";
    public static String COLUMN_QRCODENUMBER="qrCodeNumber";
    public static String COLUMN_TEXTDESCRIPTION="textDescription";
    public static String COLUMN_USERIMAGE="userImage";

    public static String TABLE = "ELEMENT";

    public ElementDAO(Context context) {
        super(context, NAME_DB, null, VERSION);
    }

    public void createTableElement(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE + " (" +
                COLUMN_IDELEMENT +" INTEGER NOT NULL, " +
                COLUMN_NAME+ " VARCHAR(45) NOT NULL, " +
                COLUMN_DEFAULTIMAGE + " VARCHAR(200) NOT NULL, " +
                COLUMN_ELEMENTSCORE + " INTEGER NOT NULL, " +
                COLUMN_QRCODENUMBER + " INTEGER NOT NULL, " +
                COLUMN_TEXTDESCRIPTION + "VARCHAR(1000) NOT NULL, " +
                COLUMN_USERIMAGE + "VARCHAR(200) NOT NULL, " +
                BookDAO.COLUMN_IDBOOK + "INTEGER NOT NULL, " +
                "CONSTRAINT " + TABLE + "_PK PRIMARY KEY (" + COLUMN_IDELEMENT + "), " +
                "CONSTRAINT "+ BookDAO.TABLE + "_" + TABLE + "_FK FOREIGN KEY (" + BookDAO.COLUMN_IDBOOK + ") REFERENCES BOOK(" + BookDAO.COLUMN_IDBOOK + "))");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE);
        createTableElement(sqLiteDatabase);
    }

    @NonNull
    private ContentValues getElementData(Element element) {
        ContentValues data = new ContentValues();
        data.put(COLUMN_IDELEMENT, element.getIdElement());
        data.put(COLUMN_NAME,element.getNameElement());
        data.put(COLUMN_DEFAULTIMAGE,element.getDefaultImage());
        data.put(COLUMN_ELEMENTSCORE, element.getElementScore());
        data.put(COLUMN_QRCODENUMBER, element.getQrCodeNumber());
        data.put(COLUMN_TEXTDESCRIPTION,element.getTextDescription());
        data.put(COLUMN_USERIMAGE,element.getUserImage());
        data.put(BookDAO.COLUMN_IDBOOK, element.getIdBook());
        return data;
    }

    public int insertElement(Element element) throws SQLiteConstraintException{
        SQLiteDatabase dataBase = getWritableDatabase();
        int insertReturn;
        ContentValues data = getElementData(element);

        insertReturn = (int) dataBase.insert(TABLE, null, data);

        return  insertReturn;
    }

    public Element findElement(int idElement){
        SQLiteDatabase dataBase = getWritableDatabase();
        Cursor cursor;

        cursor = dataBase.query(TABLE, new String[] {COLUMN_IDELEMENT, COLUMN_NAME,COLUMN_DEFAULTIMAGE,COLUMN_ELEMENTSCORE,COLUMN_QRCODENUMBER,COLUMN_TEXTDESCRIPTION,COLUMN_USERIMAGE, BookDAO.COLUMN_IDBOOK}, COLUMN_IDELEMENT + " = " + idElement ,null, null , null ,null);
        Element element = new Element();

        if(cursor.moveToFirst()){
            element.setIdElement(cursor.getShort(cursor.getColumnIndex(COLUMN_IDELEMENT)));
            element.setNameElement(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            element.setDefaultImage(cursor.getString(cursor.getColumnIndex(COLUMN_DEFAULTIMAGE)));
            element.setElementScore(cursor.getShort(cursor.getColumnIndex(COLUMN_ELEMENTSCORE)));
            element.setQrCodeNumber(cursor.getShort(cursor.getColumnIndex(COLUMN_QRCODENUMBER)));
            element.setTextDescription(cursor.getString(cursor.getColumnIndex(COLUMN_TEXTDESCRIPTION)));
            element.setUserImage(cursor.getString(cursor.getColumnIndex(COLUMN_USERIMAGE)));
            element.setIdBook(cursor.getShort(cursor.getColumnIndex(BookDAO.COLUMN_IDBOOK)));
        }

        cursor.close();
        return element;
    }

    public List<Element> findElementsBook(int idBook){
        SQLiteDatabase dataBase = getWritableDatabase();
        Cursor cursor;

        cursor = dataBase.query(TABLE, new String[] {COLUMN_IDELEMENT, COLUMN_NAME,COLUMN_DEFAULTIMAGE,COLUMN_ELEMENTSCORE,COLUMN_QRCODENUMBER,COLUMN_TEXTDESCRIPTION,COLUMN_USERIMAGE, BookDAO.COLUMN_IDBOOK}, BookDAO.COLUMN_IDBOOK + " = " + idBook ,null, null , null ,null);

        List<Element> elements = new ArrayList<>();

        while(cursor.moveToNext()){
            Element element = new Element();

            element.setIdElement(cursor.getShort(cursor.getColumnIndex(COLUMN_IDELEMENT)));
            element.setNameElement(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            element.setDefaultImage(cursor.getString(cursor.getColumnIndex(COLUMN_DEFAULTIMAGE)));
            element.setElementScore(cursor.getShort(cursor.getColumnIndex(COLUMN_ELEMENTSCORE)));
            element.setQrCodeNumber(cursor.getShort(cursor.getColumnIndex(COLUMN_QRCODENUMBER)));
            element.setTextDescription(cursor.getString(cursor.getColumnIndex(COLUMN_TEXTDESCRIPTION)));
            element.setUserImage(cursor.getString(cursor.getColumnIndex(COLUMN_USERIMAGE)));
            element.setIdBook(cursor.getShort(cursor.getColumnIndex(BookDAO.COLUMN_IDBOOK)));

            elements.add(element);
        }

        cursor.close();

        return elements;
    }

    public void updateElement(Element element) {
        SQLiteDatabase dataBase = getWritableDatabase();
        ContentValues data = getElementData(element);
        String[] parameters = {String.valueOf(element.getIdElement())};

        dataBase.update(TABLE, data, COLUMN_IDELEMENT + " = ?", parameters);
    }

    public void deleteElement(Element element){
        SQLiteDatabase dataBase = getWritableDatabase();
        String[] parameters = {String.valueOf(element.getIdElement())};

        dataBase.delete(TABLE, COLUMN_IDELEMENT + " = ?", parameters);
    }

}