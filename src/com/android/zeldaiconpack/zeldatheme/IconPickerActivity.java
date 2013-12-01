package com.android.zeldaiconpack.zeldatheme;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.zeldaiconpack.zeldatheme.adapters.IconAdapter;
import com.zeldaiconpack.zeldatheme.R;

/**
 * Created by Naveed on 11/30/13.
 * nak411@gmail.com
 */
public class IconPickerActivity extends Activity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon_picker_layout);
        //Not supported by all versions of android
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initialize();
    }

    private void initialize() {
        GridView gvIcons = (GridView) findViewById(R.id.gvIcons);
        gvIcons.setAdapter(new IconAdapter(this));

        gvIcons.setOnItemClickListener(this);
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
