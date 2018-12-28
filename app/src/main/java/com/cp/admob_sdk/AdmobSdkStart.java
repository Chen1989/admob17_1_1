package com.cp.admob_sdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cp.admob_sdk.hook.ActivityHooker;
import com.cp.admob_sdk.hook.FaceBookLinearLayout;
import com.cp.admob_sdk.hook.Hooker;
import com.cp.admob_sdk.hook.HookerPackageManager;
import com.cp.admob_sdk.hook.PackageAssist;
import com.cp.admob_sdk.util.Logger;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

/**
 * Created by PengChen on 2018/12/27.
 */

public class AdmobSdkStart {
    private final String TAG = "ChenSdk";

    private InterstitialAd mInterstitialAd;

    public void requestAds(Context context) {
        MobileAds.initialize(context, "ca-app-pub-3940256099942544~3347511713");
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                super.onAdClicked();
                Log.i(TAG, "onAdClicked");
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                Log.i(TAG, "onAdClosed");
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                Log.i(TAG, "onAdFailedToLoad");
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
                Log.i(TAG, "onAdImpression");
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
                Log.i(TAG, "onAdLeftApplication");
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.i(TAG, "onAdLoaded");
                mInterstitialAd.show();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                Log.i(TAG, "onAdOpened");
            }
        });
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    public void hook(Context context) {
        PackageAssist instance = PackageAssist.getInstance();
        instance.init(context.getApplicationContext());
        instance.setReplacePkg(context.getApplicationContext(),
                context.getPackageName(), "admob_sdk");

        HookerPackageManager.hook(context.getApplicationContext(),
                context.getPackageName(), context.getPackageName(),
                12, "1.0.5",
                "admob_sdk");

        ActivityHooker.addLifeHooker(new ActivityHooker.OnActivityLifeHooker() {
            @Override
            public void onCreateBefore(Context context, Activity activity) {
                Logger.i("onCreateBefore onCreateBefore");
                if (activity.getClass().getName().contains("AudienceNetwork")) {
                    Logger.i("flags = " + activity.getWindow().getAttributes().flags);
                    Logger.i("type = " + activity.getWindow().getAttributes().type);
//                    activity.getWindow().getAttributes().alpha = 0.5f;
                }
                if (activity.getClass().getName().contains("com.google.android.gms.ads.AdActivity")) {
                    Logger.i("admob flags = " + activity.getWindow().getAttributes().flags);
                    Logger.i("admob type = " + activity.getWindow().getAttributes().type);
                }
            }

            @Override
            public void onCreateAfter(Context context, Activity activity) {
                Logger.i("onCreateAfter onCreateAfter");
                if (activity.getClass().getName().contains("AudienceNetwork")) {
                    View view1 = activity.findViewById(android.R.id.content);
                    LinearLayout parent = ((LinearLayout)view1.getParent());
                    parent.removeView(view1);
                    LinearLayout my = new FaceBookLinearLayout(activity);
                    my.addView(view1);
                    parent.addView(my, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                }
                if (activity.getClass().getName().contains("com.google.android.gms.ads.AdActivity")) {
                    Logger.i("admob after flags = " + activity.getWindow().getAttributes().flags);
                    Logger.i("admob after type = " + activity.getWindow().getAttributes().type);
                }
            }

            @Override
            public void onStart(Context context, Activity activity) {
                Logger.i("onStart = " + activity.getWindow().getAttributes().type);
            }

            @Override
            public void onResume(Context context, Activity activity) {
                Logger.i("onResume = " + activity.getWindow().getAttributes().type);
            }

            @Override
            public void onPause(Context context, Activity activity) {
                Logger.i("onPause = " + activity.getWindow().getAttributes().type);
            }

            @Override
            public void onStop(Context context, Activity activity) {
                Logger.i("onStop = " + activity.getWindow().getAttributes().type);
            }

            @Override
            public void onDestroy(Context context, Activity activity) {
                Logger.i("onDestroy = " + activity.getWindow().getAttributes().type);
            }
        });
        ActivityHooker.lifeHock(context.getApplicationContext());
        Hooker.hook(context.getApplicationContext(), new Hooker.OnActivityManagerHooker() {
            @Override
            public void onStartActivity(Intent intent, Hooker.Handle handle) {
                Logger.i("onStartActivity   " + intent.getComponent());
            }
        });
    }
}
