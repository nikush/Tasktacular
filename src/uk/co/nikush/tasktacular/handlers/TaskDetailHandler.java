package uk.co.nikush.tasktacular.handlers;

import uk.co.nikush.tasktacular.database.TasksTable;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * Handler for managing interactions on the task detail activity.
 * 
 * @author  Nikush Patel
 */
public class TaskDetailHandler implements OnCheckedChangeListener
{
    private long task_id;
    
    private TasksTable tasks;
    
    public TaskDetailHandler(long task_id, TasksTable tasks)
    {
        this.task_id = task_id;
        this.tasks = tasks;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        if (isChecked)
            tasks.markAsComplete(task_id);
        else
            tasks.markAsIncomplete(task_id);
    }
}
