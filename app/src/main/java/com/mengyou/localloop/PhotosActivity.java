package com.mengyou.localloop;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.mengyou.library.module.BaseActivity;
import com.mengyou.localloop.adapter.PhotosAdapter;
import com.mengyou.localloop.model.AVDynamic;
import com.mengyou.localloop.model.DateModel;
import com.mengyou.localloop.model.Dymaic;
import com.mengyou.localloop.model.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 个人动态
 */
public class PhotosActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyler_photos)
    RecyclerView recylerPhotos;
    @Bind(R.id.refresh_photos)
    SwipeRefreshLayout refreshPhotos;
    private ArrayList<Dymaic> dymaics;
    private ArrayList<AVDynamic> dynamics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        ButterKnife.bind(this);
        mContext=this;
        initData();
        toolbar.setTitle("个人动态");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recylerPhotos.setLayoutManager(new LinearLayoutManager(this));
        recylerPhotos.setAdapter(new PhotosAdapter(dymaics, mContext));
    }

    @Override
    protected void initData() {
        super.initData();
        DateModel model=new DateModel();
        model.setYear(2016);
        model.setMonth(4);
        model.setDay(14);
        dymaics=new ArrayList<>();
        Dymaic dymaic=new Dymaic();
        dymaic.setTime(model);
        List<Integer> imageUrls=new ArrayList<>();
        imageUrls.add(R.drawable.icon1);
        imageUrls.add(R.drawable.icon2);
        imageUrls.add(R.drawable.icon3);
        dymaic.setImageUrls(imageUrls);
        dymaic.setContent("内容1");
        dymaic.setTime(model);
        dymaic.setShowTime(true);
        dymaics.add(dymaic);

        model=new DateModel();
        model.setYear(2016);
        model.setMonth(4);
        model.setDay(13);
        dymaic=new Dymaic();
        dymaic.setTime(model);
        imageUrls=new ArrayList<>();
        imageUrls.add(R.drawable.icon1);
        imageUrls.add(R.drawable.icon2);
        imageUrls.add(R.drawable.icon3);
        imageUrls.add(R.drawable.icon4);
        dymaic.setImageUrls(imageUrls);
        dymaic.setContent("内容2");
        dymaics.add(dymaic);

        model=new DateModel();
        model.setYear(2016);
        model.setMonth(4);
        model.setDay(12);
        dymaic=new Dymaic();
        dymaic.setTime(model);
        imageUrls=new ArrayList<>();
        imageUrls.add(R.drawable.icon1);
        imageUrls.add(R.drawable.icon2);
        dymaic.setImageUrls(imageUrls);
        dymaic.setContent("内容3");
        dymaics.add(dymaic);


        model=new DateModel();
        model.setYear(2016);
        model.setMonth(4);
        model.setDay(12);
        dymaic=new Dymaic();
        dymaic.setTime(model);
        imageUrls=new ArrayList<>();
        imageUrls.add(R.drawable.icon1);
        dymaic.setImageUrls(imageUrls);
        dymaic.setContent("内容4");
        dymaics.add(dymaic);

        AVQuery<AVDynamic> query=AVQuery.getQuery(AVDynamic.class);
        query.whereEqualTo("creater",AVUser.getCurrentUser(User.class));
        query.findInBackground(new FindCallback<AVDynamic>() {
            @Override
            public void done(List<AVDynamic> list, AVException e) {
                    Dymaic dymaic1=new Dymaic();
                    dymaic1.setTime(new DateModel(list.get(0).getCreatedAt()));
                    String dymaincId=list.get(0).getObjectId();
                    dymaic1.setDymaicId(dymaincId);
                    dymaic1.setContent(list.get(0).getContentStr());

            }
        });

        for (int i=1;i<dymaics.size();i++)
        {
            DateModel model1=dymaics.get(i-1).getTime();//前一条动态的时间
            DateModel model2=dymaics.get(i).getTime();//当条动态的时间
            if (model2.getDay()==model1.getDay()&&model2.getMonth()==model2.getMonth())
            {
                dymaics.get(i).setShowTime(false);
            }
            else
            {
                dymaics.get(i).setShowTime(true);
            }
        }
    }
}
