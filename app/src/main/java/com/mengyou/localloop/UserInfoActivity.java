package com.mengyou.localloop;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.mengyou.library.module.BaseActivity;
import com.mengyou.library.util.StatusBarBulider;
import com.mengyou.localloop.adapter.UserInfoAdapter;
import com.mengyou.localloop.model.User;
import com.mengyou.localloop.viewbuilder.DialogBuilder;
import com.mengyou.localloop.viewbuilder.SelectCityDialog;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

/**
 * 个人中心
 */
public class UserInfoActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyler_userinfo)
    RecyclerView recylerUserinfo;

    private List<String> mTitles;
    private List<String> mDatas;
    private UserInfoAdapter adapter;
    private AlertDialog dialog;
    private User user;

    public final static int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        mContext = this;
        if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT)
        {
            StatusBarBulider.setStatusBarForKITKAT(this, R.color.colorPrimaryDark);
        }
        TAG=getClass().getName();
        initData();
        initView();
    }

    @Override
    protected void initData() {
        super.initData();
        mTitles = new ArrayList<>();
        mDatas = new ArrayList<>();
        user= AVUser.getCurrentUser(User.class);
        mTitles.add("头像");
        mTitles.add("名字");
        mTitles.add("ID");
        mTitles.add("性别");
        mTitles.add("地区");
        mTitles.add("个性签名");
        mDatas.add(Integer.toString(R.mipmap.ic_launcher));
        mDatas.add(user.getNickName());
        mDatas.add(user.getUsername());
        mDatas.add("女");
        mDatas.add(user.getArea());
        mDatas.add(user.getSign());
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        recylerUserinfo.setLayoutManager(new LinearLayoutManager(mContext));
        recylerUserinfo.setAdapter(adapter = new UserInfoAdapter(mTitles, mDatas, mContext));
        recylerUserinfo.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).build());
        adapter.setOnItemClick(new UserInfoAdapter.OnItemClick() {
            @Override
            public void onItemClick(final View targetView, int position) {
                switch (position) {
                    case 0:
                        PhotoPickerIntent intent = new PhotoPickerIntent(mContext);
                        intent.setColumn(4);
                        intent.setPhotoCount(1);
                        startActivityForResult(intent, REQUEST_CODE);
                        break;
                    case 1:
                        Intent intent1 = new Intent(mContext, ChangeTextInfoActivity.class);
                        intent1.putExtra("username", "username");
                        startActivity(intent1);
                        break;
                    case 3:
                        DialogBuilder.builderItemDialog(mContext, R.array.sex, "请选择性别", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setValue(targetView, R.array.sex, which);
                            }
                        }).create().show();
                        break;
                    case 4:
                        SelectCityDialog dialog = new SelectCityDialog(mContext, new SelectCityDialog.SelectCityCallBack() {
                            @Override
                            public void callBack(final String p, final String c, final String a) {
                                user.put("area", p + c + a);
                                user.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(AVException e) {
                                        if (e == null) {
                                            TextView txtChoose = (TextView) targetView.findViewById(R.id.txt_value);
                                            txtChoose.setText(p+c+a);
                                            Toast.makeText(mContext, "保存成功", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        });
                        dialog.show();
                        break;
                    case 5:
                        Intent intent2 = new Intent(mContext, ChangeTextInfoActivity.class);
                        intent2.putExtra("sign", "sign");
                        startActivity(intent2);
                        break;
                }
            }
        });
    }

    private void setValue(final View targetView, int stringId, int which) {
        final String value = getResources().getStringArray(stringId)[which];
        user.put("sex",which+1);
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e==null)
                {
                    TextView txtChoose = (TextView) targetView.findViewById(R.id.txt_value);
                    txtChoose.setText(value);
                    Toast.makeText(UserInfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(UserInfoActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<String> photos = null;
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
            }
            try {
                dialog= DialogBuilder.loadDialog(mContext, "正在上传");
                dialog.show();
                AVFile file=AVFile.withFile("icon.png",new File(photos.get(0)));
                user.put("icon",file);
                user.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        dialog.dismiss();
                        if (e==null)
                        {
                            Toast.makeText(UserInfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            adapter.notifyDataSetChanged();
                        }
                        else
                        {
                            Toast.makeText(UserInfoActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
        adapter=null;
        dialog=null;
        mDatas=null;
        mTitles=null;
    }
}
