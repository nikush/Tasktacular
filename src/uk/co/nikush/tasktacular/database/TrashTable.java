package uk.co.nikush.tasktacular.database;

import java.text.SimpleDateFormat;

import android.content.Context;

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

    public void getAll()
    {

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
