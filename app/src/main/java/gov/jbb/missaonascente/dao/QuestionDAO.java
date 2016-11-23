package gov.jbb.missaonascente.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import gov.jbb.missaonascente.model.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionDAO extends SQLiteOpenHelper {
    private static final String NAME_DB="JBB";
    private static final int VERSION=1;

    protected static String COLUMN_ID_QUESTION = "idQuestion";
    protected static String COLUMN_DESCRIPTION = "description";
    protected static String COLUMN_CORRECT_ANSWER = "correctAnswer";
    protected static String COLUMN_ALTERNATIVE_QUANTITY = "alternativeQuantity";
    protected static String COLUMN_VERSION = "version";
    protected static String TABLE = "QUESTION";

    public QuestionDAO(Context context) {
        super(context,NAME_DB, null, VERSION);
    }

    public static void createTableQuestion(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("CREATE TABLE  IF NOT EXISTS " + TABLE + " (" +
                COLUMN_ID_QUESTION + " INTEGER NOT NULL, " +
                COLUMN_DESCRIPTION + " VARCHAR(2000) NOT NULL, " +
                COLUMN_CORRECT_ANSWER + " VARCHAR(1) NOT NULL, " +
                COLUMN_ALTERNATIVE_QUANTITY + " INTEGER NOT NULL, " +
                COLUMN_VERSION + " INTEGER NOT NULL, " +
                "CONSTRAINT " + TABLE + "_PK PRIMARY KEY (" + COLUMN_ID_QUESTION + "))");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE);
        createTableQuestion(sqLiteDatabase);
    }

    @NonNull
    private ContentValues getQuestionData(Question question) {
        ContentValues data = new ContentValues();
        data.put(COLUMN_ID_QUESTION, question.getIdQuestion());
        data.put(COLUMN_CORRECT_ANSWER, question.getCorrectAnswer());
        data.put(COLUMN_DESCRIPTION, question.getDescription());
        data.put(COLUMN_ALTERNATIVE_QUANTITY, question.getAlternativeQuantity());
        data.put(COLUMN_VERSION, question.getVersion());
        return data;
    }

    public int insertQuestion(Question question){
        SQLiteDatabase dataBase = getWritableDatabase();
        int insertReturn;
        ContentValues data = getQuestionData(question);

        insertReturn = (int) dataBase.insert(TABLE, null, data);

        return  insertReturn;
    }

    public Question findQuestion(int idQuestion){
        SQLiteDatabase dataBase = getWritableDatabase();
        Cursor cursor;

        cursor = dataBase.query(TABLE, new String[] {COLUMN_ID_QUESTION, COLUMN_DESCRIPTION, COLUMN_CORRECT_ANSWER,
        COLUMN_ALTERNATIVE_QUANTITY, COLUMN_VERSION}, COLUMN_ID_QUESTION + " = " + idQuestion ,null, null , null ,null);
        Question question = new Question();

        if(cursor.moveToFirst()){
            question.setIdQuestion(cursor.getInt(cursor.getColumnIndex(COLUMN_ID_QUESTION)));
            question.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
            question.setCorrectAnswer(cursor.getString(cursor.getColumnIndex(COLUMN_CORRECT_ANSWER)));
            question.setAlternativeQuantity(cursor.getInt(cursor.getColumnIndex(COLUMN_ALTERNATIVE_QUANTITY)));
            question.setVersion(cursor.getInt(cursor.getColumnIndex(COLUMN_VERSION)));
        }else{
            throw new SQLException();
        }

        cursor.close();
        return question;
    }

    public List<Question> findAllQuestion(){
        SQLiteDatabase dataBase = getWritableDatabase();
        Cursor cursor;

        cursor = dataBase.query(TABLE, new String[] {COLUMN_ID_QUESTION, COLUMN_DESCRIPTION, COLUMN_CORRECT_ANSWER,
                COLUMN_ALTERNATIVE_QUANTITY, COLUMN_VERSION}, null ,null, null , null ,null);
        List<Question> questions = new ArrayList<>();

        while(cursor.moveToNext()){
            Question question = new Question();

            question.setIdQuestion(cursor.getInt(cursor.getColumnIndex(COLUMN_ID_QUESTION)));
            question.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
            question.setCorrectAnswer(cursor.getString(cursor.getColumnIndex(COLUMN_CORRECT_ANSWER)));
            question.setAlternativeQuantity(cursor.getInt(cursor.getColumnIndex(COLUMN_ALTERNATIVE_QUANTITY)));
            question.setVersion(cursor.getInt(cursor.getColumnIndex(COLUMN_VERSION)));
            questions.add(question);
        }

        if(cursor.getCount() == 0){
            throw new IllegalArgumentException("No questions");
        }
        cursor.close();

        return questions;
    }

    public int updateQuestion(Question question) {
        SQLiteDatabase dataBase = getWritableDatabase();
        ContentValues data = getQuestionData(question);
        String[] parameters = {String.valueOf(question.getIdQuestion())};

        int updateReturn;
        updateReturn = dataBase.update(TABLE, data, COLUMN_ID_QUESTION + " = ?", parameters);

        return updateReturn;
    }

    public int deleteQuestion(Question question){
        SQLiteDatabase dataBase = getWritableDatabase();
        String[] parameters = {String.valueOf(question.getIdQuestion())};

        int deleteReturn;
        deleteReturn = dataBase.delete(TABLE, COLUMN_ID_QUESTION + " = ?", parameters);

        return deleteReturn;
    }

    public int countAllQuestions(){
        SQLiteDatabase dataBase = getWritableDatabase();
        Cursor cursor;
        int numberOfQuestions;
        cursor = dataBase.query(TABLE, new String[] {COLUMN_ID_QUESTION}, null ,null, null , null , COLUMN_ID_QUESTION + " DESC");

        if(cursor.moveToFirst()){
            numberOfQuestions = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_QUESTION));
        }else{
            throw new SQLException();
        }
        cursor.close();

        return numberOfQuestions;
    }
}
