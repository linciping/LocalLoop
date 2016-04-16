package com.mengyou.localloop.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/3/7.
 */
public class MyFragmentAdapter extends FragmentPagerAdapter {

    private List<String> mTitls;
    private List<Fragment> mFragments;

    public MyFragmentAdapter(FragmentManager fm, List<String> mTitls, List<Fragment> mFragments) {
        super(fm);
        this.mTitls = mTitls;
        this.mFragments = mFragments;
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
        return mTitls.get(position);
    }
}
