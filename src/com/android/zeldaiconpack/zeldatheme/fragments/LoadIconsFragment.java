package com.android.zeldaiconpack.zeldatheme.fragments;
import com.android.zeldaiconpack.zeldatheme.tasks.CopyResTask;

/**
 * Created by Naveed
 * Date: 12/15/13
 * Time: 7:24 PM
 * Contact: nak411@gmail.com
 *
 * Concrete class for loading icons
 */
public class LoadIconsFragment extends TaskFragment {

    @Override
    public CopyResTask getTask() {
        return new CopyResTask(mCallBacks, this);
    }
}
