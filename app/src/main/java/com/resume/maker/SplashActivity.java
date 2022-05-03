package com.resume.maker;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.demo.R;
import com.resume.maker.SharedPreferences;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;



public class SplashActivity extends AppCompatActivity {

    InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        AdRequest adRequest = new AdRequest.Builder().build();


        InterstitialAd.load(this,getString(R.string.admob_inter_start), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
//                        Log.i(TAG, "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i("TAG", loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });

        SharedPreferences sharedPref=new SharedPreferences(this);
        sharedPref.setShown(false);
        new Handler().postDelayed(() -> {
//            setA();
            AddManageApplication addManageApplication= (AddManageApplication) getApplication();
            addManageApplication.showAdIfAvailable(getParent(), () -> {
                if(sharedPref.getShown()){
                    updateUI();
                    return;
                }else {

                    if (mInterstitialAd != null) {
                        mInterstitialAd.show(SplashActivity.this);
                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                updateUI();
                                // Called when fullscreen content is dismissed.
                                Log.d("TAG", "The ad was dismissed.");
                            }
                        });
                    } else {
                        updateUI();
                    }
                }
            });


        }, 5000);
    }

    public void setA(){

        Intent mStartActivity = new Intent(this, SplashActivity.class);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(this, mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
//        System.exit(0);
        finish();
    }
    private void updateUI() {
        startActivity(new Intent(SplashActivity.this, OnboardingActivity.class));
        finish();
    }
    @Override
    public void onBackPressed() {

    }
}