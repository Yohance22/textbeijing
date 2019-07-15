package com.example.lenovo.textbeijing.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.example.lenovo.textbeijing.base.basePager;

public class SettingPager extends basePager {
    public SettingPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
//        1、设置标题
        tv_title.setText("ccccccccccccc");
        TextView textView = new TextView(context);
        textView.setTextSize(25);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        fl_content.addView(textView);
        textView.setText("cccccccccccccccccccc");
    }
}
