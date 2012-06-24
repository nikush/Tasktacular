package uk.co.nikush.tasktacular;

import uk.co.nikush.tasktacular.database.TasksTable;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

/**
 * Dialog prompted the user when a task is being deleted.
 * 
 * @author  Nikush Patel
 */
public class TaskDeleteDialog extends DialogFragment implements DialogInterface.OnClickListener
{
    static TaskDeleteDialog newInstance()
    {
        return new TaskDeleteDialog();
    }

    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton(getResources().getString(android.R.string.yes), this);
        builder.setNegativeButton(getResources().getString(android.R.string.no), this);
        builder.setIcon(getResources().getDrawable(R.drawable.alerts_and_states_warning));
        builder.setTitle(getResources().getString(R.string.delete_task_dialog_title));
        
        // inflating layout in onCreateView() doesn't work, so I'll do it here.
        LayoutInflater li = getActivity().getLayoutInflater();
        builder.setView(li.inflate(R.layout.delete_dialog_fragment, null));
        
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which)
    {
        // positive button
        if (which == -1)
        {
            TasksTable tasks = new TasksTable(getActivity());
            tasks.open();
            tasks.deleteTask(getArguments().getLong("task_id"));
            tasks.close();
            
            Intent home_intent = new Intent(getActivity(), TasktacularActivity.class);
            home_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(home_intent);
        }
    }
}