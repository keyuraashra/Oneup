package com.oneup.app.adapter;


import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.oneup.app.R;
import com.oneup.app.constants.Constants;
import com.oneup.app.utils.ImageUtils;

/**
 * Created by Scorpion on 11/02/16.
 */
public class GridViewAdapter extends BaseAdapter {

    public static final String TAG = GridViewAdapter.class.getSimpleName();

    private Context mContext;

    // Constructor
    public GridViewAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return Constants.ImageIds.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        GridHolder holder;

        if (convertView == null) {
            holder = new GridHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_image_view, null);
            holder.mImgNotLookView = (ImageView) convertView.
                    findViewById(R.id.imgGridItem);
            convertView.setTag(holder);
        } else {
            holder = (GridHolder) convertView.getTag();
        }
        Resources res = mContext.getResources();
        TypedArray ids = res.obtainTypedArray(R.array.dress_images);
        int id = ids.getResourceId(position, 0);// Constants.ImageIds[position];
        ImageUtils.getImageLoader(mContext).displayImage("drawable://"
                + id, holder.mImgNotLookView, ImageUtils.getDisplayOption());

        return convertView;
    }

    static class GridHolder {
        ImageView mImgNotLookView;
    }
}