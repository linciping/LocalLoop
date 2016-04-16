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
import com.avos.avoscloud.AVGeoPoint;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.mengyou.library.module.BaseFragment;
import com.mengyou.localloop.R;
import com.mengyou.localloop.adapter.NearPeopleAdapter;
import com.mengyou.localloop.model.NearPeople;
import com.mengyou.localloop.model.User;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 * 附近的人页面
 */
public class NearPeopleFragment extends BaseFragment {

    private NearPeopleAdapter adapter;
    private List<NearPeople> mDatas;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    public NearPeopleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
        initData();
    }

    @Override
    protected void initData() {
        super.initData();
        mDatas=new ArrayList<>();
        final AVGeoPoint point= User.getCurrentUser(User.class).getAVGeoPoint("location");
        AVQuery<User> query=User.getQuery(User.class);
        query.whereNear("location",point);
        query.findInBackground(new FindCallback<User>() {
            @Override
            public void done(final List<User> list, AVException e) {
                recyclerView.setAdapter(adapter = new NearPeopleAdapter(list, mContext, point));
                recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).build());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      return initView(inflater,container);
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view=inflater.inflate(R.layout.fragment_near_people, container, false);
        recyclerView= (RecyclerView) view.findViewById(R.id.recyler_nearpeople);
        refreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.refresh_nearpeople);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mDatas=null;
        adapter=null;
    }
}
