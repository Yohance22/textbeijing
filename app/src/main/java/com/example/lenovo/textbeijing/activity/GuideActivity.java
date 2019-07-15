package com.example.lenovo.textbeijing.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.lenovo.textbeijing.R;
import com.example.lenovo.textbeijing.SplashActivity;
import com.example.lenovo.textbeijing.util.CacheUtils;
import com.example.lenovo.textbeijing.util.DensityUtil;

import java.util.ArrayList;

public class GuideActivity extends Activity {
    private static final String TAG =GuideActivity.class.getSimpleName();
    private ViewPager viewpager;
    private Button btn_start_main;
    private LinearLayout ll_point_group;
    private ArrayList<ImageView> imageViews;
    private ImageView iv_red_point;
    private int leftmax;
    private int widthdpi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        viewpager = (ViewPager)findViewById(R.id.viewpager);
        btn_start_main = (Button)findViewById(R.id.btn_start_main);
        ll_point_group = (LinearLayout)findViewById(R.id.ll_point_group);
        iv_red_point = (ImageView)findViewById(R.id.iv_red_point);
        int[] ids= new int[]{
                R.drawable.guide_1,
                R.drawable.guide_2,
                R.drawable.guide_3,


        };
        widthdpi = DensityUtil.dip2px(this,10);

        imageViews = new ArrayList<>();
        for (int i = 0;i<ids.length;i++){
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(ids[i]);
            imageViews.add(imageView);
            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.point_normal);


            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthdpi,widthdpi);
            point.setLayoutParams(params);
            if (i !=0){
                params.leftMargin=10;
            }
            ll_point_group.addView(point);


        }
//        设置viewpager的适配器
        viewpager.setAdapter(new MyPagerAdapter());
//        根据view的生命周期，当视图执行到onlayout或者ondraw时候，视图的高宽边距都有了
        iv_red_point.getViewTreeObserver().addOnGlobalLayoutListener(new MyOnOnGlobalLayoutListener());
        viewpager.addOnPageChangeListener(new MyOnPageChangeListener());


    }
    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int leftmargin = (int)(positionOffset*leftmax);

            Log.e(TAG,"position=="+position+",positionOffset=="+positionOffset+"positionOffsetPixels=="+positionOffsetPixels);
            leftmargin = (int)(position*leftmax +(positionOffset*leftmax));
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)iv_red_point.getLayoutParams();
            params.leftMargin = leftmargin;
            iv_red_point.setLayoutParams(params);
            btn_start_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //保存曾经进入过主页面，跳转到主页面，关闭引导页面
                    CacheUtils.putBoolean(GuideActivity.this,SplashActivity.START_MAIN,true);
                    Intent intent = new Intent(GuideActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();

                }
            });


        }

        @Override
        public void onPageSelected(int position) {
            if (position==imageViews.size()-1){
                btn_start_main.setVisibility(View.VISIBLE);
            }else{
                btn_start_main.setVisibility(View.GONE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


    class MyOnOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {

        @Override
        public void onGlobalLayout() {
            iv_red_point.getViewTreeObserver().removeGlobalOnLayoutListener(MyOnOnGlobalLayoutListener.this);
            leftmax = ll_point_group.getChildAt(1).getLeft() - ll_point_group.getChildAt(0).getLeft();

        }
    }

    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return imageViews.size();
        }
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {


            return view==object;
        }
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = imageViews.get(position);
            container.addView(imageView);
            return imageView;
//            return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }


}
