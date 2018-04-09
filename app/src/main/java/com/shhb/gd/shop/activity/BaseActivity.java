package com.shhb.gd.shop.activity;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.jaeger.library.StatusBarUtil;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.shhb.gd.shop.R;
import com.shhb.gd.shop.application.MainApplication;
import com.shhb.gd.shop.view.BlackStatusBar;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by superMoon on 2017/3/15.
 */

public class BaseActivity extends SwipeBackActivity {

    public static Activity context;
    public boolean processFlag = true; //默认可以点击
    public KProgressHUD hud;
    public KProgressHUD failureHud;
    private ImageView imageView ;
    private AnimationDrawable drawable;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar();
        setBlackStatusBar();
        createCustomImgLoad();
        createLoading();
    }

    /**
     * 设置透明状态栏
     */
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(context, R.color.white), 0);
    }

    /**
     * 设置状态栏为黑色图标
     */
    protected void setBlackStatusBar() {
        BlackStatusBar.StatusBarLightMode(this);
    }

    private void createCustomImgLoad() {
        imageView = new ImageView(this);
        imageView.setBackgroundResource(R.drawable.custom_img_load);
        drawable = (AnimationDrawable) imageView.getBackground();
    }

    /**
     * 创建自定义的loading
     */
    protected void createLoading() {
        hud = KProgressHUD.create(context);
        hud.setCancellable(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        MainApplication.getInstance().addActivity(this);
    }

    /**
     * 设置按钮在短时间内被重复点击的有效标识（true表示点击有效，false表示点击无效）
     */
    protected synchronized void setProcessFlag() {
        processFlag = false;
    }

    /**
     * 计时线程（防止在一定时间段内重复点击按钮）
     */
    protected class TimeThread extends Thread {
        public void run() {
            try {
                sleep(1000);
                processFlag = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 显示提示框，0显示普通的loading
     */
    public void showToast(int type,String content){
        if (type == 0) {
            drawable.start();
            hud.setCustomView(imageView);
            hud.setDetailsLabel(content + "...");
            hud.show();
        } else {
            if(hud.isShowing()){
                hud.dismiss();
            }
            Message message = new Message();
            message.what = 1;
            message.obj = content;
            showHandler.sendMessage(message);
        }
    }

    /**
     * 关闭普通的loading
     */
    public void closeToast(){
        if(hud.isShowing()){
            hud.dismiss();
        }
    }

    /**
     * 显示1秒就消失的提示框
     */
    Handler showHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String content = (String) msg.obj;
            try {
                if(msg.what == 1){
                    failureHud.setDetailsLabel(content);
                    failureHud.show();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    /**
     * 1.5秒之后关掉提示
     */
    public class hideThread extends Thread {
        public void run() {
            try {
                sleep(1000);
                failureHud.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
