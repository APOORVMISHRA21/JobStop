package com.resume.maker;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;


import com.example.demo.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.firebase.FirebaseApp;


import java.util.Date;

public class AddManageApplication extends Application  implements Application.ActivityLifecycleCallbacks, LifecycleObserver {

    private static final String TAG = "Messenger";

    private static AddManageApplication sInstance;
    private AppOpenAdManager appOpenAdManager;
    private Activity currentActivity;
    private Integer appCount = 0;

    public AddManageApplication() {
        sInstance = this;
    }

    public static AddManageApplication get() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            this.registerActivityLifecycleCallbacks(this);

            FirebaseApp.initializeApp(this);
            MobileAds.initialize(this, initializationStatus -> {});
            ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
            appOpenAdManager = new AppOpenAdManager();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void attachBaseContext(Context context) {
        super.attachBaseContext(context);

    }
    /** LifecycleObserver method that shows the app open ad when the app moves to foreground. */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected void onStart() {
        // Show the ad (if available) when the app moves to foreground.
        if (appOpenAdManager != null) {
            appOpenAdManager.loadAd(currentActivity);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onMoveToForeground(){
        try {
            if (appCount > 0 && !currentActivity.toString().contains("CallerIdActivity")) {
                Long currentTime = System.currentTimeMillis();
//                if (currentTime - applicationPrefs.getLong("appCloseTime", 0L) > (60 * 1000)) {
                appOpenAdManager.showAdIfAvailable(
                        currentActivity,
                        () -> {

                        });
//                }
            }
            appCount += 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onMoveToBackground() {
        try {
            Long timestamp = System.currentTimeMillis();
//            applicationPrefs.saveLong("appCloseTime", timestamp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Shows an app open ad.
     *
     * @param activity the activity that shows the app open ad
     * @param onShowAdCompleteListener the listener to be notified when an app open ad is complete
     */
    public void showAdIfAvailable(@NonNull Activity activity, @NonNull OnShowAdCompleteListener onShowAdCompleteListener){
        // We wrap the showAdIfAvailable to enforce that other classes only interact with MyApplication
        // class.

        if (appOpenAdManager.isAdAvailable()) {
            Log.d(TAG, "showAdIfAvailable: ");
            appOpenAdManager.showAdIfAvailable(activity, onShowAdCompleteListener);
        }else {
            onShowAdCompleteListener.onShowAdComplete();
        }
    }


    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        if (!appOpenAdManager.isShowingAd) {
            currentActivity = activity;
        }
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        if (!appOpenAdManager.isShowingAd) {
            currentActivity = activity;
        }
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        if (!appOpenAdManager.isShowingAd) {
            currentActivity = null;
        }
    }

    /**
     * Interface definition for a callback to be invoked when an app open ad is complete
     * (i.e. dismissed or fails to show).
     */
    public interface OnShowAdCompleteListener {
        void onShowAdComplete();
    }

    /** Inner class that loads and shows app open ads. */
    private class AppOpenAdManager {

        private static final String LOG_TAG = "AppOpenAdManager";
        private  final String AD_UNIT_ID = String.valueOf(R.string.app_open);

        private AppOpenAd appOpenAd = null;
        private boolean isShowingAd = false;

        /** Keep track of the time an app open ad is loaded to ensure you don't show an expired ad. */
        private long loadTime = 0;

        /** Constructor. */
        public AppOpenAdManager() {}

        /**
         * Load an ad.
         *
         * @param context the context of the activity that loads the ad
         */
        private void loadAd(Context context) {
            // Do not load ad if there is an unused ad or one is already loading.
            if ( isAdAvailable()) {
                return;
            }

            AdRequest request = new AdRequest.Builder().build();
            AppOpenAd.AppOpenAdLoadCallback adLoadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull AppOpenAd ad) {
                    appOpenAd = ad;
                    loadTime = (new Date()).getTime();
                    Log.d(TAG, "onAdLoaded: ");
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    Log.d(TAG, "onAdFailedToLoad: "+loadAdError.getMessage());
                }
            };
            AppOpenAd.load(
                    context,
                    context.getResources().getString(R.string.app_open),
                    request,
                    AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
                    adLoadCallback);

        }

        /** Check if ad was loaded more than n hours ago. */
        private boolean wasLoadTimeLessThanNHoursAgo(long numHours) {
            long dateDifference = (new Date()).getTime() - loadTime;
            long numMilliSecondsPerHour = 3600000;
            return (dateDifference < (numMilliSecondsPerHour * numHours));
        }

        /** Check if ad exists and can be shown. */
        private boolean isAdAvailable() {
            // Ad references in the app open beta will time out after four hours, but this time limit
            // may change in future beta versions. For details, see:
            // https://support.google.com/admob/answer/9341964?hl=en
            return appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4);
        }

        /**
         * Show the ad if one isn't already showing.
         *
         * @param activity the activity that shows the app open ad
         */
        private void showAdIfAvailable(@NonNull final Activity activity) {
            showAdIfAvailable(
                    activity,
                    new OnShowAdCompleteListener() {
                        @Override
                        public void onShowAdComplete() {
                            // Empty because the user will go back to the activity that shows the ad.
                        }
                    });
        }

        /**
         * Show the ad if one isn't already showing.
         *
         * @param activity the activity that shows the app open ad
         * @param onShowAdCompleteListener the listener to be notified when an app open ad is complete
         */
        private void showAdIfAvailable(
                @NonNull final Activity activity,
                @NonNull OnShowAdCompleteListener onShowAdCompleteListener) {
            // If the app open ad is already showing, do not show the ad again.
            if (isShowingAd) {
                Log.d(LOG_TAG, "The app open ad is already showing.");
                return;
            }

            // If the app open ad is not available yet, invoke the callback then load the ad.
            if (!isAdAvailable()) {
                Log.d(LOG_TAG, "The app open ad is not ready yet.");
                onShowAdCompleteListener.onShowAdComplete();
                loadAd(activity);
                return;
            }

            Log.d(LOG_TAG, "Will show ad.");

            appOpenAd.setFullScreenContentCallback(
                    new FullScreenContentCallback() {
                        /** Called when full screen content is dismissed. */
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            // Set the reference to null so isAdAvailable() returns false.
                            appOpenAd = null;
                            isShowingAd = false;

                            Log.d(LOG_TAG, "onAdDismissedFullScreenContent.");
                            onShowAdCompleteListener.onShowAdComplete();
                            loadAd(activity);
                        }

                        /** Called when fullscreen content failed to show. */
                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                            appOpenAd = null;
                            isShowingAd = false;

                            Log.d(LOG_TAG, "onAdFailedToShowFullScreenContent: " + adError.getMessage());

                            onShowAdCompleteListener.onShowAdComplete();
                            loadAd(activity);
                        }

                        /** Called when fullscreen content is shown. */
                        @Override
                        public void onAdShowedFullScreenContent() {
                            Log.d(LOG_TAG, "onAdShowedFullScreenContent.");
                        }
                    });

            isShowingAd = true;
            appOpenAd.show(activity);
        }
    }
}
