package com.android.zeldaiconpack.zeldatheme;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.zeldaiconpack.zeldatheme.adapters.IconAdapter;
import com.android.zeldaiconpack.zeldatheme.structures.Icon;
import com.zeldaiconpack.zeldatheme.R;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by Naveed on 11/30/13.
 * nak411@gmail.com
 */
public class IconPickerActivity extends Activity implements AdapterView.OnItemClickListener {

    private ArrayList<Icon> mIcons;
    private boolean mLoading;
    private AsyncTask<Void, Void, ArrayList<Icon>> task;
    private IconAdapter mAdapter;

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
        mIcons = new ArrayList<Icon>();
        mAdapter = new IconAdapter(this, mIcons);
        gvIcons.setAdapter(mAdapter);
        retrieveResourceIds();
        gvIcons.setOnItemClickListener(this);
    }

    /**
     * Retrieves the drawables from res folder
     */
    private void retrieveResourceIds() {

        task = new AsyncTask<Void, Void, ArrayList<Icon>>() {
            @Override
            protected void onPreExecute() {
                mLoading = true;
                super.onPreExecute();
                //do ui stuff here
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
                        String name = getResources().getResourceEntryName(resourceId);
                        //Find a resources that starts with com or matches the 2 word pattern
                        //Short circuit at "com_"
                        if (name.startsWith("com_") || name.matches("\\w+_\\w+"))
                            icons.add(new Icon(resourceId));
                    } catch (Exception e) {
                        continue;
                    }
                    if (isCancelled())
                        break;
                }
                return icons;
            }

            @Override
            protected void onPostExecute(ArrayList<Icon> icons) {
                super.onPostExecute(icons);
                //Do ui stuff here
                mIcons.addAll(icons);

                mLoading = false;
            }
        };
        task.execute();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mLoading) {
            //Allow interruption
            task.cancel(true);
        }
        task = null;
    }

    /**
     * Use up navigation on devices using 4.0
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
        //Toggle selection
        Icon icon = mIcons.get(position);
        if(!icon.isSelected)
        {
            view.setBackgroundColor(Color.CYAN);
            icon.isSelected = true;
        }else{
            view.setBackgroundColor(Color.TRANSPARENT);
            icon.isSelected = false;
        }
    }
}
