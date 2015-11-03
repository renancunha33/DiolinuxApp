package com.fullscreen.webviews;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Created by jackson on 02/11/15.
 */
public class DIOWebViewClient extends WebViewClient {

    private final Bundle savedInstanceState;
    private final ProgressBar progressBar;
    private final WebView webView;

    public DIOWebViewClient(Bundle savedInstanceState, ProgressBar progressBar, WebView webView) {
        this.savedInstanceState = savedInstanceState;
        this.progressBar = progressBar;
        this.webView = webView;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (savedInstanceState == null)
            view.loadUrl(url);
        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        progressBar.setVisibility(View.VISIBLE);
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        progressBar.setVisibility(View.INVISIBLE);
        view.setVisibility(View.VISIBLE);
    }
}
