package com.example.lenovo.textbeijing.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.lenovo.textbeijing.R;
import com.example.lenovo.textbeijing.activity.MainActivity;

public class basePager {
    public final Context context;
    public View rootView;
    public TextView tv_title;
    public ImageButton ib_menu;
    public FrameLayout fl_content;
    public basePager(Context context){
        this.context = context;
        rootView = initView();



    }
    private View initView(){
        View view = View.inflate(context, R.layout.base_pager,null);
//        public TextView tv_title;
//        public ImageButton ib_menu;
//        public FrameLayout fl_content;
        tv_title = (TextView)view.findViewById(R.id.tv_title);
        ib_menu = (ImageButton)view.findViewById(R.id.ib_menu);
        fl_content = (FrameLayout)view.findViewById(R.id.fl_content);
        ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.getSlidingMenu().toggle();
            }
        });

        return view;

    }
    public void initData(){

    }




}
