package com.resume.maker;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.demo.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.resume.maker.dialogs.TedAdmobDialog;

import java.util.Calendar;
import java.util.List;


public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "ted>>";
    public static String resumenm;
    long TIME = 1000;
    ImageView imgcreatenew;
    ImageView resumeTemplates;
    ImageView imgprivacypolicy;
    ImageView imgrateapp;
    ImageView imgshareapp;
    ImageView imgtips;
    ImageView imgviewcreated;
    ImageView imgviewpdf;
    TedAdmobDialog nativeTedAdmobDialog;

    public InterstitialAd mInterstitialAd2 = null;

    public void log(String str) {
        Log.d(TAG, str);
    }

    public static void startAlarmBroadcastReceiver(Context context) {
        PendingIntent broadcast = PendingIntent.getBroadcast(context, 0, new Intent(context, NotificationReceivcer.class), 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(broadcast);
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(System.currentTimeMillis());
        instance.set(11, 23);
        instance.set(12, 59);
        instance.set(13, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, instance.getTimeInMillis(), broadcast);
    }


    @Override

    public void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_home);

        AdAdmob adAdmob = new AdAdmob(this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.bannerAd), this);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        startAlarmBroadcastReceiver(this);
        Window window = getWindow();
        window.clearFlags(67108864);
        window.addFlags(Integer.MIN_VALUE);
        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.grey));
        }
        this.imgcreatenew = (ImageView) findViewById(R.id.imgcreatenew);
        this.resumeTemplates  = (ImageView) findViewById(R.id.resumeTemplates);
        this.imgviewcreated = (ImageView) findViewById(R.id.imgviewcreated);
        this.imgviewpdf = (ImageView) findViewById(R.id.imgviewpdf);
        this.imgrateapp = (ImageView) findViewById(R.id.imgrateapp);
        this.imgtips = (ImageView) findViewById(R.id.imgtips);
        this.imgshareapp = (ImageView) findViewById(R.id.imgshareapp);
        this.imgprivacypolicy = (ImageView) findViewById(R.id.imgprivacypolicy);
        final Animation loadAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.unclick_btn_zoom);
        this.resumeTemplates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                view.startAnimation(loadAnimation);
                HomeActivity.this.startActivity(new Intent(HomeActivity.this, ResumeSamplesActivity.class));
                view.setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.setEnabled(true);
                    }
                }, HomeActivity.this.TIME);
            }
        });
        this.imgcreatenew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                view.startAnimation(loadAnimation);
                HomeActivity.this.requestMultiplePermissions();
                view.setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.setEnabled(true);
                    }
                }, HomeActivity.this.TIME);
            }
        });
        this.imgviewcreated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                view.startAnimation(loadAnimation);
                HomeActivity.this.startActivity(new Intent(HomeActivity.this, MyCreationActivity.class));
                view.setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.setEnabled(true);
                    }
                }, HomeActivity.this.TIME);
            }
        });
        this.imgrateapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.this.rateApp();
            }
        });
        this.imgtips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.this.startActivity(new Intent(HomeActivity.this, InterviewTipsActivity.class));
            }
        });

        this.imgshareapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.this.shareApp();
            }
        });
        this.imgprivacypolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.this.startActivity(new Intent(HomeActivity.this, PrivacyActivity.class));
            }
        });
    }


    public void feedbackApp() {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("message/rfc822");
        intent.putExtra("android.intent.extra.EMAIL", new String[]{"support@virtueinfotech.com"});
        intent.putExtra("android.intent.extra.SUBJECT", getString(R.string.app_name));
        intent.putExtra("android.intent.extra.TEXT", "");
        try {
            startActivity(Intent.createChooser(intent, "Send mail..."));
        } catch (ActivityNotFoundException unused) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }


    public void rateApp() {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + getPackageName()));
        intent.addFlags(1208483840);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }


    public void shareApp() {
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.SUBJECT", getString(R.string.app_name));
            intent.putExtra("android.intent.extra.TEXT", "\nLet me recommend you this application\n\nhttps://play.google.com/store/apps/details?id=" + getPackageName() + "\n\n");
            startActivity(Intent.createChooser(intent, "choose one"));
        } catch (Exception unused) {
        }
    }

    public void showNameDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(1);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.createresume_dialog);
        final EditText editText = (EditText) dialog.findViewById(R.id.editText);
        resumenm = editText.getText().toString();
        ((Button) dialog.findViewById(R.id.btns)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (HomeActivity.this.isEmpty(editText)) {
                    Toast.makeText(HomeActivity.this, "Please Enter Resume Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog.dismiss();
                HomeActivity.this.startActivity(new Intent(HomeActivity.this, MainActivity.class));
            }
        });
        dialog.show();
    }


    public boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }


    public void requestMultiplePermissions() {
        Dexter.withActivity(this).withPermissions("android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE").withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                if (multiplePermissionsReport.areAllPermissionsGranted()) {
                    HomeActivity.this.startActivity(new Intent(HomeActivity.this, PersonalinfoActivity.class));
                }
                if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                    Toast.makeText(HomeActivity.this, "Please allow app to continue", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.fromParts("package", HomeActivity.this.getPackageName(), null));
                    HomeActivity.this.startActivity(intent);
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                Toast.makeText(HomeActivity.this, "Please allow app to continue", Toast.LENGTH_SHORT).show();
                permissionToken.continuePermissionRequest();
            }
        }).withErrorListener(new PermissionRequestErrorListener() {
            @Override
            public void onError(DexterError dexterError) {
            }
        }).onSameThread().check();
    }

