package com.shhb.gd.shop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shhb.gd.shop.R;
import com.shhb.gd.shop.activity.AlibcActivity;
import com.shhb.gd.shop.activity.LoginActivity;
import com.shhb.gd.shop.module.AlibcUser;
import com.shhb.gd.shop.tools.BaseTools;
import com.shhb.gd.shop.tools.PrefShared;
import com.shhb.gd.shop.view.SearchWindow;

import java.util.List;

/**
 * Created by superMoon on 2017/3/15.
 */

public class Fragment1 extends BaseNavPagerFragment implements View.OnClickListener {
    private EditText search1;
    private ImageView msg, cart;
    private SearchWindow searchWindow;
    private String json;

    public static Fragment1 newInstance() {
        Fragment1 fragment = new Fragment1();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchWindow = new SearchWindow(context);
        search1 = (EditText) view.findViewById(R.id.search1);
        search1.setOnFocusChangeListener(searchOnFocusChangeListener);
        msg = (ImageView) view.findViewById(R.id.message);
        msg.setOnClickListener(this);
        cart = (ImageView) view.findViewById(R.id.cart);
        cart.setOnClickListener(this);
    }

    View.OnFocusChangeListener searchOnFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if (hasFocus) {//得到焦点时的处理内容
//                search.setFocusable(true);
//                search.setFocusableInTouchMode(true);
                search1.clearFocus();//失去焦点
                searchWindow.showAtLocation(search1, Gravity.CENTER_HORIZONTAL, 0, 0);// 显示窗口
                BaseTools.openInput(search1);
            } else {//失去焦点时的处理内容

            }
        }
    };

    @Override
    public void onClick(View view) {
        Intent intent;
        String userId = PrefShared.getString(context, "userId");
        String nick = PrefShared.getString(context, "nick");
        switch (view.getId()) {
            case R.id.message:

                break;
            case R.id.cart:
                if (null != userId && !TextUtils.equals(userId, "")) {
                    if (null != nick && !TextUtils.equals(nick, "")) {
                        intent = new Intent(context, AlibcActivity.class);
                        intent.putExtra("type", "cart");
                        context.startActivity(intent);
                    } else {
                        new AlibcUser(context).login();
                    }
                } else {
                    intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                }
                break;
        }
    }

    @Override
    protected List<String> getTitles() {
        json = PrefShared.getString(this.getContext(), "homeTabJson");
        Log.e("tabData_首页", json);
        List<String> titles = null;
        try {
            titles = JSON.parseArray(String.valueOf(JSONObject.parseObject(json).getJSONArray("titles")), String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return titles;
    }

    @Override
    protected Fragment getFragment(int position) {
        List<String> cIds = null;
        try {
            cIds = JSON.parseArray(String.valueOf(JSONObject.parseObject(json).getJSONArray("cIds")), String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String cId = cIds.get(position) + "," + 0;
        return MainFragment.newInstance(cId);
    }
}