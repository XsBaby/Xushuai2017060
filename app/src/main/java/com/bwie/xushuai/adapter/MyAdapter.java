package com.bwie.xushuai.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bwie.xushuai.fragment.WeekFragment;

public class MyAdapter extends FragmentPagerAdapter {

    String[] TITLE = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};

    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        WeekFragment weekFragment = new WeekFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("title",TITLE[position]);
//        weekFragment.setArguments(bundle);

        return weekFragment;
    }

    @Override
    public int getCount() {
        return TITLE.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLE[position];
    }
}