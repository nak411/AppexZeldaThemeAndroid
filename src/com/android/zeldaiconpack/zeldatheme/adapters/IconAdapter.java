package com.android.zeldaiconpack.zeldatheme.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.android.zeldaiconpack.zeldatheme.structures.Icon;

import java.util.ArrayList;

/**
 * Created by Naveed on 12/1/13.
 * nak411@gmail.com
 */
public class IconAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Icon> mIcons;

    public IconAdapter(Context c, ArrayList<Icon> iconIds) {
        mContext = c;
        mIcons = iconIds;
    }

    @Override
    public int getCount() {
        return mIcons.size();
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
        ivIcon.setImageResource(mIcons.get(position).resId);
        return ivIcon;
    }
}