//    public void exitdialog() {
//        DialogInterface.OnClickListener r0 = new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                if (i == -3) {
//                    HomeActivity homeActivity = HomeActivity.this;
//                    homeActivity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + HomeActivity.this.getPackageName())));
//                    dialogInterface.dismiss();
//                } else if (i == -1) {
//                    dialogInterface.dismiss();
//                    HomeActivity.this.finish();
//                    System.exit(0);
//                }
//            }
//        };
//        new AlertDialog.Builder(this).setMessage("Are you sure you want to exit?").setPositiveButton("Exit", r0).setTitle("").setNeutralButton("Rate Us", r0).show();
//    }


//public void requestNewInterstitial2() {
//    Log.d("isAdDelayedToShow", "requesting ad");
//    this.mInterstitialAd2.loadAd(new AdRequest.Builder().build());
//}
private NativeAd nativeAd;
    private void populateUnifiedNativeAdView(NativeAd nativeAd, NativeAdView adView) {
        com.google.android.gms.ads.nativead.MediaView mediaView = adView.findViewById(R.id.ad_media);
//        MediaView mediaView = adView.findViewById(R.id.ad_media);
        adView.setMediaView(mediaView);

        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));

        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // The headline is guaranteed to be in every UnifiedNativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((TextView) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        adView.setNativeAd(nativeAd);
    }

    public void loadNativeFragmentD(Dialog dialog) {
        try {
            VideoOptions videoOptions = new VideoOptions.Builder()
                    .setStartMuted(false)
                    .build();

            com.google.android.gms.ads.nativead.NativeAdOptions.Builder nativeAdOptions = new com.google.android.gms.ads.nativead.NativeAdOptions.Builder();
            nativeAdOptions.setVideoOptions(videoOptions);
            com.google.android.gms.ads.nativead.NativeAdOptions adOptions = nativeAdOptions.build();


            AdLoader adLoader = new AdLoader.Builder(dialog.getContext(), getString(R.string.admob_native_id_exit))
                    .forNativeAd(nativeAd2 -> {
                        if (nativeAd != null) {
                            nativeAd.destroy();
                        }

                        nativeAd = nativeAd2;
                        FrameLayout frameLayout =
                                dialog.findViewById(R.id.fl_adplaceholder);
                        if (frameLayout != null) {
                            NativeAdView adView = (NativeAdView) getLayoutInflater()
                                    .inflate(R.layout.native_admob_ad, (ViewGroup) getCurrentFocus(), false);

                            populateUnifiedNativeAdView(nativeAd2, adView);
                            frameLayout.removeAllViews();
                            frameLayout.addView(adView);
                        }
                    })
                    .withAdListener(new AdListener() {
                        @Override
                        public void onAdLoaded() {

                            System.out.println("loadeddddd");
                            super.onAdLoaded();
                        }

                        @Override
                        public void onAdFailedToLoad(LoadAdError loadAdError) {
                            System.out.println("faileddddd"+ loadAdError);
                            super.onAdFailedToLoad(loadAdError);
                        }

                    })
                    .withNativeAdOptions(adOptions)
                    .build();

            adLoader.loadAd(new AdRequest.Builder().build());

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

public void exitdialog() {

    final AlertDialog dialog;
    View view = getLayoutInflater().inflate(R.layout.dialog_exit, null);
    dialog = new AlertDialog.Builder(this).setView(view).create();
    Window window = dialog.getWindow();
    WindowManager.LayoutParams wlp = window.getAttributes();
    wlp.gravity = Gravity.BOTTOM;
    window.setAttributes(wlp);
    dialog.show();

    DisplayMetrics metrics = getResources().getDisplayMetrics();
    int width = metrics.widthPixels;
    int height=metrics.heightPixels;
    dialog.getWindow().setLayout(width, height/2);
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    Button btn = view.findViewById(R.id.cancel);
//    mInterstitialAd2=null;
//    this.mInterstitialAd2 = new InterstitialAd(this);
//    this.mInterstitialAd2.setAdUnitId(getString(R.string.admob_inter_exit));
//    requestNewInterstitial2();

    loadNativeFragmentD(dialog);
    btn.setOnClickListener(v -> dialog.dismiss());

    Button btn2 = view.findViewById(R.id.exit);
    btn2.setOnClickListener(v -> {
        dialog.dismiss();
        finish();
    });

}

    @Override
    public void onBackPressed() {
        exitdialog();
    }






}
