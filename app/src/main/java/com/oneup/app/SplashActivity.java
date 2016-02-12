package com.oneup.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.oneup.app.utils.PreferenceUtils;

/**
 * Created by Scorpion on 11-02-2016.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        if (PreferenceUtils.isRegistered(this)) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
        findViewById(R.id.imgGo).setOnClickListener(onClickListener);
    }

    /**
     *
     */
    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(SplashActivity.this, SignupActivity.class));
            finish();
        }
    };
}
