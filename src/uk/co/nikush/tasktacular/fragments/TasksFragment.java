package uk.co.nikush.tasktacular.fragments;

import uk.co.nikush.tasktacular.R;
import uk.co.nikush.tasktacular.database.TasksTable;
import android.app.ListFragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Fragment that lists all tasks in the database
 * 
 * @author  Nikush Patel
 */
public class TasksFragment extends ListFragment
{
    private Context context;

    private TasksTable tasks;

    public View onCreateView(LayoutInflater inflator, ViewGroup container, Bundle savedInstanceState)
    {
        context = getActivity().getBaseContext();

        tasks = new TasksTable(context);
        tasks.open();

        return inflator.inflate(R.layout.main_tasks_fragment, container, false);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        readTasks();
    }

    public void onDestroyView()
    {
        super.onDestroyView();
        tasks.close();
    }

    /**
     * Read tasks and display into list view
     */
    private void readTasks()
    {
        Cursor c = tasks.getAllTasks();

        String[] from = { TasksTable.KEY_ROWID, TasksTable.KEY_TITLE };
        int[] to = { R.id.task_id, R.id.task_title };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(context, R.layout.task_list_item, c, from, to, 0);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        String desc = "";
        String id_str = (String) ((TextView) v.findViewById(R.id.task_id)).getText();
        long record_id = Long.parseLong(id_str);

        Cursor c = tasks.getTask(record_id);
        desc = c.getString(2);
        Toast.makeText(context, desc, Toast.LENGTH_SHORT).show();
    }
}