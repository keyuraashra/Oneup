package com.oneup.app.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by Scorpion on 11/02/16.
 */
public class ImageUtils {
    public static DisplayImageOptions displayOptions;
    private static ImageLoader imageLoader;

    public static ImageLoader getImageLoader(Context context) {
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
                context).defaultDisplayImageOptions(getDisplayOption());
        if (imageLoader == null) {
            imageLoader = ImageLoader.getInstance();
            imageLoader.init(builder.build());
        }
        return imageLoader;
    }

    @SuppressWarnings("deprecation")
    public static DisplayImageOptions getDisplayOption() {
        if (displayOptions == null)
            displayOptions = new DisplayImageOptions.Builder()
                    .cacheOnDisc(true)
                    .cacheInMemory(true)
                    .resetViewBeforeLoading()
                            //.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                    .bitmapConfig(Bitmap.Config.RGB_565).build();
        return displayOptions;
    }
}
