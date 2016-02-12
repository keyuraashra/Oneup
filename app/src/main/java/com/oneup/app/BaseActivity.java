package com.oneup.app;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.oneup.app.R;

/**
 * Created by riontech1 on 26/1/16.
 */
abstract class BaseActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ActionBar mActionBar;

    public void setActionBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(isDisplayHomeAsUpEnabled());
        mActionBar.setTitle(getActionTitle());
        mActionBar.setHomeButtonEnabled(true);
        mToolbar = toolbar;
    }

    abstract String getActionTitle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    abstract boolean isDisplayHomeAsUpEnabled();

    public void setActionBarTitle(final String title) {

        if (title != null) {
            getSupportActionBar().setTitle(title);
        } else {
            getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        }
    }

    /**
     *
     */
    public void setLogo(int resId) {
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(ContextCompat.getDrawable(getApplicationContext(), resId));
    }

    /**
     * @param resId
     */
    public void setHomeIndicator(int resId) {
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(getApplicationContext(), resId));
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }
}