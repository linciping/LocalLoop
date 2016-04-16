package com.mengyou.localloop.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.mengyou.library.module.BaseFragment;
import com.mengyou.localloop.CreateDynamicActivity;
import com.mengyou.localloop.R;
import com.mengyou.localloop.adapter.MyFragmentAdapter;
import com.mengyou.localloop.viewbuilder.DialogBuilder;
import com.mengyou.localloop.viewbuilder.PopupWindowBuilder;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 * 人脉圈页面（组）
 */
public class DiscoverGroupFragment extends BaseFragment {

    private static final int RESULT_OK = -1;
    private List<String> mTitles;
    private List<Fragment> mFragments;
    private boolean isFrist = true;
    private Button btn_take_photo, btn_pick_photo, btn_cancel;
    private AlertDialog dialog;

    @Override
    public boolean getAllowEnterTransitionOverlap() {
        return super.getAllowEnterTransitionOverlap();
    }

    public DiscoverGroupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitles = new ArrayList<>();
        mFragments = new ArrayList<>();
        mContext = getActivity();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mTitles.add("人脉圈");
        mTitles.add("附近的人");
        mFragments.add(new DynamicFragment());
        mFragments.add(new NearPeopleFragment());
        final View view = inflater.inflate(R.layout.fragment_activity_group, container, false);
        ViewPager pager = (ViewPager) view.findViewById(R.id.activity_pager);
        TabLayout tab = (TabLayout) view.findViewById(R.id.activity_tab);
        final ImageView bar_btn = (ImageView) view.findViewById(R.id.bar_btn_icon);
        tab.setTabsFromPagerAdapter(new MyFragmentAdapter(getChildFragmentManager(), mTitles, mFragments));
        TabLayout.TabLayoutOnPageChangeListener listener = new TabLayout.TabLayoutOnPageChangeListener(tab);
        pager.addOnPageChangeListener(listener);
        pager.setAdapter(new MyFragmentAdapter(getChildFragmentManager(), mTitles, mFragments));
        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                    isFrist = true;
                    bar_btn.setImageResource(R.drawable.barbuttoniconcamera);
                } else {
                    isFrist = false;
                    bar_btn.setImageResource(R.drawable.barbuttoniconmore);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        bar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFrist){
                    toOtherActivity(CreateDynamicActivity.class);
                }
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTitles=null;
        mFragments=null;
    }
}
