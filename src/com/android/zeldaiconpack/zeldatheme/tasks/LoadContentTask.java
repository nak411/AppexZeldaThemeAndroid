package com.android.zeldaiconpack.zeldatheme.tasks;

import android.os.AsyncTask;

import com.android.zeldaiconpack.zeldatheme.interfaces.TaskCallBacks;

/**
 * Created by Naveed
 * Date: 12/15/13
 * Time: 7:41 PM
 * Contact: nak411@gmail.com
 *
 * Generic class for loading any type of data.
 * Note that this class currently does not implement onProgressUpdate()
 * Subclasses can override and implement it (if needed)
 */
public abstract class LoadContentTask<Params, Progress, Result>
        extends AsyncTask<Params, Progress, Result> {

    private TaskCallBacks mCallBacks;

    public abstract Result loadData();

    public LoadContentTask(TaskCallBacks mCallBacks){
        this.mCallBacks = mCallBacks;
    }

    @Override
    protected void onPreExecute() {
        if(mCallBacks!=null)
            mCallBacks.onPreExecute();
    }

    @Override
    protected Result doInBackground(Params... params) {
        return loadData();
    }

    @Override
    protected void onCancelled(Result result) {
        if(mCallBacks !=null)
            mCallBacks.onCancelled();
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        if(mCallBacks != null)
            mCallBacks.onPostExecute(result);
    }
}
