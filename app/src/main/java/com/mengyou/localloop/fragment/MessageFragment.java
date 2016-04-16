package com.mengyou.localloop.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mengyou.library.module.BaseFragment;
import com.mengyou.localloop.R;
import com.mengyou.localloop.adapter.ActivityAdapter;
import com.mengyou.localloop.adapter.MessageAdapter;
import com.mengyou.localloop.model.Activity;
import com.mengyou.localloop.model.Message;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 * 消息页面
 */
public class MessageFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private MessageAdapter adapter;
    private List<Message> mDatas;
    private TextView txt_title;

    public MessageFragment() {
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
        Message message=new Message();
        message.setIconUrl(R.drawable.icon1);
        message.setUsername("林赐平");
        mDatas.add(message);
        message=new Message();
        message.setIconUrl(R.drawable.icon2);
        message.setUsername("杨张旭");
        mDatas.add(message);
        message=new Message();
        message.setIconUrl(R.drawable.icon3);
        message.setUsername("林惠蓉");
        mDatas.add(message);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       return initView(inflater, container);
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view= inflater.inflate(R.layout.fragment_message, container, false);
        recyclerView= (RecyclerView) view.findViewById(R.id.recyler_message);
        txt_title= (TextView) view.findViewById(R.id.txt_title);
        refreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.refresh_message);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter = new MessageAdapter(mDatas, mContext, inflater));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).build());
        txt_title.setText("消息");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mDatas=null;
        adapter=null;
    }
}
