package com.mengyou.localloop.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.mengyou.library.module.BaseFragment;
import com.mengyou.library.util.NormalTools;
import com.mengyou.library.view.SideBar;
import com.mengyou.localloop.R;
import com.mengyou.localloop.adapter.ContactsAdapter;
import com.mengyou.localloop.model.User;
import com.mengyou.localloop.viewbuilder.DialogBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 * 联系人页面
 */
public class ContactsFragment extends BaseFragment {

    private SwipeRefreshLayout refreshLayout;
    private StickyListHeadersListView listView;
    private ContactsAdapter adapter;
    private SideBar sideBar;
    private TextView txt_key;
    private Map<String, Integer> maps;
    private AlertDialog dialog;

    public ContactsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return initView(inflater, container);
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        dialog= DialogBuilder.loadDialog(mContext,"正在加载......");
        dialog.show();
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_contacts);
        TextView txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setText("联系人");
        listView = (StickyListHeadersListView) view.findViewById(R.id.list_contacts);
//        listView.setAdapter(adapter = new ContactsAdapter(mDatas, mContext, inflater));
        initData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        txt_key = (TextView) view.findViewById(R.id.tv_key);
        sideBar = (SideBar) view.findViewById(R.id.kp_keys);

        sideBar.setOnKeySelectedListener(new SideBar.OnKeySelectedListener() {
            @Override
            public void onSelected(String key) {
                txt_key.setText(key);
                listView.smoothScrollToPositionFromTop(maps.get(key), 0);
            }

            @Override
            public void onDown() {
                txt_key.setVisibility(View.VISIBLE);
                sideBar.setBackgroundColor(Color.parseColor("#9e9e9e"));
            }

            @Override
            public void onUp() {
                txt_key.setVisibility(View.GONE);
                sideBar.setBackgroundColor(Color.TRANSPARENT);
            }
        });
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        User user = AVUser.getCurrentUser(User.class);
        AVRelation<User> relation = user.getRelation("friends");
        relation.getQuery().orderByAscending("pinyinindex").
                findInBackground(new FindCallback<User>() {
                    @Override
                    public void done(List<User> list, AVException e) {
                        if (list != null) {
                            listView.setAdapter(adapter = new ContactsAdapter(list, mContext));
                            int size = list.size();
                            maps = new HashMap<String, Integer>();
                            List<String> datas = new ArrayList<String>();
                            for (int i = 0; i < size; i++) {
                                boolean isAdd = true;
                                String value = list.get(i).getPinyinindex().substring(0, 1);
                                Set<String> set = maps.keySet();
                                if (set.size() == 0) {
                                    isAdd = true;
                                } else {
                                    for (String key : set) {
                                        if (value.equals(key)) {
                                            isAdd = false;
                                            break;
                                        }
                                    }
                                }
                                if (isAdd) {
                                    if (NormalTools.isNumeric(value)) {
                                        datas.add("#");
                                        maps.put("#", i);
                                    } else {
                                        value = value.toUpperCase();
                                        datas.add(value);
                                        maps.put(value, i);
                                    }
                                }
                            }
                            sideBar.setEntriesList(datas);
                            dialog.dismiss();
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        maps = null;
        adapter = null;
    }
}
