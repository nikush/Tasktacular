package uk.co.nikush.tasktacular;

import uk.co.nikush.tasktacular.database.TasksTable;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class TaskDetailActivity extends Activity
{
    private long task_id;

    private TasksTable tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_detail);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        task_id = getIntent().getLongExtra("task_id", 0);

        tasks = new TasksTable(this);
        tasks.open();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        readData();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        tasks.close();
    }

    private void readData()
    {
        Cursor record = tasks.getTask(task_id);

        TextView title = (TextView) findViewById(R.id.task_title);
        title.setText(record.getString(TasksTable.KEY_TITLE_INDEX));
        TextView description = (TextView) findViewById(R.id.task_description);
        description.setText(record.getString(TasksTable.KEY_DESCRIPTION_INDEX));
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.task_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                Intent home_intent = new Intent(this, TasktacularActivity.class);
                home_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(home_intent);
                return true;

            case R.id.edit_button:
                Intent edit_intent = new Intent(this, EditTaskActivity.class);
                edit_intent.putExtra("task_id", task_id);
                startActivity(edit_intent);
                return true;

            case R.id.delete_button:
                showDialog();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showDialog()
    {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        TaskDeleteDialog newFrag = TaskDeleteDialog.newInstance();
        Bundle args = new Bundle();
        args.putLong("task_id", task_id);
        newFrag.setArguments(args);
        newFrag.show(ft, "dialog");
    }
}
