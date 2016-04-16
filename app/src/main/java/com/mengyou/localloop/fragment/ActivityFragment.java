package com.mengyou.localloop.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.mengyou.library.module.BaseFragment;
import com.mengyou.localloop.R;
import com.mengyou.localloop.adapter.ActivityAdapter;
import com.mengyou.localloop.model.AVActivity;

import java.util.List;

/**
 * 活动页面
 */
public class ActivityFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    public ActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        mContext=getActivity();
    }

    @Override
    protected void initData() {
        super.initData();
        AVQuery<AVActivity> query= AVObject.getQuery(AVActivity.class);
        query.orderByDescending("updatedAt");
        query.findInBackground(new FindCallback<AVActivity>() {
            @Override
            public void done(List<AVActivity> list, AVException e) {
                recyclerView.setAdapter(new ActivityAdapter(list, mContext));
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return initView(inflater, container);
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.fragment_activity, parent, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyler_activity);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_activity);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        initData();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
