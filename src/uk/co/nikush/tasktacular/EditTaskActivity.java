package uk.co.nikush.tasktacular;

import java.util.HashMap;

import uk.co.nikush.tasktacular.database.TasksTable;
import uk.co.nikush.tasktacular.helpers.DateHelper;
import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class EditTaskActivity extends Activity implements OnClickListener, OnDateSetListener
{
    private long task_id;

    private EditText title_field;

    private EditText description_field;

    private TextView date_field;

    private ImageButton date_remove;

    private Button date_text_button;

    private TasksTable tasks;

    private long task_due_timestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_task);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        title_field = (EditText) findViewById(R.id.task_title);
        description_field = (EditText) findViewById(R.id.task_description);
        date_field = (TextView) findViewById(R.id.due_date_text);

        date_remove = (ImageButton) findViewById(R.id.due_date_button);
        date_remove.setOnClickListener(this);

        date_text_button = (Button) findViewById(R.id.due_date_text);
        date_text_button.setOnClickListener(this);

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
        task_due_timestamp = c.getLong(TasksTable.KEY_DATE_DUE_INDEX);

        String date_str;
        if (task_due_timestamp == 0)
        {
            date_str = getResources().getString(R.string.add_due_date);
            date_remove.setVisibility(View.INVISIBLE);
        } else
        {
            date_str = DateHelper.format(task_due_timestamp);
        }

        title_field.setText(title);
        description_field.setText(desc);
        date_field.setText(date_str);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;

            case R.id.save:
                String title = title_field.getText().toString();
                String desc = description_field.getText().toString();
                tasks.updateTask(task_id, title, desc, task_due_timestamp);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.due_date_text:
                showDialog(0);
                break;

            case R.id.due_date_button:
                task_due_timestamp = 0;
                date_text_button.setText(getResources().getString(R.string.add_due_date));
                date_remove.setVisibility(View.INVISIBLE);
                break;
        }
    }

    @Override
    protected Dialog onCreateDialog(int id)
    {
        int year, month, day;

        long timeToUse = (task_due_timestamp == 0) ? DateHelper.now() : task_due_timestamp;
        HashMap<String, Integer> map = DateHelper.disectTimestamp(timeToUse);
        
        year = map.get("year");
        month = map.get("month");
        day = map.get("day");

        return new DatePickerDialog(this, this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
    {
        task_due_timestamp = DateHelper.makeTimestamp(year, monthOfYear, dayOfMonth);
        date_text_button.setText(DateHelper.format(task_due_timestamp));
        date_remove.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_task, menu);
        return true;
    }
}
