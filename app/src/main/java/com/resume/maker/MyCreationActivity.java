package com.resume.maker;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;
import com.resume.maker.adapters.MyCreationAdapter;

import java.io.File;


public class MyCreationActivity extends AppCompatActivity {
    LinearLayout back;
    LinearLayout layout_toolbar;
    MyCreationAdapter myImageAdapter;
    RecyclerView rcmycreation;


    @Override

    public void onCreate(Bundle bundle) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + getString(R.string.app_name) + File.separator);
        File[] listFiles = file.listFiles();
        super.onCreate(bundle);
        try {
            if (!file.exists() || listFiles.length <= 0) {
                setContentView(R.layout.no_media);
                initToolbar();
                return;
            }
            requestWindowFeature(1);
            setContentView(R.layout.activity_my_creation);

            AdAdmob adAdmob = new AdAdmob(this);
            adAdmob.BannerAd((RelativeLayout) findViewById(R.id.bannerAd), this);
            adAdmob.FullscreenAd(this);



            initToolbar();
            this.rcmycreation = (RecyclerView) findViewById(R.id.rcmycreation);
            this.rcmycreation.setLayoutManager(new GridLayoutManager(this, 2));
            this.myImageAdapter = new MyCreationAdapter(this, this);
            this.rcmycreation.setAdapter(this.myImageAdapter);
            for (File file2 : file.listFiles()) {
                this.myImageAdapter.add(file2.getAbsolutePath());
            }
        } catch (Exception unused) {
            setContentView(R.layout.no_media);
            initToolbar();
        }
    }

    private void initToolbar() {
        Window window = getWindow();
        window.clearFlags(67108864);
        window.addFlags(Integer.MIN_VALUE);
        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
        this.back = (LinearLayout) findViewById(R.id.back);
        this.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyCreationActivity.this.onBackPressed();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
