package com.android.zeldaiconpack.zeldatheme.tasks;

import android.app.Fragment;
import com.android.zeldaiconpack.zeldatheme.interfaces.TaskCallBacks;
import com.android.zeldaiconpack.zeldatheme.structures.Icon;
import com.zeldaiconpack.zeldatheme.R;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by Naveed on 12/8/13.
 * nak411@gmail.com
 *
 * Concrete class for loading the resources from res folder
 */
public class CopyResTask extends LoadContentTask<Void, Integer, ArrayList<Icon>> {

    private Fragment mContext;

    public CopyResTask(TaskCallBacks mCallBacks, Fragment context) {
        super(mCallBacks);
        this.mContext = context;
    }

    /**
     * Loads the ids of Icons from resources
     * @return array list of loading icons
     */
    @Override
    public ArrayList<Icon> loadData() {
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
}
