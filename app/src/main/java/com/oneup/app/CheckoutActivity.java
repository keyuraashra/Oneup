package com.oneup.app;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.oneup.app.constants.Constants;
import com.oneup.app.fragment.ErrorDialogFragment;
import com.oneup.app.fragment.ProgressDialogFragment;
import com.oneup.app.utils.DatePickerDialogFragment;
import com.oneup.app.utils.DateTimeUtils;
import com.oneup.app.utils.PreferenceUtils;
import com.oneup.app.utils.ValidationUtils;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import java.text.ParseException;
import java.util.Calendar;


/**
 * Created by Scorpion on 11-02-2016.
 */
public class CheckoutActivity extends BaseActivity {

    private static final String TAG = CheckoutActivity.class.getSimpleName();
    private EditText mEdtFirstName, mEdtLastName, mEdtCardNumber, mEdtCVV, mEdtExpiry, mEdtAdd1, mEdtAdd2;
    private Calendar mFromDate;
    private ProgressDialogFragment progressFragment;

    // stripe key
    private static final String PUBLISHABLE_KEY = "pk_test_PDP5A3qMiLFFYulomLiQF8K4 ";

    @Override
    String getActionTitle() {
        return getString(R.string.checkout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        mFromDate = Calendar.getInstance();
        progressFragment = ProgressDialogFragment.newInstance(R.string.progressMessage);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);
        setActionBarTitle(getString(R.string.checkout));

        mEdtFirstName = (EditText) findViewById(R.id.edtFirstName);
        mEdtLastName = (EditText) findViewById(R.id.edtLastName);
        mEdtCardNumber = (EditText) findViewById(R.id.edtCardNumber);
        mEdtCVV = (EditText) findViewById(R.id.edtCVV);
        mEdtExpiry = (EditText) findViewById(R.id.edtExpiry);
        mEdtAdd1 = (EditText) findViewById(R.id.edtAdd1);
        mEdtAdd2 = (EditText) findViewById(R.id.edtAdd2);

        findViewById(R.id.rlSuccessDialog).setVisibility(View.GONE);

        String cardExpiry = PreferenceUtils.getStringFromPreferences(this, "", Constants.CARD_EXPIRY);
        if (cardExpiry != null && cardExpiry.length() > 0) {
            try {
                mFromDate.setTime(DateTimeUtils.getDateFormat().parse(cardExpiry));
            } catch (ParseException e) {
                Log.e(TAG, e.getMessage(), e);
            }

            mEdtExpiry.setText(DateTimeUtils.getCardExpiryFormat().format(mFromDate.getTime()));
        } else {
            mEdtCardNumber.setText(cardExpiry);
        }

        mEdtFirstName.setText(PreferenceUtils.getStringFromPreferences(this, "", Constants.CARD_FIRST_NAME));
        mEdtLastName.setText(PreferenceUtils.getStringFromPreferences(this, "", Constants.CARD_LAST_NAME));
        mEdtCardNumber.setText(PreferenceUtils.getStringFromPreferences(this, "", Constants.CARD_NUMBER));
        mEdtAdd1.setText(PreferenceUtils.getStringFromPreferences(this, "", Constants.CARD_ADD_1));
        mEdtAdd2.setText(PreferenceUtils.getStringFromPreferences(this, "", Constants.CARD_ADD_2));

        mEdtExpiry.setOnFocusChangeListener(onFocusChangeListener);
        findViewById(R.id.btnCheckout).setOnClickListener(checkoutListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_logunt:
                PreferenceUtils.clearPreferences(CheckoutActivity.this);
                startActivity(new Intent(CheckoutActivity.this, SplashActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private final View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                showDatePicker();
            }
        }
    };

    @Override
    boolean isDisplayHomeAsUpEnabled() {
        return true;
    }

    private final View.OnClickListener checkoutListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ValidationUtils.validate(CheckoutActivity.this)) {
                saveCreditCard();
            }
        }
    };

    private void showDatePicker() {
        final DatePickerDialogFragment date = new DatePickerDialogFragment();
        /**
         * Set Up Current Date Into dialog
         */
        final Calendar calender = Calendar.getInstance();
        final Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);

        date.setCallBack(onDateSetListener);
        date.show(getSupportFragmentManager(), "Date Picker");
    }

    /**
     * From Date set
     */
    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(final DatePicker view, final int year,
                              final int monthOfYear, final int dayOfMonth) {
            String fromDate = year + "-"
                    + String.format("%02d", (monthOfYear + 1)) + "-"
                    + String.format("%02d", dayOfMonth);
            try {
                mFromDate.setTime(DateTimeUtils.getDateFormat().parse(fromDate));
            } catch (ParseException e) {
                Log.e(TAG, e.getMessage(), e);
            }

            mEdtExpiry.setText(DateTimeUtils.getCardExpiryFormat().format(mFromDate.getTime()));
            mEdtAdd1.requestFocus();
        }
    };

    /**
     *
     */
    public void saveCreditCard() {
        int expiryMonth = mFromDate.get(Calendar.MONTH) + 1;
        Log.d(TAG, "Expiry month = " + expiryMonth);
        Log.d(TAG, "Expiry year = " + mFromDate.get(Calendar.YEAR));
        Card card = new Card(
                mEdtCardNumber.getText().toString(),
                expiryMonth,
                mFromDate.get(Calendar.YEAR),
                mEdtCVV.getText().toString());
        card.setAddressLine1(mEdtAdd1.getText().toString());
        card.setAddressLine2(mEdtAdd2.getText().toString());
        card.setCurrency("usd");

        boolean validation = card.validateCard();
        if (validation) {
            startProgress();
            new Stripe().createToken(
                    card,
                    PUBLISHABLE_KEY,
                    new TokenCallback() {
                        public void onSuccess(Token token) {
                            saveData();
                            finishProgress();

                            findViewById(R.id.rlSuccessDialog).setVisibility(View.VISIBLE);
                            Animation anim = AnimationUtils.loadAnimation(CheckoutActivity.this, R.anim.zoom_out);
                            anim.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    finish();
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            ImageView imgSuccess = (ImageView) findViewById(R.id.imgSuccess);
                            imgSuccess.setAnimation(anim);
                        }

                        public void onError(Exception error) {
                            handleError(error.getLocalizedMessage());
                            finishProgress();
                        }
                    });
        } else if (!card.validateNumber()) {
            handleError("The card number that you entered is invalid");
        } else if (!card.validateExpiryDate()) {
            handleError("The expiration date that you entered is invalid");
        } else if (!card.validateCVC()) {
            handleError("The CVC code that you entered is invalid");
        } else {
            handleError("The card details that you entered are invalid");
        }
    }

    private void startProgress() {
        progressFragment.show(getSupportFragmentManager(), "progress");
    }

    private void finishProgress() {
        progressFragment.dismiss();
    }

    private void handleError(String error) {
        DialogFragment fragment = ErrorDialogFragment.newInstance(R.string.validationErrors, error);
        fragment.show(getSupportFragmentManager(), "error");
    }

    /**
     *
     */
    private void saveData() {
        PreferenceUtils.putStringInPreferences(CheckoutActivity.this, mEdtFirstName.getText().toString(), Constants.CARD_FIRST_NAME);
        PreferenceUtils.putStringInPreferences(CheckoutActivity.this, mEdtLastName.getText().toString(), Constants.CARD_LAST_NAME);
        PreferenceUtils.putStringInPreferences(CheckoutActivity.this, mEdtCardNumber.getText().toString(), Constants.CARD_NUMBER);
        PreferenceUtils.putStringInPreferences(CheckoutActivity.this, DateTimeUtils.getDateFormat().format(mFromDate.getTime()), Constants.CARD_EXPIRY);
        PreferenceUtils.putStringInPreferences(CheckoutActivity.this, mEdtAdd1.getText().toString(), Constants.CARD_ADD_1);
        PreferenceUtils.putStringInPreferences(CheckoutActivity.this, mEdtAdd2.getText().toString(), Constants.CARD_ADD_2);
    }
}