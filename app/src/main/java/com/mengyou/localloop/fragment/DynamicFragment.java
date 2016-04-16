package com.mengyou.localloop.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.mengyou.library.module.BaseFragment;
import com.mengyou.localloop.R;
import com.mengyou.localloop.adapter.DynamicAdapter;
import com.mengyou.localloop.model.AVDynamic;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 * 人脉圈页面
 */
public class DynamicFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    public DynamicFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    protected void initData() {
        super.initData();
        AVQuery<AVDynamic> query = AVQuery.getQuery(AVDynamic.class);
        query.orderByDescending("updatedAt");
        query.findInBackground(new FindCallback<AVDynamic>() {
            @Override
            public void done(List<AVDynamic> list, AVException e) {
                recyclerView.setAdapter(new DynamicAdapter(list,mContext));
                recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).build());
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
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_dynamic, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyler_dynamic);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_dynamic);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        initData();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
