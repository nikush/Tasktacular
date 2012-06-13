package uk.co.nikush.tasktacular.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    protected SQLiteDatabase db;

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
