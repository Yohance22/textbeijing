package com.example.lenovo.textbeijing.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lenovo.textbeijing.R;
import com.example.lenovo.textbeijing.activity.MainActivity;
import com.example.lenovo.textbeijing.base.BaseFragment;
import com.example.lenovo.textbeijing.domain.NewsCenterPagerBean2;
import com.example.lenovo.textbeijing.pager.NewsCenterPager;
import com.example.lenovo.textbeijing.util.DensityUtil;
import com.example.lenovo.textbeijing.util.LogUtil;

import java.util.List;

public class LeftmenuFragment extends BaseFragment {

    private List<NewsCenterPagerBean2.DetailPagerData> data;
    private ListView listView;
    private LeftmenuFragmentAdapter adapter;
    private int prePosition;

    @Override
    public View initView() {
        LogUtil.e("右侧菜单初始化");
        listView = new ListView(context);
        listView.setPadding(0, DensityUtil.dip2px(context, 40), 0, 0);
        listView.setDividerHeight(0);//设置分割线高度为0
        listView.setCacheColorHint(Color.TRANSPARENT);
        //设置按下listview的item不变色

        listView.setSelector(android.R.color.transparent);
        //设置item的点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //1.点击的位置，点击之后变成红色
                prePosition = position;
                adapter.notifyDataSetChanged();
                //2.把左侧菜单关闭
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.getSlidingMenu().toggle();
                //3.切换到相应的切换页面，新闻、专题、组图、互动详情页面
                swichPager(prePosition);


            }
        });

        return listView;
    }

    //根据不同位置调取不同的页面
    private void swichPager(int position) {
        MainActivity mainActivity = (MainActivity) context;
        ContentFragment contentFragment = mainActivity.getContentFragment();
        NewsCenterPager newsCenterPager = contentFragment.getNewsCenterPager();
        newsCenterPager.swichPager(position);

    }


    @Override
    public void initData() {
        super.initData();
        LogUtil.e("右侧数据初始化");

    }

    //接收数据
    public void setData(List<NewsCenterPagerBean2.DetailPagerData> data) {
        this.data = data;
        for (int i = 0; i < data.size(); i++) {
            LogUtil.e("title=" + data.get(i).getTitle());

        }
        //设置是适配器
        adapter = new LeftmenuFragmentAdapter();
        listView.setAdapter(adapter);
        swichPager(prePosition);

    }

    class LeftmenuFragmentAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = (TextView) View.inflate(context, R.layout.item_leftmenu, null);
            textView.setText(data.get(position).getTitle());
            textView.setEnabled(position == prePosition);
            return textView;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

    }

}
