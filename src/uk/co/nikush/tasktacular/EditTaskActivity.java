package uk.co.nikush.tasktacular;

import java.util.Calendar;

import uk.co.nikush.tasktacular.database.TasksTable;
import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class EditTaskActivity extends Activity implements OnClickListener, OnDateSetListener
{
    private long task_id;

    private EditText title_field;

    private EditText description_field;

    private TasksTable tasks;

    private String task_date = "";

    private String task_time = "00:00:00";

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
        ((Button) findViewById(R.id.due_date_button)).setOnClickListener(this);
        ((TextView) findViewById(R.id.due_date_text)).setOnClickListener(this);

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
        switch (v.getId())
        {
            case R.id.save_button:
                String title = title_field.getText().toString();
                String desc = description_field.getText().toString();
                tasks.updateTask(task_id, title, desc, constructDate());
                finish();
                break;

            case R.id.due_date_text:
                showDialog(0);
                break;

            case R.id.due_date_button:
                task_date = "";
                ((TextView) findViewById(R.id.due_date_text)).setText(getResources().getString(R.string.due_date));
                break;
        }
    }

    @Override
    protected Dialog onCreateDialog(int id)
    {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(this, this, year, month, day);
    }

    private String constructDate()
    {
        if (task_date == "")
            return "";
        return task_date + " " + task_time;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
    {
        task_date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        ((TextView) findViewById(R.id.due_date_text)).setText(task_date);
    }
}
