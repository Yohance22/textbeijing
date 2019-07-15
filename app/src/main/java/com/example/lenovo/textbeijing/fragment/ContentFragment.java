package com.example.lenovo.textbeijing.fragment;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.lenovo.textbeijing.R;
import com.example.lenovo.textbeijing.activity.MainActivity;
import com.example.lenovo.textbeijing.adapter.ContentFragmentAdapter;
import com.example.lenovo.textbeijing.base.BaseFragment;
import com.example.lenovo.textbeijing.base.basePager;
import com.example.lenovo.textbeijing.pager.GovaffairPager;
import com.example.lenovo.textbeijing.pager.HomePager;
import com.example.lenovo.textbeijing.pager.NewsCenterPager;
import com.example.lenovo.textbeijing.pager.SettingPager;
import com.example.lenovo.textbeijing.pager.SmartServicePager;
import com.example.lenovo.textbeijing.util.LogUtil;
import com.example.lenovo.textbeijing.view.NoScrollViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

public class ContentFragment extends BaseFragment {

//    private TextView textView;
    @ViewInject(R.id.viewpager)
    private NoScrollViewPager viewpager;
    @ViewInject(R.id.rg_main)
    private RadioGroup rg_main;
    private ArrayList<basePager> basePagers;



    @Override
    public View initView() {
        LogUtil.e("右侧菜单初始化");
        View view = View.inflate(context, R.layout.content_fragment,null);
//        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
//        rg_main = (RadioGroup) view.findViewById(R.id.rg_main);
        x.view().inject(ContentFragment.this,view);
        return view;
    }
    @Override
    public void initData(){
        super.initData();
//        初始化五个页面，并放入集合中
        basePagers = new ArrayList<>();
        basePagers.add(new HomePager(context));
        basePagers.add(new NewsCenterPager(context));
        basePagers.add(new SmartServicePager(context));
        basePagers.add(new GovaffairPager(context));
        basePagers.add(new SettingPager(context));



//        textView.setText("正文数据");

        //设置适配器
        viewpager.setAdapter(new ContentFragmentAdapter(basePagers));
        rg_main.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        viewpager.addOnPageChangeListener(new MyOnPageChangeListener());
        rg_main.check(R.id.rb_home);
        basePagers.get(0).initData();
        isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);




    }
//得到新闻中心
    public NewsCenterPager getNewsCenterPager() {
        return (NewsCenterPager) basePagers.get(1);
    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffserPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            basePagers.get(position).initData();

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
    private class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb_home:
                    viewpager.setCurrentItem(0,false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
                case R.id.rb_newcenter:
                    viewpager.setCurrentItem(1,false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_FULLSCREEN);
                    break;
                case R.id.rb_smartservice:
                    viewpager.setCurrentItem(2,false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
                case R.id.rb_govaffair:
                    viewpager.setCurrentItem(3,false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
                case R.id.rb_setting:
                    viewpager.setCurrentItem(4,false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
            }
        }
    }

    private void isEnableSlidingMenu(int touchmodeFullscreen) {
        MainActivity mainActivity = (MainActivity) context;
        mainActivity.getSlidingMenu().setTouchModeAbove(touchmodeFullscreen);
    }

//    class ContentFragmentAdapter extends PagerAdapter{
//
//        @Override
//        public int getCount() {
//            return basePagers.size();
//        }
//
//        @NonNull
//        @Override
//        public Object instantiateItem(@NonNull ViewGroup container, int position) {
//            basePager basePager = basePagers.get(position);
//            View rootView = basePager.rootView;
////            basePager.initData();
//            container.addView(rootView);
//            return rootView;
//
//
////            return super.instantiateItem(container, position);
//        }
//
//
//
//        @Override
//        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
//            return view==object;
//        }
//        @Override
//        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//            container.removeView((View) object);
//        }
//    }



}
