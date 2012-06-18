package uk.co.nikush.tasktacular.database;

import java.text.SimpleDateFormat;

import android.content.Context;
import android.database.Cursor;

public class TrashTable extends DatabaseHelper
{
    public static final String KEY_ROWID = "_id";

    public static final int KEY_ROWID_INDEX = 0;

    public static final String KEY_DATE_ADDED = "date_added";

    public static final int KEY_DATE_ADDED_INDEX = 1;

    public static final String TABLE_NAME = "trash";

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public TrashTable(Context context)
    {
        super(context);
    }

    public Cursor getAll()
    {
        return db.query(TasksTable.TABLE_NAME, new String[] {
                TasksTable.KEY_ROWID, TasksTable.KEY_TITLE,
                TasksTable.KEY_DESCRIPTION }, null, null, null, null, null);
    }

    public void getTask()
    {

    }

    public void restore()
    {

    }

    public void clean()
    {

    }

}
