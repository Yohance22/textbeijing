package com.example.lenovo.textbeijing.base;

import android.content.Context;
import android.view.View;

import com.example.lenovo.textbeijing.util.LogUtil;

//详情页面的基类
public abstract class MenuDetailBasePager {
    public final Context context;
    public View rootView;
    public MenuDetailBasePager(Context context){
        this.context = context;
        rootView =initView();

    }
    //抽象方法，强制孩子实现该方法，每个页面实现不同的效果

    public abstract View initView();
    //子页面需要绑定数据，联网请求数据等的时候，重写该方法
    public void initData(){

    }

}
