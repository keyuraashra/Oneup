package com.oneup.app.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by Scorpion on 12-02-2016.
 */
public class ProgressDialogFragment extends DialogFragment {
    public static ProgressDialogFragment newInstance(int msgId) {
        ProgressDialogFragment progressDialogFragment = new ProgressDialogFragment();
        Bundle args = new Bundle();
        args.putInt("msgId", msgId);
        progressDialogFragment.setArguments(args);
        return progressDialogFragment;
    }

    public ProgressDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int msgId = getArguments().getInt("msgId");
        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage(getActivity().getResources().getString(msgId));
        return dialog;
    }
}