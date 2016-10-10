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

    private static String COLUMN_IDELEMENT = "idElement";
    private static String COLUMN_NAME = "nameElement";
    private static String COLUMN_DEFAULTIMAGE = "defaultImage";
    private static String COLUMN_ELEMENTSCORE = "elementScore";
    private static String COLUMN_QRCODENUMBER = "qrCodeNumber";
    private static String COLUMN_TEXTDESCRIPTION = "textDescription";
    private static String COLUMN_USERIMAGE = "userImage";
    private static String COLUMN_CATCHDATE = "catchDate";

    private static String TABLE = "ELEMENT";
    private static String RELATION = TABLE + "_" + ExplorerDAO.TABLE;

    public ElementDAO(Context context) {
        super(context, NAME_DB, null, VERSION);
    }

    public static void createTableElement(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE + " (" +
            COLUMN_IDELEMENT +" INTEGER NOT NULL, " +
            COLUMN_NAME+ " VARCHAR(45) NOT NULL, " +
            COLUMN_DEFAULTIMAGE + " VARCHAR(200) NOT NULL, " +
            COLUMN_ELEMENTSCORE + " INTEGER NOT NULL, " +
            COLUMN_QRCODENUMBER + " INTEGER NOT NULL, " +
            COLUMN_TEXTDESCRIPTION + " VARCHAR(1000) NOT NULL, " +
            COLUMN_USERIMAGE + " VARCHAR(200), " +
            BookDAO.COLUMN_IDBOOK + " INTEGER NOT NULL, " +
            "CONSTRAINT " + TABLE + "_PK PRIMARY KEY (" + COLUMN_IDELEMENT + "), " +
            "CONSTRAINT "+ BookDAO.TABLE + "_" + TABLE + "_FK FOREIGN KEY (" + BookDAO.COLUMN_IDBOOK + ") REFERENCES " + BookDAO.TABLE + "(" + BookDAO.COLUMN_IDBOOK + "))");
    }

    public static void createTableElementExplorer(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + RELATION + " (" +
            COLUMN_IDELEMENT +" INTEGER NOT NULL, " +
            ExplorerDAO.COLUMN_EMAIL + " VARCHAR(45) NOT NULL, " +
            COLUMN_CATCHDATE + " DATE(45) NOT NULL, " +
            "CONSTRAINT "+ TABLE + "_" + RELATION + "_FK FOREIGN KEY (" + COLUMN_IDELEMENT + ") REFERENCES " + TABLE + "(" + COLUMN_IDELEMENT + "), " +
            "CONSTRAINT "+ ExplorerDAO.TABLE + "_" + RELATION + "_FK FOREIGN KEY (" + ExplorerDAO.COLUMN_EMAIL + ") REFERENCES " + ExplorerDAO.TABLE + "(" + ExplorerDAO.COLUMN_EMAIL + "))");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RELATION);
        createTableElement(sqLiteDatabase);
        createTableElementExplorer(sqLiteDatabase);
    }

    // Element Table Methods

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

    public Element findElementFromElementTable(int idElement){
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

    public int updateElement(Element element) {
        SQLiteDatabase dataBase = getWritableDatabase();
        ContentValues data = getElementData(element);
        String[] parameters = {String.valueOf(element.getIdElement())};

        int updateReturn;
        updateReturn = dataBase.update(TABLE, data, COLUMN_IDELEMENT + " = ?", parameters);

        return updateReturn;
    }

    public int deleteElement(Element element){
        SQLiteDatabase dataBase = getWritableDatabase();
        String[] parameters = {String.valueOf(element.getIdElement())};

        int deleteReturn;
        deleteReturn = dataBase.delete(TABLE, COLUMN_IDELEMENT + " = ?", parameters);

        return deleteReturn;
    }

    // Relation Table Methods

    @NonNull
    private ContentValues getElementExplorerData(int idElement, String email, String date) {
        ContentValues data = new ContentValues();
        data.put(COLUMN_IDELEMENT, idElement);
        data.put(ExplorerDAO.COLUMN_EMAIL, email);
        data.put(COLUMN_CATCHDATE, date);
        return data;
    }

    public int insertElementExplorer(int idElement, String email, String date) throws SQLiteConstraintException{
        SQLiteDatabase dataBase = getWritableDatabase();
        int insertReturn;
        ContentValues data = getElementExplorerData(idElement, email, date);

        insertReturn = (int) dataBase.insert(RELATION, null, data);

        return  insertReturn;
    }

    public Element findElementFromRelationTable(int idElement, String email){
        SQLiteDatabase dataBase = getWritableDatabase();
        Cursor cursor;

        cursor = dataBase.query(RELATION,new String[]{COLUMN_CATCHDATE}, ExplorerDAO.COLUMN_EMAIL + " ='" + email + "' AND " +COLUMN_IDELEMENT + " = " + idElement ,null, null , null ,null );

        Element element = findElementFromElementTable(idElement);

        if(cursor.moveToFirst()){
            element.setCatchDate(cursor.getString(cursor.getColumnIndex(COLUMN_CATCHDATE)));
        }
        cursor.close();

        return element;
    }

    public List<Element> findElementsExplorerBook(int idBook, String email) {
        SQLiteDatabase dataBase = getWritableDatabase();
        Cursor cursor;

        cursor = dataBase.query(RELATION,new String[]{COLUMN_IDELEMENT,COLUMN_CATCHDATE}, ExplorerDAO.COLUMN_EMAIL + " ='" + email + "'" ,null, null , null ,null );

        List<Element> elements = new ArrayList<>();

        while(cursor.moveToNext()){
            Element element = findElementFromElementTable(cursor.getShort(cursor.getColumnIndex(COLUMN_IDELEMENT)));
            if(element.getIdBook()==idBook){
                element.setCatchDate(cursor.getString(cursor.getColumnIndex(COLUMN_CATCHDATE)));
                elements.add(element);
            }
        }
        cursor.close();

        return elements;
    }

    public int updateElementExplorer(int idElement, String email, String date) {
        SQLiteDatabase dataBase = getWritableDatabase();
        ContentValues data = getElementExplorerData(idElement, email, date);
        String[] parameters = {String.valueOf(idElement),email};

        int updateReturn;
        updateReturn = dataBase.update(RELATION, data, COLUMN_IDELEMENT + " = ? AND " + ExplorerDAO.COLUMN_EMAIL + " = ?", parameters);

        return updateReturn;
    }

    public int deleteElementExplorer(int idElement, String email){
        SQLiteDatabase dataBase = getWritableDatabase();
        String[] parameters = {String.valueOf(idElement),email};

        int deleteReturn;
        deleteReturn = dataBase.delete(RELATION, COLUMN_IDELEMENT + " = ? AND " + ExplorerDAO.COLUMN_EMAIL + " = ?", parameters);

        return deleteReturn;
    }
}