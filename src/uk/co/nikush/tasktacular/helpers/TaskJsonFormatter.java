package uk.co.nikush.tasktacular.helpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.nikush.tasktacular.asyctasks.SyncHandler;
import uk.co.nikush.tasktacular.database.TasksTable;
import uk.co.nikush.tasktacular.database.TrashTable;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;

/**
 * Get task database records in a structured JSON object.
 * 
 * @author  Nikush Patel
 */
public class TaskJsonFormatter
{
    private static TasksTable tasks;
    private static TrashTable trash;
    
    private static long lastSync;
    
    /**
     * Get a structure of tasks outlining the data to synchronise with the web 
     * service.
     * 
     * @param   ctx     context
     * @return  the data to send to the web service
     */
    public static JSONObject getTasks(Context ctx)
    {
        SharedPreferences prefs = ctx.getSharedPreferences(SyncHandler.PREFS_NAME, 0); 
        lastSync = prefs.getLong(SyncHandler.PREFS_LAST_SYNC, 0);
        
        JSONObject root = new JSONObject();
        
        tasks = new TasksTable(ctx);
        tasks.open();
        trash = new TrashTable(ctx);
        trash.open();
        
        // construct task data
        try
        {
            root.put("create", createCreateList());
            root.put("update", createUpdateList());
            root.put("trash", createTrashList());
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        
        tasks.close();
        trash.close();
        
        return root;
    }
    
    /**
     * Create the 'create' list of tasks sent to the web service for it to 
     * create.
     * 
     * @return   the list of tasks records
     */
    private static JSONArray createCreateList()
    {
        Cursor newTasks = tasks.getTasksCreatedAfter(lastSync);
        JSONArray tasksCreatedArray = new JSONArray();
        
        if (newTasks.moveToFirst())
        {
            do
            {
                tasksCreatedArray.put(taskToJson(newTasks));
            } while (newTasks.moveToNext());
        }
        
        return tasksCreatedArray;
    }
    
    /**
     * Create the 'update' list of tasks sent to the web service for it to 
     * update.
     * 
     * @return   the list of tasks records
     */
    private static JSONArray createUpdateList()
    {
        Cursor modifiedTasks = tasks.getTasksModifiedAfter(lastSync);
        JSONArray tasksModifiedArray = new JSONArray();
        
        if (modifiedTasks.moveToFirst())
        {
            do
            {
                tasksModifiedArray.put(taskToJson(modifiedTasks));
            } while (modifiedTasks.moveToNext());  
        }
        
        return tasksModifiedArray;
    }
    
    /**
     * Create a JSONObject out of the current record in the cursor
     * 
     * @param   task    the cursor to use
     * @return  the task as a JSONObject
     */
    private static JSONObject taskToJson(Cursor task)
    {
        JSONObject taskObj = new JSONObject();
        try
        {
            taskObj.put("id", task.getInt(TasksTable.KEY_ROWID_INDEX));
            taskObj.put("title", task.getString(TasksTable.KEY_TITLE_INDEX));
            taskObj.put("description", task.getString(TasksTable.KEY_DESCRIPTION_INDEX));
            taskObj.put("complete", task.getInt(TasksTable.KEY_COMPLETE_INDEX));
            taskObj.put("date_created", task.getInt(TasksTable.KEY_DATE_CREATED_INDEX));
            taskObj.put("date_due", task.getInt(TasksTable.KEY_DATE_DUE_INDEX));
            taskObj.put("date_last_modified", task.getInt(TasksTable.KEY_DATE_LAST_MODIFIED_INDEX));
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return taskObj;
    }
    
    /**
     * Create the 'trash' list of tasks sent to the web service for it to 
     * trash.
     * 
     * @return   the list of tasks records
     */
    private static JSONArray createTrashList()
    {
        Cursor trashedTasks = trash.getTasksAddedAfter(lastSync);
        JSONArray tasksTrashedArray = new JSONArray();
        
        if (trashedTasks.moveToFirst())
        {
            do
            {
                JSONObject trashTask = new JSONObject();
                try
                {
                    trashTask.put("id", trashedTasks.getInt(TrashTable.KEY_ROWID_INDEX));
                    trashTask.put("date_added", trashedTasks.getInt(TrashTable.KEY_DATE_ADDED_INDEX));
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
                tasksTrashedArray.put(trashTask);
            } while (trashedTasks.moveToNext());  
        }
        
        return tasksTrashedArray;
    }
}
