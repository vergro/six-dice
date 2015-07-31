package com.surreall.sixdice;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.surreall.sixdice.R;


public class Help extends Main {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rules);

        getGameHelper().setMaxAutoSignInAttempts(0);
        WebView browser = (WebView)findViewById(R.id.yourwebview);
        browser.setBackgroundColor(0); // make webview transparent to see background image
        WebSettings settings = browser.getSettings();
        settings.setJavaScriptEnabled(true);

        browser.loadUrl("file:///android_asset/about.html");

    }
}



	
