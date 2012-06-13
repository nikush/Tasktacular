package uk.co.nikush.tasktacular.fragments;

import uk.co.nikush.tasktacular.R;
import uk.co.nikush.tasktacular.database.DBAdapter;
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
public class AllFragment extends ListFragment
{
    private Context context;

    public View onCreateView(LayoutInflater inflator, ViewGroup container, Bundle savedInstanceState)
    {
        context = getActivity().getBaseContext();

        // move this into one of the on... methods so it can get refreshed
        readDb();

        return inflator.inflate(R.layout.main_tasks_fragment, container, false);
    }

    /**
     * Read tasks and display into list view
     */
    private void readDb()
    {
        DBAdapter db = new DBAdapter(context);
        db.open();
        Cursor c = db.getAllTasks();

        String[] from = { "_id", "title" };
        int[] to = { R.id.task_id, R.id.task_title };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(context, R.layout.task_list_item, c, from, to, 0);
        setListAdapter(adapter);
        //db.close();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        String desc = "";
        String id_str = (String) ((TextView) v.findViewById(R.id.task_id)).getText();
        long record_id = Long.parseLong(id_str);

        DBAdapter db = new DBAdapter(context);
        db.open();
        Cursor c = db.getTask(record_id);
        desc = c.getString(2);
        db.close();
        Toast.makeText(context, desc, Toast.LENGTH_SHORT).show();
    }
}