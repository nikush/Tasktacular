package uk.co.nikush.tasktacular.fragments;

import uk.co.nikush.tasktacular.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ProjectsFragment extends Fragment
{
    public View onCreateView(LayoutInflater inflator, ViewGroup container, Bundle savedInstanceState)
    {
        return inflator.inflate(R.layout.projects_fragment, container, false);
    }
}