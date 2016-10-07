package com.example.jbbmobile.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.jbbmobile.model.Element;

import java.util.ArrayList;
import java.util.List;

public class ElementDAO extends SQLiteOpenHelper {
    private static final String NAME_DB="JBB";
    private static final int VERSION=1;

    public ElementDAO(Context context) {
        super(context, NAME_DB, null, VERSION);
    }

    public void createTablesIfTheyDoesntExist(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS INFORMATION(idInformation integer primary key," +
                "qrCodeNumber integer not null," +
                "elementScore integer not null," +
                "defaultImage varchar(200) not null," +
                "nameElement varchar(45) not null)");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS ELEMENT (idElement integer primary key, " +
                "userImage varchar(200)," +
                "idInformation integer not null, " +
                "idBook integer," +
                "FOREIGN KEY (idBook) REFERENCES BOOK(idBook)," +
                "FOREIGN KEY (idInformation) REFERENCES INFORMATION(idInformation) )");


        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS textDescription(idInformation integer," +
                "description varchar(400) not null," +
                "FOREIGN KEY (idInformation) REFERENCES INFORMATION(idInformation) )");
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE ELEMENT");
        sqLiteDatabase.execSQL("DROP TABLE textDescription");
        sqLiteDatabase.execSQL("DROP TABLE INFORMATION");
        onCreate(sqLiteDatabase);
    }


    @NonNull
    private ContentValues getElementData(Element element) {
        ContentValues data = new ContentValues();
        data.put("idElement", element.getIdElement());
        data.put("userImage",element.getUserImage());
        data.put("idBook",element.getIdBook());
        data.put("idInformation", element.getIdInformation());
        return data;
    }

    @NonNull
    private ContentValues getInformationData(Element element) {
        ContentValues data = new ContentValues();
        data.put("idInformation", element.getIdInformation());
        data.put("qrCodeNumber", element.getQrCodeNumber());
        data.put("elementScore", element.getElementScore());
        data.put("defaultImage",element.getDefaultImage());
        data.put("nameElement",element.getNameElement());
        return data;
    }

    @NonNull
    private ContentValues getDescriptionData(Element element) {
        ContentValues data = new ContentValues();
        data.put("description", element.getDescription().get(0));
        data.put("idInformation", element.getIdInformation());
        return data;
    }

    public int insertElement(Element element) throws SQLiteConstraintException{
        SQLiteDatabase db = getWritableDatabase();
        int insertReturn = 0;
        ContentValues data = getElementData(element);
        insertReturn = (int) db.insert("ELEMENT", null, data);
        return  insertReturn;
    }

    public int insertInformation(Element element) throws SQLiteConstraintException{
        SQLiteDatabase db = getWritableDatabase();
        int insertReturn = 0;
        ContentValues data = getInformationData(element);
        insertReturn = (int) db.insert("INFORMATION", null, data);
        return  insertReturn;
    }

    public int insertDescription(Element element) throws SQLiteConstraintException{
        SQLiteDatabase db = getWritableDatabase();
        int insertReturn = 0;
        ContentValues data;

        while(!element.getDescription().isEmpty()){
            data = getDescriptionData(element);
            insertReturn = (int) db.insert("textDescription", null, data);
            if(insertReturn==-1){
                return insertReturn;
            }
            element.getDescription().remove(0);
        }
        return  insertReturn;
    }

    public Element findElement(Element element){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c;
        c= db.query("ELEMENT",new String[] { "idElement","userImage","idBook","idInformation" },
                "idElement = " + element.getIdElement() ,null, null , null ,null);

        Element element1 = new Element();
        if(c.moveToFirst()){
            element1.setIdElement(c.getShort(c.getColumnIndex("idElement")));
            element1.setUserImage(c.getString(c.getColumnIndex("userImage")));
            element1.setIdBook(c.getShort(c.getColumnIndex("idBook")));
            element1.setIdInformation(c.getShort(c.getColumnIndex("idInformation")));
        }
        c.close();
        c= db.query("INFORMATION",new String[] { "idInformation","qrCodeNumber","elementScore","defaultImage","nameElement" },
                "idInformation = " + element1.getIdInformation() ,null, null , null ,null);

        if(c.moveToFirst()){
            element1.setIdInformation(c.getShort(c.getColumnIndex("idInformation")));
            element1.setQrCodeNumber(c.getShort(c.getColumnIndex("qrCodeNumber")));
            element1.setElementScore(c.getShort(c.getColumnIndex("elementScore")));
            element1.setNameElement(c.getString(c.getColumnIndex("nameElement")));
            element1.setDefaultImage(c.getString(c.getColumnIndex("defaultImage")));
        }
        c= db.query("textDescription",new String[] { "idInformation","description" }, "idInformation = " + element1.getIdInformation() ,null, null , null ,null);

        element1.setDescription(new ArrayList<String>());

        while (c.moveToNext()) {
            element1.getDescription().add(c.getString(c.getColumnIndex("description")));
        }

        return element1;
    }

