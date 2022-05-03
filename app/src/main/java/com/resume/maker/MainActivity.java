package com.resume.maker;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.demo.R;



public class MainActivity extends AppCompatActivity {
    long TIME = 1000;
    LinearLayout back;
    ImageView imgacademic;
    ImageView imgexperience;
    ImageView imghobbies;
    ImageView imglanguages;
    ImageView imgpersonalinfo;
    ImageView imgreference;
    ImageView imgresumesample;
    ImageView imgskills;


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        initToolbar();
        this.imgpersonalinfo = (ImageView) findViewById(R.id.imgpersonalinfo);
        this.imgacademic = (ImageView) findViewById(R.id.imgacademic);
        this.imgexperience = (ImageView) findViewById(R.id.imgexperience);
        this.imgreference = (ImageView) findViewById(R.id.imgreference);
        this.imgskills = (ImageView) findViewById(R.id.imgskills);
        this.imglanguages = (ImageView) findViewById(R.id.imglanguages);
        this.imghobbies = (ImageView) findViewById(R.id.imghobbies);
        this.imgresumesample = (ImageView) findViewById(R.id.imgresumesample);
        final Animation loadAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.unclick_btn_zoom);
        this.imgpersonalinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                view.startAnimation(loadAnimation);
                MainActivity.this.startActivity(new Intent(MainActivity.this, PersonalinfoActivity.class));
                view.setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.setEnabled(true);
                    }
                }, MainActivity.this.TIME);
            }
        });
        this.imgacademic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                view.startAnimation(loadAnimation);
                MainActivity.this.startActivity(new Intent(MainActivity.this, AcademicActivity.class));
                view.setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.setEnabled(true);
                    }
                }, MainActivity.this.TIME);
            }
        });
        this.imgexperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                view.startAnimation(loadAnimation);
                MainActivity.this.startActivity(new Intent(MainActivity.this, ExperienceActivity.class));
                view.setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.setEnabled(true);
                    }
                }, MainActivity.this.TIME);
            }
        });
        this.imgreference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                view.startAnimation(loadAnimation);
                MainActivity.this.startActivity(new Intent(MainActivity.this, ReferenceActivity.class));
                view.setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.setEnabled(true);
                    }
                }, MainActivity.this.TIME);
            }
        });
        this.imgskills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                view.startAnimation(loadAnimation);
                MainActivity.this.startActivity(new Intent(MainActivity.this, SkillsActivity.class));
                view.setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.setEnabled(true);
                    }
                }, MainActivity.this.TIME);
            }
        });
        this.imglanguages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                view.startAnimation(loadAnimation);
                MainActivity.this.startActivity(new Intent(MainActivity.this, LanguageActivity.class));
                view.setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.setEnabled(true);
                    }
                }, MainActivity.this.TIME);
            }
        });
        this.imghobbies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                view.startAnimation(loadAnimation);
                MainActivity.this.startActivity(new Intent(MainActivity.this, HobbiesActivity.class));
                view.setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.setEnabled(true);
                    }
                }, MainActivity.this.TIME);
            }
        });
        this.imgresumesample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                view.startAnimation(loadAnimation);
                MainActivity.this.startActivity(new Intent(MainActivity.this, ResumeSamplesActivity.class));
                view.setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.setEnabled(true);
                    }
                }, MainActivity.this.TIME);
            }
        });
    }

    @TargetApi(21)
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.back);
        this.back = (LinearLayout) findViewById(R.id.back);
        this.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.onBackPressed();
            }
        });
    }

    @Override
    @RequiresApi(api = 19)
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
    }
}
