package uk.co.nikush.tasktacular.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TasksTable extends DatabaseHelper
{
    public static final String KEY_ROWID = "_id";

    public static final String KEY_TITLE = "title";

    public static final String KEY_DESCRIPTION = "description";

    public static final String KEY_DATE_CREATED = "date_created";

    public static final String KEY_DATE_DUE = "date_due";

    public static final String KEY_DATE_LAST_MODIFIED = "date_last_modified";

    public static final String TABLE_NAME = "tasks";

    static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ("
            + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_TITLE
            + " TEXT NOT NULL, " + KEY_DESCRIPTION + " TEXT, "
            + KEY_DATE_CREATED + " TEXT NOT NULL, " + KEY_DATE_DUE + " TEXT, "
            + KEY_DATE_LAST_MODIFIED + " TEXT NOT NULL);";

    public TasksTable(Context ctx)
    {
        super(ctx);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        try
        {
            db.execSQL(TABLE_CREATE);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.w("TasksTable", "Upgrading TASKS from version " + oldVersion
                + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long insertTask(String title, String description)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TITLE, title);
        initialValues.put(KEY_DESCRIPTION, description);
        initialValues.put(KEY_DATE_CREATED, "");
        initialValues.put(KEY_DATE_DUE, "");
        initialValues.put(KEY_DATE_LAST_MODIFIED, "");
        return db.insert(TABLE_NAME, null, initialValues);
    }

    public boolean deleteTask(long rowId)
    {
        return db.delete(TABLE_NAME, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public Cursor getAllTasks()
    {
        return db.query(TABLE_NAME, new String[] { KEY_ROWID, KEY_TITLE,
                KEY_DESCRIPTION }, null, null, null, null, null);
    }

    public Cursor getTask(long rowId) throws SQLException
    {
        Cursor mCursor = db.query(true, TABLE_NAME, new String[] { KEY_ROWID,
                KEY_TITLE, KEY_DESCRIPTION }, KEY_ROWID + "=" + rowId, null, null, null, null, null);
        if (mCursor != null)
        {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean updateTask(long rowId, String title, String description)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_TITLE, title);
        args.put(KEY_DESCRIPTION, description);
        return db.update(TABLE_NAME, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

}
