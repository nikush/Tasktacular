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

    /**
     * Get all active tasks.
     * 
     * Task are ordered first by tasks with due dates in ascending order, then 
     * tasks without due dates at the bottom.
     * 
     * @return  All active tasks
     */
    public Cursor getAllTasks()
    {
        return db.rawQuery("SELECT * FROM (SELECT * " +
                "FROM " + TasksTable.TABLE_NAME + 
                " WHERE "+ TasksTable.KEY_ROWID +" NOT IN (" +
                        "SELECT " + TrashTable.KEY_ROWID + " FROM "+ TrashTable.TABLE_NAME +") " +
                        "AND " + TasksTable.KEY_DATE_DUE + " > 0" + 
                " ORDER BY " + TasksTable.KEY_DATE_DUE + " ASC) " + 
                "UNION ALL " +
                "SELECT * FROM (SELECT * " +
                "FROM " + TasksTable.TABLE_NAME + 
                " WHERE "+ TasksTable.KEY_ROWID +" NOT IN (" +
                        "SELECT " + TrashTable.KEY_ROWID + " FROM "+ TrashTable.TABLE_NAME +") " +
                        "AND " + TasksTable.KEY_DATE_DUE + " == 0)", null);
    }

    /**
     * Get a particular task by its ID in the database.
     * 
     * @param   rowId   the record ID in the database
     * @return  the task record
     * @throws  SQLException
     */
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

    /**
     * Insert a new task record into the database.
     * 
     * @param   title       the title of the task
     * @param   description the task description
     * @param   due_date    the unix timestamp representing when it is due
     * @return  the ID of the database record
     */
    public long insertTask(String title, String description, long due_date)
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

    /**
     * Update a task.
     * 
     * @param   rowId       record ID in database
     * @param   title       new title
     * @param   description new description
     * @param   due_date    new due date
     * @return  wether the update was successfull
     */
    public boolean updateTask(long rowId, String title, String description, long due_date)
    {
        String now = format.format(new Date());

        ContentValues args = new ContentValues();
        args.put(KEY_TITLE, title);
        args.put(KEY_DESCRIPTION, description);
        args.put(KEY_DATE_LAST_MODIFIED, now);
        args.put(KEY_DATE_DUE, due_date);
        return db.update(TABLE_NAME, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

    /**
     * Delete the specified task.
     * 
     * This doesn't delete the record from the database, but marks it as 
     * 'trashed' and to be deleted later.
     *  
     * @param   id      record ID
     */
    public void deleteTask(long id)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(TrashTable.KEY_ROWID, id);

        String now = format.format(new Date());
        initialValues.put(TrashTable.KEY_DATE_ADDED, now);

        db.insert(TrashTable.TABLE_NAME, null, initialValues);
    }

    /**
     * Mark a task as complete.
     * 
     * @param   rowId   record ID
     */
    public void markAsComplete(long rowId)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_COMPLETE, 1);
        db.update(TABLE_NAME, args, KEY_ROWID + "=" + rowId, null);
    }

    /**
     * Mark a task as incomplete.
     * 
     * @param   rowId   record ID
     */
    public void markAsIncomplete(long rowId)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_COMPLETE, 0);
        db.update(TABLE_NAME, args, KEY_ROWID + "=" + rowId, null);
    }
}
