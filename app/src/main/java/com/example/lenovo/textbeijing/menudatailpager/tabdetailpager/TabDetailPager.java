package com.example.lenovo.textbeijing.menudatailpager.tabdetailpager;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.example.lenovo.textbeijing.R;
import com.example.lenovo.textbeijing.base.MenuDetailBasePager;
import com.example.lenovo.textbeijing.domain.NewsCenterPagerBean2;
import com.example.lenovo.textbeijing.domain.TabDetailPagerBean;
import com.example.lenovo.textbeijing.util.CacheUtils;
import com.example.lenovo.textbeijing.util.Constants;
import com.example.lenovo.textbeijing.util.DensityUtil;
import com.example.lenovo.textbeijing.util.LogUtil;
import com.example.lenovo.textbeijing.view.HorizontalScrollViewPager;
import com.example.lenovo.textbeijing.view.RefreshListview;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

//页签详情页面
public class TabDetailPager extends MenuDetailBasePager {

    private int pullDownRefreshHeight;
    private static final int pull_down_refresh = 0 ;
    private static final int release_refresh = 1 ;
    private static final int refreshing = 2 ;
    private int currentStatus = pull_down_refresh;

    private ImageOptions imageOptions;
    private HorizontalScrollViewPager viewpager;
    private TextView tv_title;
    private LinearLayout ll_point_group;
    private RefreshListview  listview;
    private TabDetailPagerListAdapter adapter;
    private final NewsCenterPagerBean2.DetailPagerData.ChildrenData childrenData;
    /*    private TextView textView;*/
    private String url;//成员变量
    //顶部轮播图部分的新闻的数据
    private List<TabDetailPagerBean.DataEntity.TopnewsData> topnews;
    /*新闻列表数据 的集合*/
    private List<TabDetailPagerBean.DataEntity.NewsData> news;

    public TabDetailPager(Context context, NewsCenterPagerBean2.DetailPagerData.ChildrenData childrenData) {
        super(context);
        this.childrenData = childrenData;
        imageOptions = new ImageOptions.Builder()
                .setSize(org.xutils.common.util.DensityUtil.dip2px(100), org.xutils.common.util.DensityUtil.dip2px(100))
                .setRadius(org.xutils.common.util.DensityUtil.dip2px(5))
                // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setCrop(true) // 很多时候设置了合适的scaleType也不需要它.
                // 加载中或错误图片的ScaleType
                //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.drawable.news_pic_default)
                .setFailureDrawableId(R.drawable.news_pic_default)
                .build();
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.tabdetail_pager, null);
        listview = (RefreshListview ) view.findViewById(R.id.listview);

        View topNewsView = View.inflate(context,R.layout.topnews,null);

        viewpager = (HorizontalScrollViewPager) topNewsView.findViewById(R.id.viewpager);
        tv_title = (TextView) topNewsView.findViewById(R.id.tv_title);
        ll_point_group = (LinearLayout) topNewsView.findViewById(R.id.ll_point_group);

