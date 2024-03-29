package uk.co.nikush.tasktacular.fragments;

import uk.co.nikush.tasktacular.R;
import uk.co.nikush.tasktacular.TaskDetailActivity;
import uk.co.nikush.tasktacular.database.TasksTable;
import uk.co.nikush.tasktacular.database.TrashTable;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Fragment for displaying projects in the database
 * 
 * @author  Nikush Patel
 */
public class TrashFragment extends ListFragment
{
    public TrashTable trash;

    public Context context;

    public View onCreateView(LayoutInflater inflator, ViewGroup container, Bundle savedInstanceState)
    {
        context = getActivity().getBaseContext();

        trash = new TrashTable(context);
        trash.open();

        return inflator.inflate(R.layout.main_trash_fragment, container, false);
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
        trash.close();
    }

    /**
     * Read the thrashed tasks from the database.
     */
    private void readTasks()
    {
        Cursor c = trash.getAll();

        String[] from = { TasksTable.KEY_ROWID, TasksTable.KEY_TITLE };
        int[] to = { R.id.task_id, R.id.task_title };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(context, R.layout.task_list_item, c, from, to, 0);
        setListAdapter(adapter);
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        String id_str = (String) ((TextView) v.findViewById(R.id.task_id)).getText();
        long record_id = Long.parseLong(id_str);

        Intent intent = new Intent(getActivity().getBaseContext(), TaskDetailActivity.class);
        intent.putExtra("task_id", record_id);

        startActivity(intent);
    }
}