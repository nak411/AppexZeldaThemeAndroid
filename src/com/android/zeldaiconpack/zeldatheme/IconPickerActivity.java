package com.android.zeldaiconpack.zeldatheme;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.zeldaiconpack.zeldatheme.adapters.IconAdapter;
import com.android.zeldaiconpack.zeldatheme.structures.Icon;
import com.zeldaiconpack.zeldatheme.R;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by Naveed on 11/30/13.
 * nak411@gmail.com
 */
public class IconPickerActivity extends Activity implements
        AdapterView.OnItemClickListener {

    private TextView tvItemCount;

    private ArrayList<Icon> mIcons;
    private boolean mLoading;
    private AsyncTask<Void, Void, ArrayList<Icon>> task;
    private IconAdapter mAdapter;

    private int counter;

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

    /**
     * This gets called after onStart
     *
     * @param menu the menu to add the custom view to
     * @return success/failure
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu items for use in action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.icon_picker_activity_actions, menu);
        MenuItem itemSave = menu.findItem(R.id.btnSave);

        if (itemSave != null) {
            View v = itemSave.getActionView();
            if (v != null) {

                tvItemCount = (TextView) v.findViewById(R.id.tvCount);

                ImageView ivSave = (ImageView) v.findViewById(R.id.ivSave);

                //Have to set this listener here
                //The id changes for some reason if the
                //activity implements on click
                ivSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Save Icons
                    }
                });
            }
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void initialize() {
        GridView gvIcons = (GridView) findViewById(R.id.gvIcons);

        if (mIcons == null)
            mIcons = new ArrayList<Icon>();

        if (mAdapter == null) {
            mAdapter = new IconAdapter(this, mIcons);
            counter = 0;
            gvIcons.setAdapter(mAdapter);
            retrieveResourceIds();
        } else {
            //Activity is being restored
            gvIcons.setAdapter(mAdapter);
        }

        gvIcons.setOnItemClickListener(this);
        // ivSave.setOnClickListener(this);
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
                mAdapter.notifyDataSetChanged();
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
            case R.id.btnSave:
                //Save the icons here
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void updateCount() {
        tvItemCount.setText(Integer.toString(counter));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toggle selection
        Icon icon = mIcons.get(position);
        if (!icon.isSelected) {
            view.setBackgroundColor(Color.CYAN);
            icon.isSelected = true;
            counter++;
        } else {
            view.setBackgroundColor(Color.TRANSPARENT);
            icon.isSelected = false;
            counter--;
        }
        updateCount();
    }
}
