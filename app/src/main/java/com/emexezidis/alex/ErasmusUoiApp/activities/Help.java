package com.emexezidis.alex.ErasmusUoiApp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.emexezidis.alex.ErasmusUoiApp.R;

import java.util.Locale;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        WebView webView = (WebView) findViewById(R.id.faq_webview);

        if(Locale.getDefault().getLanguage().equals("el")) {
            WebSettings settings = webView.getSettings();
            settings.setDefaultTextEncodingName("utf-8");
            webView.loadUrl("file:///android_asset/erasmus_faq_greek.html");
        } else {
            webView.loadUrl("file:///android_asset/erasmus_faq_english.html");
        }
        webView.setBackgroundColor(getResources().getColor(R.color.almostWhite));
    }

    // This is used to provide back navigation through the toolbar
    // for 4.0 Android devices:
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
