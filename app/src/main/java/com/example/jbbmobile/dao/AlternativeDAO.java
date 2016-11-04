package com.example.jbbmobile.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jbbmobile.model.Alternative;
import com.example.jbbmobile.model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AlternativeDAO extends SQLiteOpenHelper{
    private static final String NAME_DB="JBB";
    private static final int VERSION=1;

    protected static final String COLUMN_ID_ALTERNATIVE = "idAlternative";
    protected static final String COLUMN_ID_QUESTION = "idQuestion";
    protected static final String COLUMN_ALTERNATIVE_LETTER = "alternativeLetter";
    protected static final String COLUMN_ALTERNATIVE_DESCRIPTION = "alternativeDescription";
    protected static final String TABLE = "ALTERNATIVE";

    public AlternativeDAO(Context context) {
        super(context,NAME_DB, null, VERSION);
    }

    public static void createTableAlternative(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE + " (" +
                COLUMN_ID_ALTERNATIVE + " INTEGER NOT NULL, " +
                COLUMN_ID_QUESTION + " INTEGER, " +
                COLUMN_ALTERNATIVE_DESCRIPTION + " VARCHAR(500) NOT NULL, " +
                COLUMN_ALTERNATIVE_LETTER + " VARCHAR(1) NOT NULL, " +
                "CONSTRAINT " + TABLE + "_PK PRIMARY KEY (" + COLUMN_ID_ALTERNATIVE + "), " +
                "CONSTRAINT " + TABLE + "_" + QuestionDAO.TABLE + "_FK FOREIGN KEY (" + COLUMN_ID_QUESTION +
                        ") REFERENCES " + QuestionDAO.TABLE + " (" + QuestionDAO.COLUMN_ID_QUESTION + "))");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        createTableAlternative(db);
    }

    private ContentValues getAlternativeData(Alternative alternative){
        ContentValues data = new ContentValues();
        data.put(COLUMN_ID_ALTERNATIVE, alternative.getIdAlternative());
        data.put(COLUMN_ID_QUESTION, alternative.getIdQuestion());
        data.put(COLUMN_ALTERNATIVE_DESCRIPTION, alternative.getAlternativeDescription());
        data.put(COLUMN_ALTERNATIVE_LETTER, alternative.getAlternativeLetter());

        return data;
    }

    public int insertAlternative(Alternative alternative){
        SQLiteDatabase dataBase = getWritableDatabase();
        int insertReturn;
        ContentValues data = getAlternativeData(alternative);

        insertReturn = (int) dataBase.insert(TABLE, null, data);

        return insertReturn;
    }

    public Alternative findAlternative(int idAlternative){
        SQLiteDatabase dataBase = getWritableDatabase();
        Cursor cursor;

        cursor = dataBase.query(TABLE, new String[] {COLUMN_ID_QUESTION, COLUMN_ID_ALTERNATIVE, COLUMN_ALTERNATIVE_LETTER,
                COLUMN_ALTERNATIVE_DESCRIPTION},
                COLUMN_ID_ALTERNATIVE + " = " + idAlternative ,null, null , null ,null);

        Alternative alternative;
        if(cursor.moveToNext()){
            int idQuestion;
            String alternativeLetter;
            String alternativeDescription;

            idQuestion = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_QUESTION));
            alternativeLetter = cursor.getString(cursor.getColumnIndex(COLUMN_ALTERNATIVE_LETTER));
            alternativeDescription = cursor.getString(cursor.getColumnIndex(COLUMN_ALTERNATIVE_DESCRIPTION));
            alternative = new Alternative(idAlternative,alternativeLetter, alternativeDescription, idQuestion);
        }else{
            throw new SQLException();
        }
        cursor.close();
        return alternative;
    }

    public List<Alternative> findQuestionAlternatives(int idQuestion){
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor;

        cursor = database.query(TABLE, new String[] {COLUMN_ID_QUESTION, COLUMN_ID_ALTERNATIVE, COLUMN_ALTERNATIVE_LETTER,
                        COLUMN_ALTERNATIVE_DESCRIPTION},
                COLUMN_ID_QUESTION + " = " + idQuestion ,null, null , null ,null);

        List<Alternative> alternatives = new ArrayList<>();

        while(cursor.moveToNext()){
            Alternative alternative;
            int idAlternative;
            String alternativeLetter;
            String alternativeDescription;

            idAlternative = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_ALTERNATIVE));
            alternativeLetter = cursor.getString(cursor.getColumnIndex(COLUMN_ALTERNATIVE_LETTER));
            alternativeDescription = cursor.getString(cursor.getColumnIndex(COLUMN_ALTERNATIVE_DESCRIPTION));
            alternative = new Alternative(idAlternative,alternativeLetter, alternativeDescription, idQuestion);

            alternatives.add(alternative);
        }
        cursor.close();
        return alternatives;
    }

    public int deleteAlternative(Alternative alternative){
        SQLiteDatabase dataBase = getWritableDatabase();
        String[] parameters = {String.valueOf(alternative.getIdAlternative())};
        int deleteReturn;
        deleteReturn = dataBase.delete(TABLE, COLUMN_ID_ALTERNATIVE + " = ?", parameters);

        return deleteReturn;
    }
}
