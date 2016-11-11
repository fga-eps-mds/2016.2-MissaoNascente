package gov.jbb.missaonascente.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HelperDAO extends SQLiteOpenHelper{
    private static final String NAME_DB="JBB";
    private static final int VERSION=1;

    public HelperDAO(Context context) {
        super(context,NAME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        ExplorerDAO.createTableExplorer(sqLiteDatabase);
        BookDAO.createTableBook(sqLiteDatabase);
        ElementDAO.createTableElement(sqLiteDatabase);
        ElementDAO.createTableElementExplorer(sqLiteDatabase);
        ElementDAO.createTableVersion(sqLiteDatabase);
        NotificationDAO.createTableNotification(sqLiteDatabase);
        QuestionDAO.createTableQuestion(sqLiteDatabase);
        AlternativeDAO.createTableAlternative(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}
}
