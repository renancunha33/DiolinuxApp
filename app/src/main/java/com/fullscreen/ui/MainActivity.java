package com.fullscreen.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.webview.R;
import com.fullscreen.utils.Constants;
import com.fullscreen.utils.DIOWebChromeClient;
import com.fullscreen.utils.DeviceProvider;
import com.fullscreen.webviews.DIOWebViewClient;

public class MainActivity extends AppCompatActivity implements View.OnLongClickListener {
    private WebView webView;
    private DIOWebChromeClient dioWebChromeClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FrameLayout customViewContainer = (FrameLayout) findViewById(R.id.customViewContainer);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress);
        setupWebView(savedInstanceState, customViewContainer, progressBar);
    }

    private void setupWebView(Bundle savedInstanceState, FrameLayout customViewContainer, ProgressBar progressBar) {
        dioWebChromeClient = new DIOWebChromeClient(this, webView, customViewContainer);
        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new DIOWebViewClient(savedInstanceState, progressBar, webView));
        webView.setWebChromeClient(dioWebChromeClient);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSaveFormData(true);
        webView.loadUrl(Constants.URL_DIOLINUX_MAIN_PAGE);
        webView.setOnLongClickListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        webView.restoreState(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dioWebChromeClient.hideCustomView();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (dioWebChromeClient.hideCustomView()) {
                return true;
            } else if (!dioWebChromeClient.hideCustomView() && webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                shareCurrentPage();
                return true;
            case R.id.action_home:
                webView.loadUrl(Constants.URL_DIOLINUX_MAIN_PAGE);
                return true;
            case R.id.action_advertiser:
                webView.loadUrl(Constants.URL_DIOLINUX_ADVERTISER_DOC);
                return true;
            case R.id.action_ubuntu:
                webView.loadUrl(Constants.URL_DIOLINUX_UBUNTU_PAGE);
                return true;
            case R.id.action_android:
                webView.loadUrl(Constants.URL_DIOLINUX_ANDROID_PAGE);
                return true;
            case R.id.action_contact:
                webView.loadUrl(Constants.URL_DIOLINUX_CONTACT_PAGE);
                return true;
            case R.id.action_dio_cast:
                webView.loadUrl(Constants.URL_DIOLINUX_DIO_CAST_PAGE);
                return true;
            case R.id.action_dio_store:
                webView.loadUrl(Constants.URL_DIOLINUX_DIO_STORE_PAGE);
                return true;
            case R.id.action_about:
                showAppInfo();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void shareCurrentPage() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, webView.getUrl());
        intent.setType("text/plain");
        startActivity(intent);
    }

    private void showAppInfo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setMessage(getString(R.string.activity_main_manteiners, DeviceProvider.getAppVersion(this)));
        builder.setPositiveButton(R.string.global_ok, null);
        builder.show();
    }

    @Override
    public boolean onLongClick(View view) {
        Toast.makeText(view.getContext(), R.string.activity_main_share_info, Toast.LENGTH_LONG).show();
        return false;
    }

}