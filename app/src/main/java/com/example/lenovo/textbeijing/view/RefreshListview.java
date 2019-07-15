package com.example.lenovo.textbeijing.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lenovo.textbeijing.R;

public class RefreshListview extends ListView {
    //下拉刷新
    private LinearLayout headerView;
    private View ll_pull_down_refresh;
    private ImageView iv_arrow;
    private ProgressBar pb_status;
    private TextView tv_time;
    private TextView tv_status;
    private int pullDownRefreshHeight;


    public RefreshListview(Context context) {
        this(context, null);
    }

    public RefreshListview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView(context);
    }

    private void initHeaderView(Context context) {
        headerView = (LinearLayout) View.inflate(context, R.layout.refresh_header, null);
        ll_pull_down_refresh = headerView.findViewById(R.id.ll_pull_down_refresh);
        iv_arrow = (ImageView) headerView.findViewById(R.id.iv_arrow);
        pb_status = (ProgressBar) headerView.findViewById(R.id.pb_status);
        tv_time = (TextView) headerView.findViewById(R.id.tv_time);
        tv_status = (TextView) headerView.findViewById(R.id.tv_status);
        //默认隐藏下拉刷新控件
        ll_pull_down_refresh.measure(0,0);
        pullDownRefreshHeight = ll_pull_down_refresh.getMeasuredHeight();
        ll_pull_down_refresh.setPadding(0,-pullDownRefreshHeight,0,0);

        //添加头
        RefreshListview.this.addHeaderView(headerView);




    }
    private float startY = -1;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (startY==-1){
                    startY =ev.getY();
                }
                float endY = ev.getY();
                float distanceY = endY - startY;
                if (distanceY>0){
                    int paddingTop = (int) (-pullDownRefreshHeight+distanceY);
                    ll_pull_down_refresh.setPadding(0,paddingTop,0,0);
                }

                break;
            case MotionEvent.ACTION_UP:
                startY = -1;
                break;

        }
        return super.onTouchEvent(ev);
    }
}
