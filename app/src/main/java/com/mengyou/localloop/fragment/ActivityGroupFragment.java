package com.mengyou.localloop.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mengyou.library.module.BaseFragment;
import com.mengyou.localloop.CreateActivity;
import com.mengyou.localloop.R;
import com.mengyou.localloop.adapter.MyFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 * 活动页面（组）
 */
public class ActivityGroupFragment extends BaseFragment {
    private List<String> mTitles;
    private List<Fragment> mFragments;

    public ActivityGroupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    protected void initData() {
        super.initData();
        mTitles = new ArrayList<>();
        mFragments = new ArrayList<>();
        mContext = getActivity();
        mTitles.add("活动");
        mTitles.add("浦城头条");
        mFragments.add(new ActivityFragment());
        mFragments.add(new HeadlineFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return initView(inflater, container);
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_activity_group, container, false);
        ViewPager pager = (ViewPager) view.findViewById(R.id.activity_pager);
        TabLayout tab = (TabLayout) view.findViewById(R.id.activity_tab);
        final ImageView bar_btn = (ImageView) view.findViewById(R.id.bar_btn_icon);
        bar_btn.setImageResource(R.drawable.barbuttoniconadd);
        tab.setTabsFromPagerAdapter(new MyFragmentAdapter(getChildFragmentManager(), mTitles, mFragments));
        TabLayout.TabLayoutOnPageChangeListener listener = new TabLayout.TabLayoutOnPageChangeListener(tab);
        pager.addOnPageChangeListener(listener);
        pager.setAdapter(new MyFragmentAdapter(getChildFragmentManager(), mTitles, mFragments));
        bar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toOtherActivity(CreateActivity.class);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mFragments=null;
        mTitles=null;
    }
}
