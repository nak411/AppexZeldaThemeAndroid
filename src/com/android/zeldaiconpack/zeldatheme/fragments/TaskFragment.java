package com.android.zeldaiconpack.zeldatheme.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import com.android.zeldaiconpack.zeldatheme.interfaces.TaskCallBacks;
import com.android.zeldaiconpack.zeldatheme.tasks.LoadContentTask;

/**
 * Created by Naveed
 * Date: 12/15/13
 * Time: 2:15 PM
 * Contact: nak411@gmail.com
 */
public abstract class TaskFragment extends Fragment {

    //Used to execute any task
    public abstract LoadContentTask getTask();

    //Call backs to communicate back to the activity
    public TaskCallBacks mCallBacks;
    //Generic class for loading any type of date
    private LoadContentTask mTask;
    private boolean mLoading;
    /**
     * Hold a reference to the parent Activity so we can report the
     * task's current progress and results. The Android framework
     * will pass us a reference to the newly created Activity after
     * each configuration change.
     *
     * @param activity the newly created activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //Prevent the caller from passing in an invalid activity
        if (!(activity instanceof TaskCallBacks))
            throw new IllegalStateException("Activity must implement task call back interface");

        //Hold reference to the current instance of the activity so we can report
        //back to it
        mCallBacks = (TaskCallBacks) activity;
        Log.d("TASKMONITOR", "Called on Attach of task fragment");
    }

    /**
     * Only called when the retained fragment is first
     * created
     *
     * @param savedInstanceState state information
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Retain information across configuration change
        setRetainInstance(true);
        Log.d("TASKMONITOR", "Called on Create of task fragment");
        start();
    }

    /**
     * Set call backs to null so we don't
     * leak the activity instance
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mCallBacks = null;
        Log.d("TASKMONITOR", "Called on detatch of task fragment");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TASKMONITOR", "Called on destroy of task fragment");
        //Cancel the task if the fragment is destroyed before the task finishes
        //The app was closed by the user
        cancel();
    }

    /**
     * Starts the background task
     */
    @SuppressWarnings("unchecked")
    public void start() {
        if (!mLoading) {
            //Create and execute the task
            mTask = getTask();
            //(Not sure how to get rid off the unchecked
            // warning here)
            mTask.execute();
            mLoading = true;
        }
    }

    /**
     * Cancel the background task
     */
    public void cancel() {
        if (mLoading) {
            //Cancel without interruption
            mTask.cancel(false);
            mTask = null;
            mLoading = false;
        }
    }
}