    public Element findElementByBook(Element element){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c;
        c= db.query("ELEMENT",new String[] { "idElement","userImage","idBook", "idInformation" }, "idElement = " +
                element.getIdElement() + " AND idBook = " +element.getIdBook(), null, null, null, null);

        Element element1 = new Element();
        if(c.moveToFirst()){
            element1.setIdElement(c.getInt(c.getColumnIndex("idElement")));
            element1.setUserImage(c.getString(c.getColumnIndex("userImage")));
            element1.setIdBook(c.getInt(c.getColumnIndex("idBook")));
            element1.setIdInformation(c.getInt(c.getColumnIndex("idInformation")));
        }
        c.close();

        c= db.query("INFORMATION",new String[] { "idInformation","qrCodeNumber","elementScore","defaultImage","nameElement" }, "idInformation = " + element1.getIdInformation() ,null, null , null ,null);

        if(c.moveToFirst()){
            element1.setIdInformation(c.getShort(c.getColumnIndex("idInformation")));
            element1.setQrCodeNumber(c.getShort(c.getColumnIndex("qrCodeNumber")));
            element1.setElementScore(c.getShort(c.getColumnIndex("elementScore")));
            element1.setNameElement(c.getString(c.getColumnIndex("nameElement")));
            element1.setDefaultImage(c.getString(c.getColumnIndex("defaultImage")));
        }

        c= db.query("textDescription",new String[] { "idInformation","description" }, "idInformation = " + element1.getIdInformation() ,null, null , null ,null);

        element1.setDescription(new ArrayList<String>());

        findDescription(element);

        c.close();

        return element1;
    }

    public Element findElementByQrCode (int code) throws Exception{
        Log.i("abc", "Entrando findElement");
        SQLiteDatabase db = getWritableDatabase();
        Cursor c;
        Log.i("Qualquer coisa",  "qrCodeNumber = " + code );
        c= db.query("INFORMATION",new String[] { "idInformation","qrCodeNumber","elementScore","defaultImage","nameElement" },
                "qrCodeNumber = " + code ,null, null , null ,null);

        Element element = new Element();
        if(c.moveToFirst()){
            element.setIdInformation(c.getShort(c.getColumnIndex("idInformation")));
            element.setQrCodeNumber(c.getShort(c.getColumnIndex("qrCodeNumber")));
            element.setElementScore(c.getShort(c.getColumnIndex("elementScore")));
            element.setNameElement(c.getString(c.getColumnIndex("nameElement")));
            element.setDefaultImage(c.getString(c.getColumnIndex("defaultImage")));
        }
        else {
            Log.i("Qualquer coisa", "Errou: ");
            throw new Exception("Invalid qr code");
        }

        c.close();

        c= db.query("ELEMENT",new String[] { "idElement","userImage","idBook","idInformation" },
                "idInformation = " + element.getIdInformation() ,null, null , null ,null);

        if(c.moveToFirst()){
            element.setIdElement(c.getShort(c.getColumnIndex("idElement")));
            element.setUserImage(c.getString(c.getColumnIndex("userImage")));
            element.setIdBook(c.getShort(c.getColumnIndex("idBook")));
            element.setIdInformation(c.getShort(c.getColumnIndex("idInformation")));
        }
        c.close();

        c= db.query("textDescription",new String[] { "idInformation","description" }, "idInformation = " + element.getIdInformation() ,null, null , null ,null);

        element.setDescription(new ArrayList<String>());

        while (c.moveToNext()) {
            element.getDescription().add(c.getString(c.getColumnIndex("description")));
        }

        return element;
    }

