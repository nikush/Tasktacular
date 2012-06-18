package uk.co.nikush.tasktacular.database;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

public class TasksTable extends DatabaseHelper
{
    public static final String KEY_ROWID = "_id";

    public static final int KEY_ROWID_INDEX = 0;

    public static final String KEY_TITLE = "title";

    public static final int KEY_TITLE_INDEX = 1;

    public static final String KEY_DESCRIPTION = "description";

    public static final int KEY_DESCRIPTION_INDEX = 2;

    public static final String KEY_COMPLETE = "complete";

    public static final int KEY_COMPLETE_INDEX = 3;

    public static final String KEY_DATE_CREATED = "date_created";

    public static final int KEY_DATE_CREATED_INDEX = 4;

    public static final String KEY_DATE_DUE = "date_due";

    public static final int KEY_DATE_DUE_INDEX = 5;

    public static final String KEY_DATE_LAST_MODIFIED = "date_last_modified";

    public static final int KEY_DATE_LAST_MODIFIED_INDEX = 6;

    public static final String TABLE_NAME = "tasks";

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public TasksTable(Context ctx)
    {
        super(ctx);
    }

    public Cursor getAllTasks()
    {
        return db.query(TABLE_NAME, new String[] { KEY_ROWID, KEY_TITLE,
                KEY_DESCRIPTION }, null, null, null, null, null);
    }

    public Cursor getTask(long rowId) throws SQLException
    {
        Cursor mCursor = db.query(true, TABLE_NAME, new String[] { KEY_ROWID,
                KEY_TITLE, KEY_DESCRIPTION, KEY_COMPLETE, KEY_DATE_CREATED,
                KEY_DATE_DUE, KEY_DATE_LAST_MODIFIED }, KEY_ROWID + "=" + rowId, null, null, null, null, null);
        if (mCursor != null)
        {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public long insertTask(String title, String description, String due_date)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TITLE, title);
        initialValues.put(KEY_DESCRIPTION, description);
        initialValues.put(KEY_COMPLETE, 0);

        String now = format.format(new Date());
        initialValues.put(KEY_DATE_CREATED, now);
        initialValues.put(KEY_DATE_DUE, due_date);
        initialValues.put(KEY_DATE_LAST_MODIFIED, now);
        return db.insert(TABLE_NAME, null, initialValues);
    }

    public boolean updateTask(long rowId, String title, String description, String due_date)
    {
        String now = format.format(new Date());

        ContentValues args = new ContentValues();
        args.put(KEY_TITLE, title);
        args.put(KEY_DESCRIPTION, description);
        args.put(KEY_DATE_LAST_MODIFIED, now);
        args.put(KEY_DATE_DUE, due_date);
        return db.update(TABLE_NAME, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public void deleteTask(long id)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(TrashTable.KEY_ROWID, id);

        String now = format.format(new Date());
        initialValues.put(TrashTable.KEY_DATE_ADDED, now);

        db.insert(TrashTable.TABLE_NAME, null, initialValues);
    }

    public void markAsComplete(long rowId)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_COMPLETE, 1);
        db.update(TABLE_NAME, args, KEY_ROWID + "=" + rowId, null);
    }

    public void markAsIncomplete(long rowId)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_COMPLETE, 0);
        db.update(TABLE_NAME, args, KEY_ROWID + "=" + rowId, null);
    }
}
