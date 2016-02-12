package com.oneup.app.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.oneup.app.R;

/**
 * Created by Scorpion on 11/02/16.
 */
public class AlertDialogUtils {

    /**
     * Custom Toast
     *
     * @param message
     * @param context
     */
    public static void showToast(final String message, final Context context) {
        final LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.toast_layout, null);

        final TextView text = (TextView) layout
                .findViewById(R.id.tv_toast_message);
        text.setText(message);
        final Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}