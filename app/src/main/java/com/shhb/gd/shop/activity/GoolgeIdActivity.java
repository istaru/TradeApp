package com.shhb.gd.shop.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.shhb.gd.shop.R;
import com.shhb.gd.shop.module.Constants;

/**
 * Created by superMoon on 2018/3/5.
 */

public class GoolgeIdActivity extends BaseActivity implements View.OnClickListener, RewardedVideoAdListener {
    private LinearLayout onBack;
    private RewardedVideoAd mAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title_activity);
        initAdMob();
        initView();
    }

    private void initView() {
        onBack = (LinearLayout) findViewById(R.id.onBack);
        onBack.setOnClickListener(this);
    }

    /**
     * 初始化Google广告
     */
    private void initAdMob() {
        MobileAds.initialize(context, Constants.GOOGLE_ID);
        mAd = MobileAds.getRewardedVideoAdInstance(context);
        mAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();
    }

    private void loadRewardedVideoAd() {
        mAd.loadAd(Constants.GOOGLE_VIDEO_ID,new AdRequest.Builder().build());
    }

    @Override
    public void onRewardedVideoAdLoaded() {
//        showToast(0,"广告加载中……");
        Log.e("TAG","广告加载中……");
    }

    @Override
    public void onRewardedVideoAdOpened() {
//        Log.e("TAG","广告已打开");
        Log.e("TAG","广告已打开");
    }

    @Override
    public void onRewardedVideoStarted() {
        Log.e("TAG","开始加载广告");
    }

    @Override
    public void onRewardedVideoAdClosed() {
        Log.e("TAG","广告已关闭");
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        Log.e("TAG","onRewarded执行");
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        Log.e("TAG","onRewardedVideoAdLeftApplication执行");
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
//        showToast(1,"广告加载失败！");
        Log.e("TAG","广告加载失败！"+i);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.onBack:
                if (mAd.isLoaded()) {
                    mAd.show();
                }
                break;
        }
    }
}
