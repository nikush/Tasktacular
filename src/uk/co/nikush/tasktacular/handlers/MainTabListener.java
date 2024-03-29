package uk.co.nikush.tasktacular.handlers;

import uk.co.nikush.tasktacular.R;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Fragment;
import android.app.FragmentTransaction;

/**
 * Handler class for managing tabs in the main activity
 * 
 * @author  Nikush Patel
 */
public class MainTabListener implements TabListener
{
    public Fragment fragment;

    public MainTabListener(Fragment fragment)
    {
        this.fragment = fragment;
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft)
    {
        ft.replace(R.id.fragment_container, fragment);
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft)
    {
        ft.remove(fragment);
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft)
    {
    
    }

}
