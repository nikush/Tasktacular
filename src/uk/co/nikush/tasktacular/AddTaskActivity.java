package uk.co.nikush.tasktacular;

import uk.co.nikush.tasktacular.database.DBAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AddTaskActivity extends Activity implements OnClickListener
{
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        ((Button) findViewById(R.id.add_button)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.add_button:
                addTask();
                break;
        }
    }

    private void addTask()
    {
        TextView titleText = (TextView) findViewById(R.id.title);
        TextView descText = (TextView) findViewById(R.id.description);

        DBAdapter db = new DBAdapter(this);
        db.open();
        db.insertTask(titleText.getText().toString(), descText.getText().toString()); // returns id as long data type
        db.close();
        //startActivity(new Intent(this, TasktacularActivity.class));
    }
}
