package com.android.zeldaiconpack.zeldatheme;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.zeldaiconpack.zeldatheme.R;

/**
 * Created by Naveed on 11/30/13.
 */
public class IconPickerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon_picker_layout);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Used up navigation on devices using 4.0
     * @param item
     * @return
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
}
