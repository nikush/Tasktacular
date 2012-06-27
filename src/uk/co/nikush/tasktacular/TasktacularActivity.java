package uk.co.nikush.tasktacular;

import uk.co.nikush.tasktacular.fragments.TasksFragment;
import uk.co.nikush.tasktacular.fragments.TrashFragment;
import uk.co.nikush.tasktacular.handlers.MainTabListener;
import uk.co.nikush.tasktacular.handlers.SyncHandler;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Main application activity. 
 * 
 * @author  Nikush Patel
 */
public class TasktacularActivity extends Activity
{
    public static Context appContext;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        // The activity is being created.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        appContext = this;

        setUpActionBar();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        // The activity is about to become visible.
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        // The activity has become visible (it is now "resumed").
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        // The activity is about to be destroyed.
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.add_button:
                startActivity(new Intent(this, AddTaskActivity.class));
                return true;
                
            case R.id.refresh_button:
                Log.d("TasktacularActivity", "Refresh");
                SyncHandler sync = new SyncHandler();
                sync.execute("http://10.0.2.2/tasktacular/");
                return true;
                
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Initialise the action bar for use with tabs
     */
    private void setUpActionBar()
    {
        ActionBar actionBar = getActionBar();

        // Tell it we want to use tabs
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tasksTab = actionBar.newTab().setText(getResources().getString(R.string.tasks));
        ActionBar.Tab trashTab = actionBar.newTab().setText(getResources().getString(R.string.trash));

        Fragment tasksFragment = new TasksFragment();
        Fragment trashFragment = new TrashFragment();

        tasksTab.setTabListener(new MainTabListener(tasksFragment));
        trashTab.setTabListener(new MainTabListener(trashFragment));

        actionBar.addTab(tasksTab);
        actionBar.addTab(trashTab);
    }
}