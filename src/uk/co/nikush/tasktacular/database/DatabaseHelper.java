package uk.co.nikush.tasktacular.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Simple class for communicating with the database
 * 
 * @author  Nikush Patel
 */
public abstract class DatabaseHelper extends SQLiteOpenHelper
{
    static final String DATABASE_NAME = "tasktacular";

    static final int DATABASE_VERSION = 1;

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
