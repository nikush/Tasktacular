package uk.co.nikush.tasktacular;

import java.util.Calendar;

import uk.co.nikush.tasktacular.database.TasksTable;
import uk.co.nikush.tasktacular.helpers.DateHelper;
import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Activity for adding tasks to the database
 * 
 * @author  Nikush Patel
 */
public class AddTaskActivity extends Activity implements OnClickListener, DatePickerDialog.OnDateSetListener
{
    private long task_due_timestamp;

    private ImageButton date_remove;

    private Button date_text;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        date_remove = (ImageButton) findViewById(R.id.due_date_button);
        date_remove.setOnClickListener(this);

        date_text = (Button) findViewById(R.id.due_date_text);
        date_text.setOnClickListener(this);
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
                task_due_timestamp = 0;
                date_text.setText(getResources().getString(R.string.add_due_date));
                date_remove.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_task, menu);
        return true;
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

            case R.id.save:
                addTask();
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
        tasks.insertTask(title_val, desc_val, task_due_timestamp);
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

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
    {
        task_due_timestamp = DateHelper.makeTimestamp(year, monthOfYear, dayOfMonth);
        date_text.setText(DateHelper.format(task_due_timestamp));
        date_remove.setVisibility(View.VISIBLE);
    }
}
