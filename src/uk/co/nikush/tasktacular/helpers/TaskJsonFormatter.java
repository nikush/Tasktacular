package uk.co.nikush.tasktacular.helpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.nikush.tasktacular.database.TasksTable;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

/**
 * Get task database records in a structured JSON object.
 * 
 * @author  Nikush Patel
 */
public class TaskJsonFormatter
{
    public static JSONObject getTasks(Context ctx)
    {
        JSONObject root = new JSONObject();
        
        TasksTable tasks = new TasksTable(ctx);
        tasks.open();
        Cursor newTasks = tasks.getTasksCreatedAfter(0);
        
        // create: []
        JSONArray tasksCreatedArray = new JSONArray();
        
        newTasks.moveToFirst();
        do
        {
            tasksCreatedArray.put(taskToJson(newTasks));
        } while (newTasks.moveToNext());
        
        tasks.close();
        
        try
        {
            root.put("create", tasksCreatedArray);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        
        Log.d("TaskJsonFormatter", root.toString());
        return root;
    }
    
    private static JSONObject taskToJson(Cursor task)
    {
        JSONObject taskObj = new JSONObject();
        try
        {
            taskObj.put("id", task.getString(TasksTable.KEY_ROWID_INDEX));
            taskObj.put("title", task.getString(TasksTable.KEY_TITLE_INDEX));
            taskObj.put("description", task.getString(TasksTable.KEY_DESCRIPTION_INDEX));
            taskObj.put("complete", task.getString(TasksTable.KEY_COMPLETE_INDEX));
            taskObj.put("date_created", task.getString(TasksTable.KEY_DATE_CREATED_INDEX));
            taskObj.put("date_due", task.getString(TasksTable.KEY_DATE_DUE_INDEX));
            taskObj.put("date_last_modified", task.getString(TasksTable.KEY_DATE_LAST_MODIFIED_INDEX));
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return taskObj;
    }
}
