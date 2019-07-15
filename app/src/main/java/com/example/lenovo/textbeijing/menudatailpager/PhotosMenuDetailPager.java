package com.example.lenovo.textbeijing.menudatailpager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.lenovo.textbeijing.base.MenuDetailBasePager;
import com.example.lenovo.textbeijing.util.LogUtil;

public class PhotosMenuDetailPager extends MenuDetailBasePager {
    private TextView textView;
    public PhotosMenuDetailPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        TextView textView = new TextView(context);
        textView.setTextSize(25);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        return textView;
    }

    @Override
    public void initData() {

        super.initData();
        LogUtil.e("tuzu详情页面被初始化了");
    }
}