        //把顶部轮播图视图以头的方式添加到listview中
        listview.addHeaderView(topNewsView);
        return view;
    }
    @Override
    public void initData() {
        super.initData();
        url = Constants.BASE_URL + childrenData.getUrl();
        //把之前缓存的数据取出
        String saveJson = CacheUtils.getString(context, url);
        if (!TextUtils.isEmpty(saveJson)) {
            //解析数据
            processData(saveJson);
        }
        //LogUtil.e(childrenData.getTitle()+"的联网地址=="+url);
        //联网请求数据
        getDataFromNet();
    }

    private void getDataFromNet() {
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //缓存数据
                CacheUtils.putString(context, url, result);
                LogUtil.e(childrenData.getTitle() + "页面数据请求成功" + result);
                //解析和显示处理数据
                processData(result);
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e(childrenData.getTitle() + "页面数据请求失败" + ex.getMessage());
            }
            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e(childrenData.getTitle() + "页面数据请求onCancelled==" + cex.getMessage());
            }
            @Override
            public void onFinished() {
                LogUtil.e(childrenData.getTitle() + "页面数据请求onFinished==");
            }
        });
    }
    //之前点高亮显示的位置
    private int prePosition;
    private void processData(String json) {
        TabDetailPagerBean bean = parsedJson(json);
        LogUtil.e(childrenData.getTitle() + "解析成功" + bean.getData().getNews().get(0).getTitle());
        //顶部轮播图数据
        topnews = bean.getData().getTopnews();
        //设置viewpager的适配器
        viewpager.setAdapter(new TabDetailPagerTopNewsAdapter());
        //添加红点
        addPoint();
        //监听页面变化，，设置红点变化和文本变化
        viewpager.addOnPageChangeListener(new MyOnPageChangeListener());
        tv_title.setText(topnews.get(prePosition).getTitle());
        //准备listview的集合数据
        news = bean.getData().getNews();
        //设置listview的适配器
        adapter = new TabDetailPagerListAdapter();
        listview.setAdapter(adapter);
    }
    class TabDetailPagerListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return news.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;

            if (convertView == null){
                convertView = View.inflate(context,R.layout.item_tabdetail_pager,null);
                viewHolder = new ViewHolder();
                viewHolder.iv_icon = (ImageView)convertView.findViewById(R.id.iv_icon);
                viewHolder.tv_title = (TextView)convertView.findViewById(R.id.tv_title);
                viewHolder.tv_time = (TextView)convertView.findViewById(R.id.tv_time);
                convertView.setTag(viewHolder);


            }else {
                viewHolder = (ViewHolder) convertView.getTag();

            }

            //根据位置得到数据
            //52-1633
            TabDetailPagerBean.DataEntity.NewsData newsData = news.get(position);
            String imageUrl = Constants.BASE_URL +newsData.getListimage();
            //请求图片xutil3
            x.image().bind(viewHolder.iv_icon,imageUrl,imageOptions);
            //现在改为glide

/*            Glide.with(context)
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(viewHolder.iv_icon);*/
            viewHolder.tv_title.setText(newsData.getTitle());
            viewHolder.tv_time.setText(newsData.getPubdate());
            return convertView;
        }
    }
    static class ViewHolder{
        ImageView iv_icon;
        TextView tv_title;
        TextView tv_time;


    }


    private void addPoint() {
        ll_point_group.removeAllViews();//移除所有的红点

        for (int i = 0; i < topnews.size(); i++) {
            ImageView imageView = new ImageView(context);
            imageView.setBackgroundResource(R.drawable.point_selector);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(org.xutils.common.util.DensityUtil.dip2px(8),
                    org.xutils.common.util.DensityUtil.dip2px(8));

            if (i==0){
                imageView.setEnabled(true);
            }else {
                imageView.setEnabled(false);
                params.leftMargin  = org.xutils.common.util.DensityUtil.dip2px(8);
            }


            imageView.setLayoutParams(params);
            ll_point_group.addView(imageView);


        }
    }
    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //1、设置文本
            tv_title.setText(topnews.get(position).getTitle());
            //2、对应页面的点--高亮红色(1)把之前的设置灰色
            ll_point_group.getChildAt(prePosition).setEnabled(false);
            ll_point_group.getChildAt(position).setEnabled(true);
            prePosition=position;



        }

        @Override
        public void onPageScrollStateChanged(int position) {

        }
    }
    class TabDetailPagerTopNewsAdapter extends PagerAdapter {
        @NonNull
        @Override
        public int getCount() {
            return topnews.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = new ImageView(context);
            imageView.setBackgroundResource(R.drawable.home_scroll_default);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(imageView);
            TabDetailPagerBean.DataEntity.TopnewsData topnewsData = topnews.get(position);
            String imageUrl = Constants.BASE_URL + topnewsData.getTopimage();
            //联网请求图片
            x.image().bind(imageView, imageUrl,imageOptions);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            //super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }
    private TabDetailPagerBean parsedJson(String json) {
        return new Gson().fromJson(json, TabDetailPagerBean.class);

    }
}
