package uk.co.nikush.tasktacular;

import uk.co.nikush.tasktacular.database.DBAdapter;
import uk.co.nikush.tasktacular.fragments.AllFragment;
import uk.co.nikush.tasktacular.fragments.ProjectsFragment;
import uk.co.nikush.tasktacular.handlers.MainTabListener;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class TasktacularActivity extends Activity
{
    public static Context appContext;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        appContext = this;

        ActionBar actionBar = getActionBar();

        // remove the top of the action bar to only show tabs
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        // Tell it we want to use tabs
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab allTab = actionBar.newTab().setText("All");
        ActionBar.Tab projectsTab = actionBar.newTab().setText("Projects");

        Fragment allFragment = new AllFragment();
        Fragment projectsFragment = new ProjectsFragment();

        allTab.setTabListener(new MainTabListener(allFragment));
        projectsTab.setTabListener(new MainTabListener(projectsFragment));

        actionBar.addTab(allTab);
        actionBar.addTab(projectsTab);

        readDb();
    }

    private void readDb()
    {
        DBAdapter db = new DBAdapter(this);
        db.open();
        Cursor c = db.getAllTasks();
        if (c.moveToFirst())
        {
            do
            {
                DisplayTask(c);
            } while (c.moveToNext());
        }
        db.close();
    }

    private void DisplayTask(Cursor c)
    {
        Toast.makeText(this, "id: " + c.getString(0) + "\n" + "title: "
                + c.getString(1) + "\n" + "description: " + c.getString(2)
                + "\n", Toast.LENGTH_SHORT).show();
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_all_fragment, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.add:
                startActivity(new Intent(this, AddTaskActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}