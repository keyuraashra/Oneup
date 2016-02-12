package com.oneup.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.oneup.app.constants.Constants;
import com.oneup.app.utils.AlertDialogUtils;
import com.oneup.app.utils.PreferenceUtils;
import com.oneup.app.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Scorpion on 11-02-2016.
 */
public class SignupActivity extends BaseActivity {
    private static final String TAG = SignupActivity.class.getSimpleName();
    private EditText mEdtName, mEdtEmail, mEdtPassword;
    private CallbackManager mCallbackManager;

    @Override
    String getActionTitle() {
        return getString(R.string.signup);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);
        setActionBarTitle(getString(R.string.signup));

        mEdtName = (EditText) findViewById(R.id.edtName);
        mEdtEmail = (EditText) findViewById(R.id.edtEmail);
        mEdtPassword = (EditText) findViewById(R.id.edtPassword);

        findViewById(R.id.btnLogin).setOnClickListener(loginClickListener);
        findViewById(R.id.btnFacebook).setOnClickListener(facebookClickListener);
    }

    @Override
    boolean isDisplayHomeAsUpEnabled() {
        return false;
    }

    private final View.OnClickListener loginClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String EMAIL_PATTERN =
                    "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

            Pattern pattern = Pattern.compile(EMAIL_PATTERN);
            Matcher matcher = pattern.matcher(mEdtEmail.getText().toString());
            if (mEdtName.getText().toString().length() > 0) {
                if (mEdtEmail.getText().toString().length() > 0 && matcher.matches()) {
                    if (mEdtPassword.getText().toString().length() > 6) {
                        PreferenceUtils.setIsRegistered(SignupActivity.this, true);
                        PreferenceUtils.putStringInPreferences(SignupActivity.this, mEdtName.getText().toString(), Constants.NAME);
                        PreferenceUtils.putStringInPreferences(SignupActivity.this, mEdtEmail.getText().toString(), Constants.EMAIL);
                        PreferenceUtils.putStringInPreferences(SignupActivity.this, mEdtPassword.getText().toString(), Constants.PASSWORD);

                        // start main activity
                        startActivity(new Intent(SignupActivity.this, MainActivity.class));
                        finish();
                    } else {
                        mEdtPassword.setError(getResources().getString(R.string.error_password));
                    }
                } else {
                    mEdtEmail.setError("Invalid email!");
                    mEdtEmail.requestFocus();
                }
            } else {
                mEdtName.setError("Please enter your name!");
                mEdtName.requestFocus();
            }
        }
    };

    private final View.OnClickListener facebookClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            initFacebook();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /**
     *
     */
    private void initFacebook() {
        if (Utils.checkInternetConnection(this)) {
            FacebookSdk.sdkInitialize(this);
            mCallbackManager = CallbackManager.Factory.create();
            try {
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList(new String[]{"public_profile", "email"}));
                LoginManager.getInstance().registerCallback(mCallbackManager, callback);
            } catch (Exception var2) {
                Log.e(TAG, var2.getMessage(), var2);
            }
        } else {
            AlertDialogUtils.showToast(getString(R.string.no_internet), this);
        }
    }

    /**
     * FaceBook Callback
     */
    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Log.d(TAG, "onSuccess");
            AccessToken accessToken = loginResult.getAccessToken();

            GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    if (response.getError() != null) {
                        Log.d(TAG, "Response = " + response.getError().toString());
                        AlertDialogUtils.showToast(response.getError().getErrorMessage(), SignupActivity.this);
                    } else {
                        try {
                            Log.d(TAG, "Response = " + object.toString());
                            PreferenceUtils.putStringInPreferences(SignupActivity.this, object.getString("email"), Constants.EMAIL);
                            PreferenceUtils.putStringInPreferences(SignupActivity.this, object.getString("name"), Constants.NAME);
                            PreferenceUtils.putStringInPreferences(SignupActivity.this, "FACEBOOK", Constants.PASSWORD);
                            startActivity(new Intent(SignupActivity.this, MainActivity.class));
                            finish();
                        } catch (JSONException e) {
                            AlertDialogUtils.showToast(e.getMessage(), SignupActivity.this);
                            Log.e(TAG, e.getMessage(), e);
                        }
                    }
                }
            });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender,location");
            request.setParameters(parameters);
            request.executeAsync();
        }

        @Override
        public void onCancel() {
            AlertDialogUtils.showToast("Login cancelled by User.", SignupActivity.this);
        }

        @Override
        public void onError(FacebookException e) {
            Log.e(TAG, e.getMessage(), e);
            AlertDialogUtils.showToast(e.getMessage(), SignupActivity.this);
        }
    };
}
