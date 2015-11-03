package com.fullscreen.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.webview.R;
import com.fullscreen.utils.Constants;
import com.fullscreen.utils.DIOWebChromeClient;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private DIOWebChromeClient dioWebChromeClient;
    private Intent shareIntent = new Intent(Intent.ACTION_SEND);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FrameLayout customViewContainer = (FrameLayout) findViewById(R.id.customViewContainer);

        webView = (WebView) findViewById(R.id.webView);
        DIOWebViewClient mWebViewClient = new DIOWebViewClient();
        webView.setWebViewClient(mWebViewClient);

        dioWebChromeClient = new DIOWebChromeClient(this, webView, customViewContainer);
        webView.setWebChromeClient(dioWebChromeClient);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSaveFormData(true);

        webView.loadUrl(Constants.URL_DIOLINUX_MAIN_PAGE);

        webView.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        final Toast toast = Toast.makeText(view.getContext(), "Compartilhe o link com as opções do menu", Toast.LENGTH_LONG);
                        toast.show();
                        return false;
                    }

                });

        if (savedInstanceState == null) {
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return false;
                }
            });
        }
        webView.setWebViewClient(new WebViewClient() {
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                ProgressBar pb = (ProgressBar) findViewById(R.id.progress);
                pb.setVisibility(View.VISIBLE);

            }

            public void onPageFinished(WebView view, String url) {
                ProgressBar pb = (ProgressBar) findViewById(R.id.progress);
                pb.setVisibility(View.INVISIBLE);
                view.setVisibility(View.VISIBLE);
                if (webView.getUrl() != null)
                    shareCurrentPage();
            }
        });
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

    //TODO Refatorar isso
    private void showAppInfo() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setMessage("Version 1.2.5 \n\n" +
                "Developer: Renan Cunha\n" +
                "Designer: Dionatan Simioni\nContato sobre o app:\nrenan.cunha33@gmail.com");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.show();
    }

    private void shareCurrentPage() {
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, webView.getUrl());
        shareIntent.setType("text/plain");
        startActivity(shareIntent);
    }

    private class DIOWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

}