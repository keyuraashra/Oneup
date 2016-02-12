package com.oneup.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

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

/**
 * Created by Scorpion on 11-02-2016.
 */
public class LoginActivity extends BaseActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private CallbackManager mCallbackManager;

    @Override
    String getActionTitle() {
        return getString(R.string.login);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);
        setActionBarTitle(getString(R.string.login));
    }

    @Override
    boolean isDisplayHomeAsUpEnabled() {
        return false;
    }

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
                        AlertDialogUtils.showToast(response.getError().getErrorMessage(), LoginActivity.this);
                    } else {
                        try {
                            Log.d(TAG, "Response = " + object.toString());
                            PreferenceUtils.putStringInPreferences(LoginActivity.this, object.getString("email"), Constants.EMAIL);
                            PreferenceUtils.putStringInPreferences(LoginActivity.this, object.getString("name"), Constants.NAME);
                            PreferenceUtils.putStringInPreferences(LoginActivity.this, "FACEBOOK", Constants.PASSWORD);

                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } catch (JSONException e) {
                            AlertDialogUtils.showToast(e.getMessage(), LoginActivity.this);
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
            AlertDialogUtils.showToast("Login cancelled by User.", LoginActivity.this);
        }

        @Override
        public void onError(FacebookException e) {
            Log.e(TAG, e.getMessage(), e);
            AlertDialogUtils.showToast(e.getMessage(), LoginActivity.this);
        }
    };
}