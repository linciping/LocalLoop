package com.mengyou.localloop;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.GetFileCallback;
import com.avos.avoscloud.SaveCallback;
import com.mengyou.library.module.BaseActivity;
import com.mengyou.library.util.MyImageLoader;
import com.mengyou.library.util.StatusBarBulider;
import com.mengyou.localloop.adapter.GridImageAdapter;
import com.mengyou.localloop.adapter.ImageAdapter;
import com.mengyou.localloop.fragment.ActivityFragment;
import com.mengyou.localloop.model.AVActivity;
import com.mengyou.localloop.model.User;
import com.mengyou.localloop.viewbuilder.DialogBuilder;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author linciping
 * @version 1.0
 * @see ActivityFragment
 * 活动详情
 */
public class InfoActivity extends BaseActivity {

    //region 控件注入
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.btn_signup)
    Button btnSignup;
    @Bind(R.id.nav_menu)
    LinearLayout navMenu;
    @Bind(R.id.image_icon)
    ImageView imageIcon;
    @Bind(R.id.txt_username)
    TextView txtUsername;
    @Bind(R.id.image_sex)
    ImageView imageSex;
    @Bind(R.id.btn_level)
    Button btnLevel;
    @Bind(R.id.btn_state)
    Button btnState;
    @Bind(R.id.txt_header_info)
    TextView txtHeaderInfo;
    @Bind(R.id.image_type)
    ImageView imageType;
    @Bind(R.id.txt_name)
    TextView txtName;
    @Bind(R.id.txt_info)
    TextView txtInfo;
    @Bind(R.id.image_info)
    ImageView imageInfo;
    @Bind(R.id.recyler_info_images)
    GridView recylerInfoImages;
    @Bind(R.id.txt_count)
    TextView txtCount;
    @Bind(R.id.recyler_photo)
    RecyclerView recylerPhoto;
    //endregion

    private ImageAdapter imageAdapter;//图片适配器
    private List<String> imageUrls;//已报名用户图片资源
    private List<String> contentPics;//内容图片资源
    private List<User> users = new ArrayList<>();//已报名的用户集合
    private AVActivity activity;//活动信息对象
    private GridImageAdapter adapter;//图片网格适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ButterKnife.bind(this);
        mContext = this;
        if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT)
        {
            StatusBarBulider.setStatusBarForKITKAT(this, R.color.colorPrimaryDark);
        }
        TAG = getClass().getSimpleName();
        initData();
    }

    @Override
    protected void initData() {
        super.initData();
        final String targetId = getIntent().getStringExtra("targetId");
        AVQuery<AVActivity> query = AVQuery.getQuery(AVActivity.class);
        query.getInBackground(targetId, new GetCallback<AVActivity>() {
            @Override
            public void done(AVActivity avActivity, AVException e) {
                Log.e("data", avActivity.toString());
                activity = avActivity;
                initView();
            }
        });
        imageUrls = new ArrayList<>();
    }

    @Override
    protected void initView() {
        super.initView();
        contentPics = new ArrayList<>();
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //region基础设置
        activity.getAVObject("creater").fetchIfNeededInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                User user = (User) avObject;
                txtUsername.setText(user.getNickName());
                btnLevel.setText("v" + user.getRank());
                if (user.getSex() == 1) {
                    imageSex.setImageResource(R.drawable.icon_man);
                } else {
                    imageSex.setImageResource(R.drawable.icon_woman);
                }
                ImageLoader.getInstance().displayImage(user.getAVFile("icon").getUrl(), imageIcon, MyImageLoader.newOption());
            }
        });

        if (activity.getState() == 1) {
            btnState.setText("进行中");
        } else {
            btnState.setText("已结束");
        }
        switch (activity.getTheme()) {
            case 0:
                imageType.setImageResource(R.drawable.sport);
                txtName.setText("运动");
                break;
            case 1:
                imageType.setImageResource(R.drawable.video);
                txtName.setText("看电影");
                break;
            default:
                imageType.setImageResource(R.drawable.travel);
                txtName.setText("旅游");
                break;
        }
        setVisivility(txtInfo, activity.getContentStr() + "\n" + activity.getTime() + "\n" + activity.getAddr());
        recylerPhoto.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        //endregion

        //region 设置图片内容
        final List<AVObject> files = activity.getList("contentPic");
        if (files != null && files.size() > 0) {//根据图片文件的数量，来决定显示列表或是图片控件
            if (files.size() == 0) {
                imageInfo.setVisibility(View.VISIBLE);
                AVFile.withObjectIdInBackground(files.get(0).getObjectId(), new GetFileCallback<AVFile>() {
                    @Override
                    public void done(AVFile avFile, AVException e) {
                        ImageLoader.getInstance().displayImage(avFile.getUrl(), imageInfo, MyImageLoader.newOption());
                    }
                });
                recylerInfoImages.setVisibility(View.GONE);
            } else {
                imageInfo.setVisibility(View.GONE);
                recylerInfoImages.setVisibility(View.VISIBLE);
                for (int i = 0; i < files.size(); i++) {
                    AVFile.withObjectIdInBackground(files.get(i).getObjectId(), new GetFileCallback<AVFile>() {
                        @Override
                        public void done(AVFile avFile, AVException e) {
//                            ImageLoader.getInstance().displayImage(avFile.getUrl(), imageInfo, MyImageLoader.newOption());
                            contentPics.add(avFile.getUrl());
                            if (contentPics.size() == files.size()) {
                                recylerInfoImages.setAdapter(adapter = new GridImageAdapter(contentPics, mContext));
                                setListViewHeightBasedOnChildren(recylerInfoImages);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }
        } else {
            imageInfo.setVisibility(View.GONE);
            recylerInfoImages.setVisibility(View.GONE);
        }
        //endregion

        //region 显示已参加的用户
        final List<AVObject> objects = activity.getList("signupList");
        if (objects != null && objects.size() > 0) {
            for (int i = 0; i < objects.size(); i++) {
                AVQuery<User> query = AVQuery.getQuery(User.class);
                query.whereEqualTo("objectId", objects.get(i).getObjectId());
                query.findInBackground(new FindCallback<User>() {
                    @Override
                    public void done(List<User> list, AVException e) {
                        users = list;
                        imageUrls.add(list.get(0).getAVFile("icon").getUrl());
                        if (imageUrls.size() == objects.size()) {
                            recylerPhoto.setAdapter(imageAdapter = new ImageAdapter(imageUrls, mContext));
                            txtCount.setText(imageUrls.size() + ">");
                        }
                    }
                });
            }
        } else {
            txtCount.setText("暂时没有用户报名");
        }
        //endregion

        //报名按钮处理逻辑
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final User user = User.getCurrentUser(User.class);
                activity.getAVObject("creater").fetchIfNeededInBackground(new GetCallback<AVObject>() {

                    @Override
                    public void done(AVObject avObject, AVException e) {
                        User createUser = (User) avObject;
                        if (createUser.getObjectId().equals(user.getObjectId())) {
                            Toast.makeText(InfoActivity.this, "创建者无法报名", Toast.LENGTH_SHORT).show();
                        } else if (isHave(user.getObjectId())) {
                            Toast.makeText(InfoActivity.this, "你已经报名", Toast.LENGTH_SHORT).show();
                        } else {
                            activity.addUnique("signupList", user);
                            activity.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(AVException e) {
                                    if (e == null) {
                                        imageUrls.add(user.getAVFile("icon").getUrl());
                                        adapter.notifyDataSetChanged();
                                        Toast.makeText(InfoActivity.this, "成功报名", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.e("error", e.getMessage());
                                    }
                                }
                            });

                        }
                    }
                });
            }
        });
    }

    //regionGridView高度自适配

    /**
     * GridView高度自适配
     *
     * @param listView 需要适配高度GridView
     */
    public static void setListViewHeightBasedOnChildren(GridView listView) {
        // 获取listview的adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
        int col = 4;// listView.getNumColumns();
        int totalHeight = 0;
        // i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
        // listAdapter.getCount()小于等于8时计算两次高度相加
        for (int i = 0; i < listAdapter.getCount(); i += col) {
            // 获取listview的每一个item
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }

        // 获取listview的布局参数
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // 设置高度
        params.height = totalHeight;
        // 设置margin
        ((GridView.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        // 设置参数
        listView.setLayoutParams(params);
    }
    //endregion

    //region 根据值，设置文本的可见性

    /**
     * 根据值，设置文本的可见性
     *
     * @param textView 对象文本
     * @param value    值
     */
    public void setVisivility(TextView textView, String value) {
        if (value == null) {
            textView.setVisibility(View.GONE);
        } else {
            if (value.equals("")) {
                textView.setVisibility(View.GONE);
            } else {
                if (textView.getVisibility() == View.GONE) {
                    textView.setVisibility(View.VISIBLE);
                }
                textView.setText(value);
            }
        }
    }
    //endregion

    /**
     * 集合是否包含目标值
     *
     * @param value
     * @return
     */
    private boolean isHave(String value) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getObjectId().equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
        adapter = null;
        imageUrls = null;
    }
}
