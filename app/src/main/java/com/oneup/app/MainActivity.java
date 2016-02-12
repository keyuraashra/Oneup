package com.oneup.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.oneup.app.adapter.GridViewAdapter;
import com.oneup.app.utils.PreferenceUtils;

public class MainActivity extends BaseActivity {
    private GridView mGridViewChooseLook;

    @Override
    String getActionTitle() {
        return "Products";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);
        setActionBarTitle("Products");

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
        mGridViewChooseLook = (GridView) findViewById(R.id.gridView);
        mGridViewChooseLook.setAdapter(new GridViewAdapter(this));
        mGridViewChooseLook.setOnItemClickListener(onItemClickListener);
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
                PreferenceUtils.clearPreferences(MainActivity.this);
                startActivity(new Intent(MainActivity.this, SplashActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    boolean isDisplayHomeAsUpEnabled() {
        return false;
    }

    /**
     *
     */
    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent i = new Intent(MainActivity.this, CheckoutActivity.class);
            // Pass image index
            i.putExtra("id", position);
            startActivity(i);
        }
    };
}