package com.hemantjoshi.newsapp;

import android.graphics.Bitmap;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * @author HemantJ
 * This class is called when the user clicks on a single news item
 * It opens a {@code WebView} that loads the link accordingly
 */

public class NewsDetailsActivity extends AppCompatActivity {
    private WebView webView;
    private Bundle bundle;
    private String web;
    private ProgressBar progressBar;

    /**
     * @param savedInstanceState bundle
     * {@code webView.setWebViewClient(new WebViewClient())} to open the link in the app and not in a new
     *                           browser window.
     * @see WebView for more details
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        webView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressBar);

        bundle = getIntent().getExtras();
        if(bundle != null)
            web = bundle.getString("link");
        WebSettings settings = webView.getSettings();
        /*
        Enabling JavaScript for smooth browsing and video playing
         */
        settings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon){
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url){
                progressBar.setVisibility(View.INVISIBLE);
                webView.setVisibility(View.VISIBLE);
            }
        });
        webView.loadUrl(web);
    }
}
