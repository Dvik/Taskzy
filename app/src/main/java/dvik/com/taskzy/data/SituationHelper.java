package dvik.com.taskzy.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Divya on 11/28/2016.
 */

public class SituationHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = SituationHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "situations.db";
    private static final int DATABASE_VERSION = 1;

    public SituationHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_SITUATION_TABLE = "CREATE TABLE " +
                SituationContract.SituationEntry.TABLE_SITUATIONS + "(" +
                SituationContract.SituationEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                SituationContract.SituationEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                SituationContract.SituationEntry.COLUMN_HEADPHONE_STATE + " TEXT NOT NULL, " +
                SituationContract.SituationEntry.COLUMN_WEATHER_STATE + " TEXT NOT NULL, " +
                SituationContract.SituationEntry.COLUMN_LATITUDE + " TEXT NOT NULL, " +
                SituationContract.SituationEntry.COLUMN_LONGITUDE + " TEXT NOT NULL, " +
                SituationContract.SituationEntry.COLUMN_ACTIVITY + " TEXT NOT NULL, " +
                SituationContract.SituationEntry.COLUMN_TIME + " TEXT NOT NULL, " +
                SituationContract.SituationEntry.COLUMN_ACTION + " TEXT NOT NULL, " +
                SituationContract.SituationEntry.COLUMN_ACTION_NAME + " TEXT NOT NULL);";

        sqLiteDatabase.execSQL(SQL_CREATE_SITUATION_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SituationContract.SituationEntry.TABLE_SITUATIONS);
        sqLiteDatabase.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                SituationContract.SituationEntry.TABLE_SITUATIONS + "'");

        onCreate(sqLiteDatabase);

    }
}
