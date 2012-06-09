package uk.co.nikush.tasktacular;

import uk.co.nikush.tasktacular.fragments.AllFragment;
import uk.co.nikush.tasktacular.fragments.ProjectsFragment;
import uk.co.nikush.tasktacular.handlers.MainTabListener;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

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
    }
}