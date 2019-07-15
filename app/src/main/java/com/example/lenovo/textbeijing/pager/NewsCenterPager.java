package com.example.lenovo.textbeijing.pager;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.lenovo.textbeijing.activity.MainActivity;
import com.example.lenovo.textbeijing.base.MenuDetailBasePager;
import com.example.lenovo.textbeijing.base.basePager;
import com.example.lenovo.textbeijing.domain.NewsCenterPagerBean2;
import com.example.lenovo.textbeijing.fragment.LeftmenuFragment;
import com.example.lenovo.textbeijing.menudatailpager.InteracMenuDetailPager;
import com.example.lenovo.textbeijing.menudatailpager.NewsMenuDetailPager;
import com.example.lenovo.textbeijing.menudatailpager.PhotosMenuDetailPager;
import com.example.lenovo.textbeijing.menudatailpager.TopicMenuDetailPager;
import com.example.lenovo.textbeijing.util.CacheUtils;
import com.example.lenovo.textbeijing.util.Constants;
import com.example.lenovo.textbeijing.util.LogUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class NewsCenterPager extends basePager {
    //左侧菜单对应的数据集合
    private List<NewsCenterPagerBean2.DetailPagerData> data;
    //详情页面的集合
    private ArrayList<MenuDetailBasePager> detailBasePagers;

    public NewsCenterPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        ib_menu.setVisibility(View.VISIBLE);
//        1、设置标题
        tv_title.setText("新闻中心");
        TextView textView = new TextView(context);
        textView.setTextSize(25);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        fl_content.addView(textView);
        textView.setText("新闻中心");
        /*获取缓存数据*/
        String saveJson = CacheUtils.getString(context,Constants.NEWSCENTER_PAGER_URL);
        if (!TextUtils.isEmpty(saveJson)){
            procressData(saveJson);
        }


        //联网请求数据
        getDataFromNet();
    }

    /*
     *使用xutil联网请求数据
     */
    private void getDataFromNet() {
        RequestParams params = new RequestParams(Constants.NEWSCENTER_PAGER_URL);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("使用xutils3联网请求成功" + result);

                //缓存数据
                CacheUtils.putString(context,Constants.NEWSCENTER_PAGER_URL,result);
                procressData(result);
                //设置适配器


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("使用xutils3联网请求失败" + ex.getMessage());

            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("使用xutils3联网onCancelled" + cex.getMessage());


            }

            @Override
            public void onFinished() {
                LogUtil.e("使用xutils3联网onFinished");

            }
        });

    }
    private void procressData(String json) {

//        NewsCenterPagerBean bean = parsedJson(json);
        NewsCenterPagerBean2 bean = parsedJson2(json);
//        String title = bean.getData().get(0).getChildren().get(1).getTitle();


//        LogUtil.e("使用Gson解析json数据成功-title==" + title);
        String title2 = bean.getData().get(0).getChildren().get(1).getTitle();
        LogUtil.e("使用Gson解析json数据成功NewsCenterPagerBean2-title2-------------------------==" + title2);
        //给左侧菜单传递数据
        data = bean.getData();

        MainActivity mainActivity = (MainActivity) context;
        //得到左侧菜单
        LeftmenuFragment leftmenuFragment = mainActivity.getLeftmenuFragment();

        //添加详情页面

        detailBasePagers = new ArrayList<>();
        detailBasePagers.add(new NewsMenuDetailPager(context,data.get(0)));//新闻详情页面
        detailBasePagers.add(new TopicMenuDetailPager(context));//专题详情页面
        detailBasePagers.add(new PhotosMenuDetailPager(context));//图组详情页面
        detailBasePagers.add(new InteracMenuDetailPager(context));//互动详情页面

        //把数据传递给左侧菜单
        leftmenuFragment.setData(data);


    }