    public List<Element> findElementsBook(int idBook){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c;

        c= db.query("ELEMENT",new String[] { "idElement" ,"userImage","idBook" ,"idInformation"}, "idBook = " + idBook ,null, null , null ,null);

        List<Element> elements = new ArrayList<Element>();

        while(c.moveToNext()){
            Element element1 = new Element();

            element1.setIdElement(c.getShort(c.getColumnIndex("idElement")));
            element1.setUserImage(c.getString(c.getColumnIndex("userImage")));
            element1.setIdBook(c.getShort(c.getColumnIndex("idBook")));
            element1.setIdInformation(c.getShort(c.getColumnIndex("idInformation")));


            Cursor cursor;
            cursor = db.query("INFORMATION",new String[] { "idInformation","qrCodeNumber","elementScore","defaultImage","nameElement" }, "idInformation = " + element1.getIdInformation() ,null, null , null ,null);

            if(cursor.moveToFirst()){

                element1.setIdInformation(cursor.getShort(cursor.getColumnIndex("idInformation")));
                element1.setQrCodeNumber(cursor.getShort(cursor.getColumnIndex("qrCodeNumber")));
                element1.setElementScore(cursor.getShort(cursor.getColumnIndex("elementScore")));
                element1.setNameElement(cursor.getString(cursor.getColumnIndex("nameElement")));
                element1.setDefaultImage(cursor.getString(cursor.getColumnIndex("defaultImage")));
            }

            cursor= db.query("textDescription",new String[] { "idInformation","description" }, "idInformation = " + element1.getIdInformation() ,null, null , null ,null);

            element1.setDescription(new ArrayList<String>());

            while (cursor.moveToNext()) {
                element1.getDescription().add(cursor.getString(cursor.getColumnIndex("description")));
            }
            cursor.close();
            elements.add(element1);
        }
        c.close();
        return elements;
    }

    public void updateElement(Element element) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues data = getElementData(element);

        String[] params = {String.valueOf(element.getIdElement())};

        db.update("ELEMENT", data, "idElement = ?", params);
    }

    public void updateInformations(Element element) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues data = getInformationData(element);

        String[] params = {String.valueOf(element.getIdInformation())};

        db.update("INFORMATION", data, "idInformation = ?", params);
    }

    public void updateDescription(Element element) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues data = getDescriptionData(element);

        String[] params = {String.valueOf(element.getIdInformation())};

        db.update("textDescription", data, "idInformation = ?", params);
    }

    public void deleteElement(Element element){
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {String.valueOf(element.getIdElement())};
        db.delete("ELEMENT", "idElement = ?", params);

    }


    public List<Element> findInformation() {
        String sql = "SELECT * FROM INFORMATION;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        List<Element> elements = new ArrayList<Element>();
        while (cursor.moveToNext()) {
            Element element1 = new Element();

            element1.setIdInformation(cursor.getShort(cursor.getColumnIndex("idInformation")));
            element1.setQrCodeNumber(cursor.getShort(cursor.getColumnIndex("qrCodeNumber")));
            element1.setElementScore(cursor.getShort(cursor.getColumnIndex("elementScore")));
            element1.setNameElement(cursor.getString(cursor.getColumnIndex("nameElement")));
            element1.setDefaultImage(cursor.getString(cursor.getColumnIndex("defaultImage")));

            elements.add(element1);
        }
        cursor.close();

        return elements;
    }

    public List<String> findAllDescription() {
        String sql = "SELECT * FROM textDescription;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<String> description = new ArrayList<String>();

        while (c.moveToNext()) {

            description.add(c.getString(c.getColumnIndex("description")));

        }
        c.close();

        return description;
    }

    public List<String>  findDescription(Element element){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c;
        c= db.query("textDescription",new String[] { "description", "idInformation" }, "idInformation = " + element.getIdInformation() ,null, null , null ,null);
        List<String> descriptions= new ArrayList<String>();
        while (c.moveToNext()) {
            descriptions.add(c.getString(c.getColumnIndex("description")));
        }
        return descriptions;
    }
}