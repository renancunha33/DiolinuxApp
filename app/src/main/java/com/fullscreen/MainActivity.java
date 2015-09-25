package com.fullscreen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;


import android.app.Activity;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.example.webview.R;

public class MainActivity extends Activity {
    private WebView webView;
    private FrameLayout customViewContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;
    private View mCustomView;
    private myWebChromeClient mWebChromeClient;
    private myWebViewClient mWebViewClient;
    private String url = "http://www.diolinux.com.br";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customViewContainer = (FrameLayout) findViewById(R.id.customViewContainer);
        webView = (WebView) findViewById(R.id.webView);

        mWebViewClient = new myWebViewClient();
        webView.setWebViewClient(mWebViewClient);

        mWebChromeClient = new myWebChromeClient();
        webView.setWebChromeClient(mWebChromeClient);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSaveFormData(true);
        webView.loadUrl(url);

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

    public boolean inCustomView() {
        return (mCustomView != null);
    }

    public void hideCustomView() {
        mWebChromeClient.onHideCustomView();
    }

    @Override
    protected void onPause() {
        super.onPause();    //To change body of overridden methods use File | Settings | File Templates.
        webView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
        webView.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();    //To change body of overridden methods use File | Settings | File Templates.
        if (inCustomView()) {
            hideCustomView();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (inCustomView()) {
                hideCustomView();
                return true;
            }

            if ((mCustomView == null) && webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);

    }

    class myWebChromeClient extends WebChromeClient {
        private Bitmap mDefaultVideoPoster;
        private View mVideoProgressView;

        @Override
        public void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback) {
            onShowCustomView(view, callback);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {

            // if a view already exists then immediately terminate the new one
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            mCustomView = view;
            webView.setVisibility(View.GONE);
            customViewContainer.setVisibility(View.VISIBLE);
            customViewContainer.addView(view);
            customViewCallback = callback;
        }

        @Override
        public View getVideoLoadingProgressView() {

            if (mVideoProgressView == null) {
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                mVideoProgressView = inflater.inflate(R.layout.video_progress, null);
            }
            return mVideoProgressView;
        }

        @Override
        public void onHideCustomView() {
            super.onHideCustomView();    //To change body of overridden methods use File | Settings | File Templates.
            if (mCustomView == null)
                return;

            webView.setVisibility(View.VISIBLE);
            customViewContainer.setVisibility(View.GONE);

            // Hide the custom view.
            mCustomView.setVisibility(View.GONE);

            // Remove the custom view from its container.
            customViewContainer.removeView(mCustomView);
            customViewCallback.onCustomViewHidden();

            mCustomView = null;
        }
    }

    class myWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);    //To change body of overridden methods use File | Settings | File Templates.
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem m8 = menu.add(0, 0, 0, "Home");
        m8.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        MenuItem m2 = menu.add(0, 1, 0, "Anuncie");
        m2.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        MenuItem m3 = menu.add(0, 2, 0, "Ubuntu");
        m3.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        MenuItem m4 = menu.add(0, 3, 0, "Android");
        m4.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        MenuItem m5 = menu.add(0, 4, 0, "Contato");
        m5.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        MenuItem m6 = menu.add(0, 5, 0, "DioCast");
        m6.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        MenuItem m7 = menu.add(0, 6, 0, "DioStore");
        m7.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        MenuItem m1 = menu.add(0, 7, 0, "Sobre");
        m1.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == 7) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Diolinux Webapp");
            builder.setMessage("Version 1.2.2 \n\n" +
                    "Developer: Renan Cunha\n" +
                    "Designer: Dionatan Simioni\nContato sobre o app:\nrenan.cunha33@gmail.com");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            builder.show();

            // final Toast toast =  Toast.makeText(this, "Diolinux Webapp 1.0.0.9 \nDesigned by Renan Cunha\nrenan.cunnha33@gmail.com ", Toast.LENGTH_LONG);
            // toast.show();
        } else {
            switch (id) {
                case 0:
                    url = "http://www.diolinux.com.br";
                    break;
                case 1:
                    url = "https://docs.google.com/presentation/d/1mkVqs3HBvM_kY-n3hLbLFZeK0p0YmIm3aMXYaTf7jDo/pub?start=false&loop=false&delayms=3000&slide=id.p";
                    break;
                case 2:
                    url = "http://www.diolinux.com.br/search/label/Ubuntu";
                    break;
                case 3:
                    url = "http://www.diolinux.com.br/search/label/Android";
                    break;
                case 4:
                    url = "http://www.diolinux.com.br/p/contato.html";
                    break;
                case 5:
                    url = "http://www.diolinux.com.br/search/label/DioCast";
                    break;
                case 6:
                    url = "http://www.diostore.com.br";
                    break;
            }
            webView.loadUrl(url);
        }
        /*noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}