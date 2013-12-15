package com.android.zeldaiconpack.zeldatheme.tasks;

import android.app.Fragment;
import android.os.AsyncTask;
import com.android.zeldaiconpack.zeldatheme.interfaces.TaskCallBacks;
import com.android.zeldaiconpack.zeldatheme.structures.Icon;
import com.zeldaiconpack.zeldatheme.R;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by Naveed on 12/8/13.
 * nak411@gmail.com
 */
public class CopyResTask extends AsyncTask<Void, Integer, ArrayList<Icon>> {

    private TaskCallBacks mCallBacks;
    private Fragment mContext;

    public CopyResTask(TaskCallBacks mCallBacks, Fragment context) {
        this.mCallBacks = mCallBacks;
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        if (mCallBacks != null)
            mCallBacks.onPreExecute();
    }

    @Override
    protected ArrayList<Icon> doInBackground(Void... params) {
        final Class<R.drawable> c = R.drawable.class;
        final Field[] fields = c.getDeclaredFields();
        ArrayList<Icon> icons = new ArrayList<Icon>();
        for (Field field : fields) {
            final int resourceId;
            try {
                resourceId = field.getInt(null);
                String name = mContext.getResources().getResourceEntryName(resourceId);
                //Find a resources that starts with com or matches the 2 word pattern
                //Short circuit at "com_"
                if (name.startsWith("com_") || name.matches("\\w+_\\w+"))
                    icons.add(new Icon(resourceId, name));
            } catch (Exception e) {
                continue;
            }
            if (isCancelled())
                break;
        }
        return icons;

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        if (mCallBacks != null)
            mCallBacks.onProgressUpdate(values[0]);
    }

    @Override
    protected void onCancelled(ArrayList<Icon> icons) {
        if (mCallBacks != null)
            mCallBacks.onCancelled();
    }

    @Override
    protected void onPostExecute(ArrayList<Icon> icons) {
        if(mCallBacks!=null)
            mCallBacks.onPostExecute(icons);
    }
}
