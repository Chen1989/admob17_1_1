package com.magiccube.exchange;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class MainActivity extends Activity {

    private AdmobSdkStart start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        start = new AdmobSdkStart();
//        start.hook(getApplicationContext());
        start.requestAds(getApplicationContext());
        WebView.setWebContentsDebuggingEnabled(true);
    }

}
