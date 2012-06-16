package uk.co.nikush.tasktacular;

import java.util.Calendar;

import uk.co.nikush.tasktacular.database.TasksTable;
import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

/**
 * Activity for adding tasks to the database
 * 
 * @author  Nikush Patel
 */
public class AddTaskActivity extends Activity implements OnClickListener, DatePickerDialog.OnDateSetListener
{
    private String task_date = "";

    private String task_time = "00:00:00";

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ((Button) findViewById(R.id.add_button)).setOnClickListener(this);
        ((Button) findViewById(R.id.due_date_button)).setOnClickListener(this);
        ((TextView) findViewById(R.id.due_date_text)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.add_button:
                addTask();
                break;

            case R.id.due_date_text:
                showDialog(0);
                break;

            case R.id.due_date_button:
                task_date = "";
                ((TextView) findViewById(R.id.due_date_text)).setText(getResources().getString(R.string.add_due_date));
                break;
        }
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

    private void addTask()
    {
        TextView titleText = (TextView) findViewById(R.id.title_input);
        TextView descText = (TextView) findViewById(R.id.description_input);

        String title_val = titleText.getText().toString();
        String desc_val = descText.getText().toString();

        TasksTable tasks = new TasksTable(this);
        tasks.open();
        tasks.insertTask(title_val, desc_val, constructDate());
        tasks.close();

        // return to home 
        Intent intent = new Intent(this, TasktacularActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
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
