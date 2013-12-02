package com.android.zeldaiconpack.zeldatheme;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.zeldaiconpack.zeldatheme.adapters.IconAdapter;
import com.zeldaiconpack.zeldatheme.R;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by Naveed on 11/30/13.
 * nak411@gmail.com
 */
public class IconPickerActivity extends Activity implements AdapterView.OnItemClickListener {

    private ArrayList<Integer> mIcons;
    private boolean mLoading;
    private AsyncTask<Void, Void, ArrayList<Integer>> task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon_picker_layout);
        //Not supported by all versions of android
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        initialize();
    }

    private void initialize() {
        GridView gvIcons = (GridView) findViewById(R.id.gvIcons);
        mIcons = new ArrayList<Integer>();
        retrieveResourceIds();
        gvIcons.setAdapter(new IconAdapter(this, mIcons));
        gvIcons.setOnItemClickListener(this);
    }

    private void retrieveResourceIds(){
        task = new AsyncTask<Void, Void, ArrayList<Integer>>() {
            @Override
            protected void onPreExecute() {
                mLoading = true;
                super.onPreExecute();
                //do ui stuff here
            }

            @Override
            protected ArrayList<Integer> doInBackground(Void... params) {
                final Class<R.drawable> c = R.drawable.class;
                final Field[] fields = c.getDeclaredFields();
                ArrayList<Integer> icons = new ArrayList<Integer>();
                for (Field field : fields) {
                    final int resourceId;
                    try {
                        resourceId = field.getInt(null);
                        String name = getResources().getResourceEntryName(resourceId);
                        if (name.startsWith("com_"))
                            icons.add(resourceId);
                    } catch (Exception e) {
                        continue;
                    }
                    if(isCancelled())
                        break;
                }
                return icons;
            }

            @Override
            protected void onPostExecute(ArrayList<Integer> iconIds) {
                super.onPostExecute(iconIds);
                //Do ui stuff here
                mIcons.addAll(iconIds);
                mLoading = false;
            }
        };
        task.execute();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mLoading){
            //Allow interruption
            task.cancel(true);
        }
        task = null;
    }

    /**
     * Used up navigation on devices using 4.0
     *
     * @param item the item selected
     * @return true on false depending on action completion
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(IconPickerActivity.this, "" + position, Toast.LENGTH_SHORT).show();
    }
}
