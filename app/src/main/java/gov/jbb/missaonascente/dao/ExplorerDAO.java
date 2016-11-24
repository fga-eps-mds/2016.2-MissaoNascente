package gov.jbb.missaonascente.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import gov.jbb.missaonascente.model.Explorer;

public class ExplorerDAO extends SQLiteOpenHelper{
    private static final String NAME_DB="JBB";
    private static final int VERSION=1;
    private Context context;

    protected static String COLUMN_NICKNAME ="nickname";
    protected static String COLUMN_EMAIL ="email";
    protected static String COLUMN_PASSWORD ="password";
    protected static String COLUMN_SCORE="score";
    protected static String COLUMN_ENERGY ="energy";
    protected static String COLUMN_QUESTIONS_ANSWERED = "questionsAnswered";
    protected static String COLUMN_CORRECT_QUESTIONS = "correctQuestions";


    protected static String TABLE ="EXPLORER";

    public ExplorerDAO(Context context) {
        super(context,NAME_DB, null, VERSION);
        this.context = context;
    }

    public static void createTableExplorer(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE + " (" +
            COLUMN_NICKNAME + " VARCHAR(12) UNIQUE NOT NULL, " +
            COLUMN_EMAIL + " VARCHAR(45) NOT NULL, " +
            COLUMN_PASSWORD + " VARCHAR(64) NOT NULL, " + //The password length was altered from 12 to 64, because of the encryption.
            COLUMN_SCORE +" INTEGER NOT NULL, " +
            COLUMN_QUESTIONS_ANSWERED + " INTEGER NOT NULL DEFAULT 0, " +
            COLUMN_CORRECT_QUESTIONS + " INTEGER NOT NULL DEFAULT 0, " +
            COLUMN_ENERGY + " INTEGER DEFAULT 100, " +
            "CONSTRAINT " + TABLE + "_PK PRIMARY KEY (" + COLUMN_EMAIL + "))");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE);
        createTableExplorer(sqLiteDatabase);
    }

    @NonNull
    private ContentValues getExplorerData(Explorer explorer) {
        ContentValues data = new ContentValues();
        data.put(COLUMN_NICKNAME, explorer.getNickname());
        data.put(COLUMN_EMAIL, explorer.getEmail());
        data.put(COLUMN_PASSWORD, explorer.getPassword());
        data.put(COLUMN_SCORE,explorer.getScore());
        data.put(COLUMN_CORRECT_QUESTIONS,explorer.getCorrectQuestion());
        data.put(COLUMN_QUESTIONS_ANSWERED,explorer.getQuestionAnswered());
        return data;
    }

    public int insertExplorer(Explorer explorer) throws SQLiteConstraintException {
        SQLiteDatabase dataBase = getWritableDatabase();
        int insertReturn;
        ContentValues data = getExplorerData(explorer);
        insertReturn = (int) dataBase.insert(TABLE, null, data);
        Log.d("Insert Explorer", String.valueOf(insertReturn));
        return insertReturn;
    }

    public Explorer findExplorer(String email){
        SQLiteDatabase dataBase = getWritableDatabase();
        Cursor cursor;
        cursor = dataBase.query(TABLE,new String[] {COLUMN_NICKNAME,COLUMN_EMAIL,COLUMN_PASSWORD,COLUMN_SCORE, COLUMN_CORRECT_QUESTIONS, COLUMN_QUESTIONS_ANSWERED}, COLUMN_EMAIL + " ='" + email +"'",null, null , null ,null);
        Log.d("Cursor Count Find", String.valueOf(cursor.getCount()));
        Explorer explorer = new Explorer();
        if(cursor.moveToFirst()){
            explorer.setNickname(cursor.getString(cursor.getColumnIndex(COLUMN_NICKNAME)));
            explorer.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
            explorer.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
            explorer.setScore(cursor.getInt(cursor.getColumnIndex(COLUMN_SCORE)));
            explorer.setCorrectQuestion(cursor.getInt(cursor.getColumnIndex(COLUMN_CORRECT_QUESTIONS)));
            explorer.setQuestionAnswered(cursor.getInt(cursor.getColumnIndex(COLUMN_QUESTIONS_ANSWERED)));
        }else{
            throw new SQLException();
        }

        cursor.close();

        return explorer;
    }

    public Explorer findExplorerLogin(String email, String password){
        SQLiteDatabase dataBase = getWritableDatabase();
        Cursor cursor;
        cursor = dataBase.query(TABLE, new String[] {COLUMN_NICKNAME,COLUMN_EMAIL,COLUMN_PASSWORD,COLUMN_SCORE,COLUMN_CORRECT_QUESTIONS, COLUMN_QUESTIONS_ANSWERED}, COLUMN_EMAIL + " ='" + email +"' AND " + COLUMN_PASSWORD + " ='" + password +"'",null, null , null ,null);

        Explorer explorer = new Explorer();

        if(cursor.moveToFirst()){
            explorer.setNickname(cursor.getString(cursor.getColumnIndex(COLUMN_NICKNAME)));
            explorer.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
            explorer.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
            explorer.setScore(cursor.getInt(cursor.getColumnIndex(COLUMN_SCORE)));
            explorer.setCorrectQuestion(cursor.getInt(cursor.getColumnIndex(COLUMN_CORRECT_QUESTIONS)));
            explorer.setQuestionAnswered(cursor.getInt(cursor.getColumnIndex(COLUMN_QUESTIONS_ANSWERED)));
        }

        cursor.close();

        return explorer;
    }

    public int updateExplorer(Explorer explorer) throws SQLiteConstraintException{
        SQLiteDatabase dataBase = getWritableDatabase();
        ContentValues data = getExplorerData(explorer);

        Log.d("====QUEST==ION====", explorer.getCorrectQuestion() + explorer.getEmail() + explorer.getQuestionAnswered());
        String[] parameters = {explorer.getEmail()};
        int updateReturn;
        updateReturn = dataBase.update(TABLE, data, COLUMN_EMAIL + " = ?", parameters);

        return updateReturn;
    }

    public int deleteExplorer(Explorer explorer){
        SQLiteDatabase dataBase = getWritableDatabase();
        String[] parameters = {explorer.getEmail()};
        int deleteReturn;
        deleteReturn = dataBase.delete(TABLE, COLUMN_EMAIL + " = ?", parameters);

        deleteExplorerOnOnlineDataBase(explorer);

        return deleteReturn;
    }

    private void deleteExplorerOnOnlineDataBase (Explorer explorer) {
        Response.Listener<String> respostListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
            }
        };

        DeleteRequest deleteRequest = new DeleteRequest(explorer.getEmail(), respostListener);
        RequestQueue queue = Volley.newRequestQueue(this.context);
        queue.add(deleteRequest);
    }

    public void deleteAllExplorers(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("DELETE FROM " + TABLE);
    }

    public int findEnergy(String email){
        SQLiteDatabase dataBase = getWritableDatabase();
        Cursor cursor;
        cursor = dataBase.query(TABLE, new String[] {COLUMN_ENERGY}, COLUMN_EMAIL + " ='" + email +"'",null, null , null ,null);

        Explorer explorer = new Explorer();

        if(cursor.moveToFirst()){
            explorer.setEnergy(cursor.getInt(cursor.getColumnIndex(COLUMN_ENERGY)));
        }

        cursor.close();

        return explorer.getEnergy();
    }

    public int updateEnergy(Explorer explorer) throws SQLiteConstraintException{
        SQLiteDatabase dataBase = getWritableDatabase();

        ContentValues data = new ContentValues();
        data.put(COLUMN_ENERGY, explorer.getEnergy());

        String[] parameters = {explorer.getEmail()};

        int updateReturn;
        updateReturn = dataBase.update(TABLE, data, COLUMN_EMAIL + " = ?", parameters);

        return updateReturn;
    }
}