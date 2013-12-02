package com.android.zeldaiconpack.zeldatheme;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
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


        final Class<R.drawable> c = R.drawable.class;
        final Field[] fields = c.getDeclaredFields();
        ArrayList<Integer> icons = new ArrayList<Integer>();

        for (int i = 0, max = fields.length; i < max; i++) {
            final int resourceId;
            try {
                resourceId = fields[i].getInt(null);
                String name = getResources().getResourceEntryName(resourceId);
                Log.d("Resources Zelda", name);
                if (name.startsWith("com_"))
                    icons.add(resourceId);
            } catch (Exception e) {
                continue;
            }
    /* make use of resourceId for accessing Drawables here */
        }
        gvIcons.setAdapter(new IconAdapter(this, icons));

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
