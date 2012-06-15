package uk.co.nikush.tasktacular;

import uk.co.nikush.tasktacular.database.TasksTable;
import android.app.ActionBar;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditTaskActivity extends Activity implements OnClickListener
{
    private long task_id;

    private EditText title_field;

    private EditText description_field;

    private TasksTable tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_task);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        title_field = (EditText) findViewById(R.id.task_title);
        description_field = (EditText) findViewById(R.id.task_description);

        ((Button) findViewById(R.id.save_button)).setOnClickListener(this);

        task_id = getIntent().getLongExtra("task_id", 0);

        tasks = new TasksTable(this);
        tasks.open();

        populateFields();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        tasks.close();
    }

    private void populateFields()
    {
        Cursor c = tasks.getTask(task_id);
        String title = c.getString(TasksTable.KEY_TITLE_INDEX);
        String desc = c.getString(TasksTable.KEY_DESCRIPTION_INDEX);

        title_field.setText(title);
        description_field.setText(desc);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v)
    {
        String title = title_field.getText().toString();
        String desc = description_field.getText().toString();
        tasks.updateTask(task_id, title, desc);
        finish();
    }
}
