package com.android.zeldaiconpack.zeldatheme.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.zeldaiconpack.zeldatheme.R;


/**
 * Created by Naveed on 12/1/13.
 * nak411@gmail.com
 */
public class IconAdapter extends BaseAdapter {

    private Context mContext;

    public IconAdapter(Context c) {
        mContext = c;
    }

    @Override
    public int getCount() {
        return mIcons.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //Create a new image view for each item referenced by
    //the adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView ivIcon;

        if(convertView == null){
            ivIcon = new ImageView(mContext);
            ivIcon.setLayoutParams(new GridView.LayoutParams(85, 85));
            ivIcon.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ivIcon.setPadding(8, 8, 8, 8);
        }else{
            ivIcon = (ImageView) convertView;
        }

        ivIcon.setImageResource(mIcons[position]);

        return ivIcon;
    }

    private Integer[] mIcons = {
            R.drawable.angrybirds,
            R.drawable.browser,
            R.drawable.com_android_browser_browseractivity,
            R.drawable.com_android_contacts_activities_dialtactsactivity,
            R.drawable.com_android_contacts_activities_peopleactivity,
            R.drawable.com_estrongs_android_pop_view_fileexploreractivity,
            R.drawable.com_android_gallery3d_app_gallery,
            R.drawable.com_android_providers_downloads_ui_downloadlist,
            R.drawable.com_instagram_android_activity_maintabactivity
    };
}
