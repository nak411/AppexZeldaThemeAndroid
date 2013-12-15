package com.android.zeldaiconpack.zeldatheme.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.android.zeldaiconpack.zeldatheme.structures.Icon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;

/**
 * Created by Naveed on 12/8/13.
 * nak411@gmail.com
 */
public class ResCopier {

    private HashSet<Icon> mSelectedIcons;
    private Context mContext;

    public ResCopier(HashSet<Icon> mSelectedIcons, Context mContext)
    {
        this.mSelectedIcons = mSelectedIcons;
        this.mContext = mContext;
    }

    public void copyIcons(){

        String dirPath = Environment.getExternalStorageDirectory().toString();

        for(Icon icon : mSelectedIcons)
        {
           Bitmap image =  BitmapFactory.decodeResource(mContext.getResources(), icon.resId);
            writeResource(image, dirPath);

        }
    }

    /**
     * Copies bytes of an image to the sd card
     * Todo generate file names
     * @param image
     * @param path
     */
    private void writeResource(Bitmap image, String path) {
        try
        {
            File f = new File(path, "a");
            OutputStream out = new FileOutputStream(f);
            image.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        }
        catch (IOException e)
        {

        }
    }
}
