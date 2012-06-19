package uk.co.nikush.tasktacular.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Simple class used to help working with the SQLiteOpenHelper.
 * 
 * It acquired the database object and keeps track of the database version.
 * 
 * @author  Nikush Patel
 */
public abstract class DatabaseHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "tasktacular";

    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_CREATE_TASKS = 
        "CREATE TABLE " + TasksTable.TABLE_NAME + " ("
        + TasksTable.KEY_ROWID  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + TasksTable.KEY_TITLE  + " TEXT NOT NULL, "
        + TasksTable.KEY_DESCRIPTION + " TEXT, "
        + TasksTable.KEY_COMPLETE + " INTEGER, "
        + TasksTable.KEY_DATE_CREATED  + " INTEGER NOT NULL, "
        + TasksTable.KEY_DATE_DUE + " INTEGER, "
        + TasksTable.KEY_DATE_LAST_MODIFIED + " INTEGER NOT NULL);";

    public static final String TABLE_CREATE_TRASH = 
        "CREATE TABLE " + TrashTable.TABLE_NAME + " ( "
        + TrashTable.KEY_ROWID + " INTEGER PRIMARY KEY NOT NULL, "
        + TrashTable.KEY_DATE_ADDED + " INTEGER NOT NULL, "
        + "FOREIGN KEY(" + TrashTable.KEY_ROWID + ") " +
            "REFERENCES " + TasksTable.TABLE_NAME + "(" + TasksTable.KEY_ROWID + ") );";

    protected SQLiteDatabase db;

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        try
        {
            Log.d("athedu", TABLE_CREATE_TRASH);
            db.execSQL(TABLE_CREATE_TASKS);
            db.execSQL(TABLE_CREATE_TRASH);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TasksTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TrashTable.TABLE_NAME);
        onCreate(db);
    }

    public void open()
    {
        db = getWritableDatabase();
    }

    public void close()
    {
        db.close();
    }
}
