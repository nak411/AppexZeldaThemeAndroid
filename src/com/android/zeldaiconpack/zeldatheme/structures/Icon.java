package com.android.zeldaiconpack.zeldatheme.structures;

/**
 * Created by Naveed on 12/6/13.
 * nak411@gmail.com
 */
public class Icon {

    public Integer resId;
    public boolean isSelected;

    public Icon(Integer resId, String resName)
    {
        this.resId = resId;
        this.isSelected = false;
    }
}
