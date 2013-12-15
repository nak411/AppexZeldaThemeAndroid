package com.android.zeldaiconpack.zeldatheme;

import android.app.Activity;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.zeldaiconpack.zeldatheme.adapters.IconAdapter;
import com.android.zeldaiconpack.zeldatheme.fragments.TaskFragment;
import com.android.zeldaiconpack.zeldatheme.interfaces.TaskCallBacks;
import com.android.zeldaiconpack.zeldatheme.structures.Icon;
import com.zeldaiconpack.zeldatheme.R;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Naveed on 11/30/13.
 * nak411@gmail.com
 */
public class IconPickerActivity extends Activity implements
        AdapterView.OnItemClickListener, TaskCallBacks {

    private static String TAG = "IconPicker";
    private TaskFragment mTaskFragment;
    private TextView tvItemCount;

    private ArrayList<Icon> mIcons;
    private HashSet<Icon> mSelected;
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
    public boolean onCreateOptionsMenu(Menu menu)
    {
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
            else
            {
                Log.e(TAG, "View was null in itemSave.getActionView()");
            }
        }
        else
        {
            Log.e(TAG, "Menu itemSave was null");
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void initialize() {
        GridView gvIcons = (GridView) findViewById(R.id.gvIcons);

        if (mIcons == null)
            mIcons = new ArrayList<Icon>();

        if (mAdapter == null) {
            mAdapter = new IconAdapter(this, mIcons);
            mSelected = new HashSet<Icon>();
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
        FragmentManager fm = getFragmentManager();
        mTaskFragment = (TaskFragment) fm.findFragmentByTag("task");

        if(mTaskFragment ==null){
            mTaskFragment = new TaskFragment();
            fm.beginTransaction().add(mTaskFragment, "task").commit();
        }
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


    private void updateSelected(Icon icon) {
        tvItemCount.setText(Integer.toString(counter));

        if (icon.isSelected)
            mSelected.add(icon);
        else
            mSelected.remove(icon);
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
        updateSelected(icon);
    }

    @Override
    public void onPreExecute() {
        mLoading = true;

    }

    @Override
    public void onProgressUpdate(int percent) {

    }

    @Override
    public void onCancelled() {

    }

    @Override
    public void onPostExecute(Object result) {
        if(result.getClass() == ArrayList.class){
            ArrayList genArray = (ArrayList) result;
            for(Object obj : genArray){
                if(obj instanceof Icon){
                    mIcons.add((Icon) obj);
                }
            }
            mAdapter.notifyDataSetChanged();
        }
        mLoading = false;

    }
}
