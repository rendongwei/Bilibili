package com.don.bilibili.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class TabAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;
    private String[] mTitle;

    public TabAdapter(FragmentManager fm, List<Fragment> fragments,String... titles) {
        super(fm);
        mFragments = fragments;
        mTitle = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }
}
