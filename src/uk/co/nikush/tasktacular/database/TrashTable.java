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
    
    /**
     * See if a task has been trashed.
     * 
     * @param   id  the task ID in the database
     * @return  wether it's trashed or not
     */
    public boolean taskInTrash(long id)
    {
        Cursor c = db.query(true, TABLE_NAME, new String[] { KEY_ROWID }, KEY_ROWID + "=" + id, null, null, null, null, null);
        if (c != null)
            return c.moveToFirst();
        return false;
    }

    /**
     * Restore a task from the trash back into the active tasks list.
     * 
     * This will remove indexing entry from the trash table allowing for the 
     * task to appear to be active again.
     * 
     * @param   id  the taks ID in the database
     * @return  if the restore was successfull
     */
    public boolean restore(long id)
    {
        return db.delete(TABLE_NAME, KEY_ROWID + "== " + id, null)  == 1;
    }

    public void clean()
    {

    }
    
    /**
     * Get all trash records added after a specified date.
     * 
     * @param   timestamp   the date to use
     * @return  cursor of matching trash recordse
     */
    public Cursor getTasksAddedAfter(long timestamp)
    {
        Cursor mCursor = db.query(true, TABLE_NAME, new String[] { KEY_ROWID, KEY_DATE_ADDED }, 
                KEY_DATE_ADDED + ">" + timestamp, null, null, null, null, null);
        if (mCursor != null)
        {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

}
