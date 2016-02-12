package com.oneup.app.utils;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.oneup.app.R;

import java.util.List;


/**
 * Created by Scorpion on 11-09-2015.
 */
public class ValidationUtils {
    private static final String TAG = ValidationUtils.class.getSimpleName();

    /**
     * @param activity
     * @return
     */
    public static boolean validate(Activity activity) {
        boolean result = true;
        ViewGroup viewGroup = (ViewGroup) activity.findViewById(R.id.rlInputs);
        List<View> editTextList = viewGroup.getFocusables(View.FOCUS_FORWARD);

        for (View editText : editTextList) {
            final EditText validateEditText = (EditText) editText;
            switch (editText.getId()) {
                case R.id.edtFirstName:
                case R.id.edtLastName:
                case R.id.edtCardNumber:
                case R.id.edtCVV:
                case R.id.edtExpiry:
                    if (validateEditText.getText().toString() == null ||
                            validateEditText.getText().toString().length() <= 0) {
                        validateEditText.setError(validateEditText.getHint().toString() + activity.getResources().getString(R.string.hint_error));
                        validateEditText.requestFocus();
                        result = false;
                    } else {
                        validateEditText.setError(null);
                        result = true;
                    }
                    break;
            }

            if (!result)
                break;
        }
        return result;
    }
}
