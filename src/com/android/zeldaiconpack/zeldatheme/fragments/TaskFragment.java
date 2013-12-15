package com.android.zeldaiconpack.zeldatheme.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;

import com.android.zeldaiconpack.zeldatheme.interfaces.TaskCallBacks;
import com.android.zeldaiconpack.zeldatheme.tasks.CopyResTask;

/**
 * Created by Naveed
 * Date: 12/15/13
 * Time: 2:15 PM
 * Contact: nak411@gmail.com
 */
public class TaskFragment extends Fragment{

    private TaskCallBacks mCallBacks;
    private CopyResTask mTask;

    /**
     * Hold a reference to the parent Activity so we can report the
     * task's current progress and results. The Android framework
     * will pass us a reference to the newly created Activity after
     * each configuration change.
     * @param activity the newly created activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallBacks = (TaskCallBacks) activity;
    }

    /**
     * Only called when the retained fragment is first
     * created
     * @param savedInstanceState state information
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Retain information across configuration change
        setRetainInstance(true);

        //Create and execute the task
        mTask = new CopyResTask(mCallBacks, this);
        mTask.execute();
    }

    /**
     * Set call backs to null so we don't
     * leak the activity instance
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mCallBacks = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Cancel the task if the fragment is destroyed before the task finishes
        //The app was closed by the user
        if(mTask !=null && ((mTask.getStatus() == AsyncTask.Status.RUNNING)
                || (mTask.getStatus() == AsyncTask.Status.PENDING))){
            mTask.cancel(true);
        }
    }
}
