package com.example.lenovo.textbeijing.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.textbeijing.base.basePager;

import java.util.ArrayList;

public class ContentFragmentAdapter extends PagerAdapter {
    private final ArrayList<basePager> basePagers;

    public ContentFragmentAdapter(ArrayList<basePager> basePagers){
        this.basePagers = basePagers;
    }

        @Override
        public int getCount() {
            return basePagers.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            basePager basePager = basePagers.get(position);
            View rootView = basePager.rootView;
//            basePager.initData();
            container.addView(rootView);
            return rootView;


//            return super.instantiateItem(container, position);
        }



        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;
        }
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

