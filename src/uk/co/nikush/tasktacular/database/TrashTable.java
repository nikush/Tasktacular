package uk.co.nikush.tasktacular.database;

import android.content.Context;
import android.database.Cursor;

public class TrashTable extends DatabaseHelper
{
    public static final String KEY_ROWID = "_id";

    public static final int KEY_ROWID_INDEX = 0;

    public static final String KEY_DATE_ADDED = "date_added";

    public static final int KEY_DATE_ADDED_INDEX = 1;

    public static final String TABLE_NAME = "trash";

    public TrashTable(Context context)
    {
        super(context);
    }

    /**
     * Get all tasks in the trash.
     * 
     * Tasks are ordered by the date they were trashed, in descending order.
     * 
     * @return  all trashed tasks
     */
    public Cursor getAll()
    {
        return db.rawQuery("SELECT " + TasksTable.TABLE_NAME + ".* " +
        		"FROM " + TasksTable.TABLE_NAME + ", " + TrashTable.TABLE_NAME +
        		" WHERE " + TasksTable.TABLE_NAME + "." + TasksTable.KEY_ROWID + 
        		      "== " + TrashTable.TABLE_NAME + "." + TrashTable.KEY_ROWID +
		          " ORDER BY " + TrashTable.KEY_DATE_ADDED + " DESC", null);
    }

    public void restore()
    {

    }

    public void clean()
    {

    }

}
