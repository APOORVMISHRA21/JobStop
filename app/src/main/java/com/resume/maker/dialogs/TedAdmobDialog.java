package com.resume.maker.dialogs;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;

import com.example.demo.R;
import com.resume.maker.interfaces.OnBackPressListener;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class TedAdmobDialog extends AlertDialog {
    private static final String TAG = "ted>>";
    private LinearLayout bannerContainer;
    private Builder builder;
    private ProgressBar progressView;

    @Retention(RetentionPolicy.SOURCE)

    public @interface AdType {
        public static final int BANNER = 2;
        public static final int NATIVE = 1;
    }

    public TedAdmobDialog(Builder builder, int i) {
        super(builder.context, i);
        this.builder = builder;
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d("ted", "onCreate()");
        setContentView(R.layout.dialog_tedadmob);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        Window window = getWindow();
        layoutParams.copyFrom(window.getAttributes());
        layoutParams.width = -1;
        layoutParams.height = -2;
        window.setAttributes(layoutParams);
        setCanceledOnTouchOutside(false);
        initView();
        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                TedAdmobDialog.this.progressView.setVisibility(View.GONE);
                TedAdmobDialog.this.bannerContainer.setVisibility(View.GONE);
                TedAdmobDialog.this.bannerContainer.removeAllViews();
                int i = TedAdmobDialog.this.builder.adType;

            }
        });
    }

    private void initView() {

        this.progressView = (ProgressBar) findViewById(R.id.progressView);
        this.bannerContainer = (LinearLayout) findViewById(R.id.view_banner_container);
        findViewById(R.id.tv_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TedAdmobDialog.this.onFinishClick();
            }
        });
        TextView textView = (TextView) findViewById(R.id.tv_review);
        View findViewById = findViewById(R.id.view_btn_divider);
        if (this.builder.showReviewButton) {
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TedAdmobDialog.this.onReviewClick();
                }
            });
            return;
        }
        textView.setVisibility(View.GONE);
        findViewById.setVisibility(View.GONE);
    }


    public void onFinishClick() {
        if (this.builder.onBackPressListener != null) {
            this.builder.onBackPressListener.onFinish();
        }
        dismiss();
    }


    public void onReviewClick() {
        openPlayStore();
        if (this.builder.onBackPressListener != null) {
            this.builder.onBackPressListener.onReviewClick();
        }
    }

    private void openPlayStore() {
        String packageName = getContext().getPackageName();
        try {
            Context context = getContext();
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + packageName)));
        } catch (ActivityNotFoundException unused) {
            Context context2 = getContext();
            context2.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + packageName)));
        }
    }



    public static class Builder {
        private int adType;
        private Context context;
        private OnBackPressListener onBackPressListener;
        private String unitId;
        private boolean startMute = true;
        private boolean showReviewButton = true;

        public Builder(Context context, int i, String str) {
            this.context = context;
            this.adType = i;
            this.unitId = str;
        }

        public Builder setStartMute(boolean z) {
            this.startMute = z;
            return this;
        }


        public Builder setOnBackPressListener(OnBackPressListener onBackPressListener) {
            this.onBackPressListener = onBackPressListener;
            return this;
        }

        public Builder showReviewButton(boolean z) {
            this.showReviewButton = z;
            return this;
        }

        public TedAdmobDialog create() {
            return new TedAdmobDialog(this, R.style.TedAdmobDialog);
        }
    }
}
