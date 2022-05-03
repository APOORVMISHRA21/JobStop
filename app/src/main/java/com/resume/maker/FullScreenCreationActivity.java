package com.resume.maker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.example.demo.R;
import com.resume.maker.utils.ZoomLayout;

import java.io.File;
import java.io.FileOutputStream;


public class FullScreenCreationActivity extends AppCompatActivity {
    LinearLayout back;
    ImageView imgshare;
    ImageView imgshowcreation;
    Uri mImageUri;
    RelativeLayout layoutrelativemain;


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_full_screen_creation);


        AdAdmob adAdmob = new AdAdmob(this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.bannerAd), this);
        adAdmob.FullscreenAd(this);

        initToolbar();
        this.imgshowcreation = (ImageView) findViewById(R.id.imgshowcreation);
        this.layoutrelativemain = (RelativeLayout) findViewById(R.id.layoutrelativemain);
        this.layoutrelativemain.setClipToOutline(true);
        this.imgshare = (ImageView) findViewById(R.id.imgshare);
        this.mImageUri = Uri.parse(getIntent().getStringExtra("creationuri"));
        this.imgshowcreation.setImageURI(this.mImageUri);
        this.imgshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FullScreenCreationActivity.this.shareImg();
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setHomeButtonEnabled(true);
        toolbar.setNavigationIcon(R.drawable.back);
        this.back = (LinearLayout) findViewById(R.id.back);
        this.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FullScreenCreationActivity.this.onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fullscreencreation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 16908332) {
            onBackPressed();
            finish();
            return true;
        } else if (itemId != R.id.menu_shareimg) {
            return super.onOptionsItemSelected(menuItem);
        } else {
            shareImg();
            return true;
        }
    }


    public void shareImg() {
        Bitmap bitmapFromView = getBitmapFromView(this.imgshowcreation);
        try {
            File file = new File(getExternalCacheDir(), "resume_maker.png");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmapFromView.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            file.setReadable(true, false);
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setFlags(268435456);
            intent.putExtra("android.intent.extra.STREAM", FileProvider.getUriForFile(this, getPackageName(), file));
            intent.putExtra("android.intent.extra.TEXT", "Shared via " + getString(R.string.app_name));
            intent.setType("image/png");
            startActivity(Intent.createChooser(intent, "Share image via"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private Bitmap getBitmapFromView(View view) {
        Bitmap createBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Drawable background = view.getBackground();
        if (background != null) {
            background.draw(canvas);
        } else {
            canvas.drawColor(-1);
        }
        view.draw(canvas);
        return createBitmap;
    }


}
