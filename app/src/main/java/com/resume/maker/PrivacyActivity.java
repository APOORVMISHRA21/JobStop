package com.resume.maker;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.demo.R;


public class PrivacyActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    WebView webView;


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_privacy);
        initToolbar();
        this.webView = (WebView) findViewById(R.id.webview);
        startWebView("https://www.google.com/");
    }

    private void startWebView(String str) {
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.setScrollBarStyle(33554432);
        this.webView.getSettings().setBuiltInZoomControls(true);
        this.webView.getSettings().setDisplayZoomControls(false);
        this.webView.getSettings().setUseWideViewPort(true);
        this.webView.getSettings().setLoadWithOverviewMode(true);
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setMessage("Loading...");
        this.progressDialog.show();
        this.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String str2) {
                webView.loadUrl(str2);
                return true;
            }

            @Override
            public void onPageFinished(WebView webView, String str2) {
                if (PrivacyActivity.this.progressDialog.isShowing()) {
                    PrivacyActivity.this.progressDialog.dismiss();
                }
            }

            @Override
            public void onReceivedError(WebView webView, int i, String str2, String str3) {
                PrivacyActivity privacyActivity = PrivacyActivity.this;
                Toast.makeText(privacyActivity, "Error:" + str2, Toast.LENGTH_SHORT).show();
            }
        });
        this.webView.loadUrl(str);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setHomeButtonEnabled(true);
        toolbar.setNavigationIcon(R.drawable.back);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService("connectivity");
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isAvailable() && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public void showAlertDialog(Context context, String str, String str2, Boolean bool) {
        AlertDialog create = new AlertDialog.Builder(context).create();
        create.setCancelable(false);
        create.setTitle(str);
        create.setMessage(str2);
        create.setButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                PrivacyActivity.this.finish();
            }
        });
        create.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
    }
}
