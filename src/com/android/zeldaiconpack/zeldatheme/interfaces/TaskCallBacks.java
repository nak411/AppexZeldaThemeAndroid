package com.android.zeldaiconpack.zeldatheme.interfaces;

/**
 * Created by Naveed
 * Date: 12/15/13
 * Time: 3:12 PM
 * Contact: nak411@gmail.com
 */

/**
 * Callback interface through which the fragment will report the
 * task's progress and results back to the Activity.
 */
public interface TaskCallBacks {

    void onPreExecute();
    void onProgressUpdate(int percent);
    void onCancelled();
    void onPostExecute(Object result);
}
