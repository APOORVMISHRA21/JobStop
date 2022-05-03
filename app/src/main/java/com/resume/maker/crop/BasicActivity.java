package com.resume.maker.crop;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.demo.R;
import com.resume.maker.AboutmeActivity;


public class BasicActivity extends AppCompatActivity {
    private static final String TAG = "BasicActivity";

    public static Intent createIntent(Activity activity) {
        return new Intent(activity, BasicActivity.class);
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_basic);
        if (bundle == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, BasicFragment.newInstance()).commit();
        }
        initToolbar();
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setHomeButtonEnabled(true);
        toolbar.setNavigationIcon(R.drawable.back);
    }

    public void startResultActivity(Uri uri) {
        if (!isFinishing()) {
            Intent intent = new Intent();
            intent.putExtra("imageUri2", uri.toString());
            intent.putExtra(AboutmeActivity.EXTRA_PROFILE_ID, uri.toString());
            setResult(-1, intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
