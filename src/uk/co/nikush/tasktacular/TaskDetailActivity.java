package uk.co.nikush.tasktacular;

import uk.co.nikush.tasktacular.database.TasksTable;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class TaskDetailActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_detail);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        long task_id = getIntent().getLongExtra("task_id", 0);

        TasksTable tasks = new TasksTable(this);
        tasks.open();
        Cursor record = tasks.getTask(task_id);
        tasks.close();

        TextView title = (TextView) findViewById(R.id.task_title);
        title.setText(record.getString(TasksTable.KEY_TITLE_INDEX));
        TextView description = (TextView) findViewById(R.id.task_description);
        description.setText(record.getString(TasksTable.KEY_DESCRIPTION_INDEX));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                Intent intent = new Intent(this, TasktacularActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
