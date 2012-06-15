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
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TaskDeleteDialog extends DialogFragment implements DialogInterface.OnClickListener
{
    static TaskDeleteDialog newInstance()
    {
        TaskDeleteDialog dialog = new TaskDeleteDialog();
        return dialog;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.delete_task_question));
        builder.setPositiveButton(getResources().getString(android.R.string.yes), this);
        builder.setNegativeButton(getResources().getString(android.R.string.no), this);
        TextView tv = new TextView(getActivity());
        tv.setText("dskljhfasdkljfh");
        //builder.setView(tv);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return super.onCreateView(inflater, container, savedInstanceState);
        //return inflater.inflate(R.layout.delete_dialog_fragment, container, false);
    }
}