/*    //解析JSON数据和显示数据
    private void procressData(String json) {
        //NewsCenterPagerBean bean = parsedJson(json);
        NewsCenterPagerBean2 bean = parsedJson2(json);


        //String title = bean.getData().get(0).getChildren().get(1).getTitle();
        //LogUtil.e("使用gson解析数据成功==" + title);
        String title2 = bean.getData().get(0).getChildren().get(1).getTitle();
        LogUtil.e("使用gson解析数据成功==" + title2);


        //给左侧菜单传递数据,要传递参数要先得到
        data = bean.getData();
        MainActivity mainActivity = (MainActivity) context;
        //得到左侧菜单
        LeftmenuFragment leftmenuFragment = mainActivity.getLeftmenuFragment();
        //添加详情页面
        detailBasePagers = new ArrayList<>();
        detailBasePagers.add(new NewsMenuDetailPager(context));
        detailBasePagers.add(new TopicMenuDetailPager(context));
        detailBasePagers.add(new PhotosMenuDetailPager(context));
        detailBasePagers.add(new InteracMenuDetailPager(context));
        //把数据传递给左侧菜单
        leftmenuFragment.setData(data);


    }*/
    private NewsCenterPagerBean2 parsedJson2(String json) {
        NewsCenterPagerBean2 bean2 = new NewsCenterPagerBean2();
        try {
            JSONObject object = new JSONObject(json);


            int retcode = object.optInt("retcode");
            bean2.setRetcode(retcode);//retcode字段解析成功

            JSONArray data = object.optJSONArray("data");
            if (data != null && data.length() > 0) {

                List<NewsCenterPagerBean2.DetailPagerData> detailPagerDatas = new ArrayList<>();
                //设置列表数据
                bean2.setData(detailPagerDatas);
                //for循环，解析每条数据
                for (int i = 0; i < data.length(); i++) {

                    JSONObject jsonObject = (JSONObject) data.get(i);

                    NewsCenterPagerBean2.DetailPagerData detailPagerData = new NewsCenterPagerBean2.DetailPagerData();
                    //添加到集合中
                    detailPagerDatas.add(detailPagerData);

                    int id = jsonObject.optInt("id");
                    detailPagerData.setId(id);
                    int type = jsonObject.optInt("type");
                    detailPagerData.setType(type);
                    String title = jsonObject.optString("title");
                    detailPagerData.setTitle(title);
                    String url = jsonObject.optString("url");
                    detailPagerData.setUrl(url);
                    String url1 = jsonObject.optString("url1");
                    detailPagerData.setUrl1(url1);
                    String dayurl = jsonObject.optString("dayurl");
                    detailPagerData.setDayurl(dayurl);
                    String excurl = jsonObject.optString("excurl");
                    detailPagerData.setExcurl(excurl);
                    String weekurl = jsonObject.optString("weekurl");
                    detailPagerData.setWeekurl(weekurl);


                    JSONArray children = jsonObject.optJSONArray("children");
                    if (children != null && children.length() > 0) {

                        List<NewsCenterPagerBean2.DetailPagerData.ChildrenData> childrenDatas  = new ArrayList<>();

                        //设置集合-ChildrenData
                        detailPagerData.setChildren(childrenDatas);

                        for (int j = 0; j < children.length(); j++) {
                            JSONObject childrenitem = (JSONObject) children.get(j);

                            NewsCenterPagerBean2.DetailPagerData.ChildrenData childrenData = new NewsCenterPagerBean2.DetailPagerData.ChildrenData();
                            //添加到集合中
                            childrenDatas.add(childrenData);


                            int childId = childrenitem.optInt("id");
                            childrenData.setId(childId);
                            String childTitle = childrenitem.optString("title");
                            childrenData.setTitle(childTitle);
                            String childUrl = childrenitem.optString("url");
                            childrenData.setUrl(childUrl);
                            int childType = childrenitem.optInt("type");
                            childrenData.setType(childType);

                        }

                    }


                }


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return bean2;
    }
/*
    //使用android系统自带的API解析json数据
    private NewsCenterPagerBean2 parsedJson2(String json) {
        NewsCenterPagerBean2 bean2 = new NewsCenterPagerBean2();
        try {
            JSONObject object = new JSONObject(json);

            int retcode = object.optInt("retcode");
            bean2.setRetcode(retcode);//retcode这个字段就解析成功了

            JSONArray data = object.optJSONArray("data");
            if (data != null && data.length() > 0) {
                List<NewsCenterPagerBean2.DetailPagerData>detailPagerDatas = new ArrayList<>();
                //设置列表数据
                bean2.setData(detailPagerDatas);


                //for循环，解析每一条数据
                for (int i =0;i<data.length();i++){
                    JSONObject jsonObject = (JSONObject) data.get(i);
                    NewsCenterPagerBean2.DetailPagerData detailPagerData = new NewsCenterPagerBean2.DetailPagerData();
                    //添加到集合中去
                    detailPagerDatas.add(detailPagerData);


                    int id =jsonObject.optInt("id");
                    detailPagerData.setId(id);
                    int type =jsonObject.optInt("type");
                    detailPagerData.setType(type);
                    String title = jsonObject.optString("title");
                    detailPagerData.setTitle(title);
                    String url = jsonObject.optString("url");
                    detailPagerData.setUrl(url);
                    String url1 = jsonObject.optString("url1");
                    detailPagerData.setUrl1(url1);
                    String dayurl = jsonObject.optString("dayurl");
                    detailPagerData.setDayurl(dayurl);
                    String exurl = jsonObject.optString("exurl");
                    detailPagerData.setExcurl(exurl);
                    String weekurl = jsonObject.optString("weekurl");
                    detailPagerData.setWeekurl(weekurl);



                    JSONArray children = jsonObject.optJSONArray("children");
                    if (children !=null&&children.length()>0){


                        List<NewsCenterPagerBean2.DetailPagerData.ChildrenData>childrenDatas = new ArrayList<>();
                        //设置集合
                        detailPagerData.setChildren(childrenDatas);

                        for (int j = 0;j<children.length();j++){
                            JSONObject childrenitem = (JSONObject) children.get(j);
                            NewsCenterPagerBean2.DetailPagerData.ChildrenData childrenData = new NewsCenterPagerBean2.DetailPagerData.ChildrenData();
                            childrenDatas.add(childrenData);

                            int childId = childrenitem.optInt("id");
                            childrenData.setId(childId);
                            int childType = childrenitem.optInt("type");
                            childrenData.setType(type);

                            String childTitle = childrenitem.optString("title");
                            childrenData.setTitle(title);
                            String childUrl  = childrenitem.optString("url");
                            childrenData.setUrl(url);

                        }

                    }

                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return bean2;

    }*/

    //解析json数据
    //1、使用系统法人API解析json
    //2、使用第三方框架解析json数据。例如Gson，fastjson（阿里巴巴）
    //
    private NewsCenterPagerBean2 parsedJson(String json) {
        //Gson gson = new Gson();
        //NewsCenterPagerBean bean = gson.fromJson(json, NewsCenterPagerBean.class);


        return new Gson().fromJson(json, NewsCenterPagerBean2.class);
    }

    //切换位置position详情页面
    public void swichPager(int position) {
        //1设置标题
        tv_title.setText(data.get(position).getTitle());
        //移除之前的内容
        fl_content.removeAllViews();
        //添加新内容
        MenuDetailBasePager detailBasePager = detailBasePagers.get(position);
        View rootView = detailBasePager.rootView;
        detailBasePager.initData();//初始化数据
        fl_content.addView(rootView);

    }
}
