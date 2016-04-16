package com.mengyou.localloop;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import com.mengyou.library.module.BaseActivity;
import com.mengyou.library.util.StatusBarBulider;
import com.mengyou.localloop.adapter.CreateActivityAdapter;
import com.mengyou.localloop.event.ActivityImageEvent;
import com.mengyou.localloop.fragment.ActivityFragment;
import com.mengyou.localloop.model.AVActivity;
import com.mengyou.localloop.model.User;
import com.mengyou.localloop.viewbuilder.DialogBuilder;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import me.iwf.photopicker.PhotoPickerActivity;

/**
 * @author linciping
 * @version 1.0
 * @see ActivityFragment
 * 创建活动
 */
public class CreateActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyler_create_activity)
    RecyclerView recylerCreateActivity;
    private List<String> mDatas;
    private List<String> mTitle;
    private CreateActivityAdapter adapter;
    private AlertDialog.Builder builder;
    private AVActivity avActivity=new AVActivity();
    public final static int REQUEST_CODE = 1;
    private List<String> selectedPhotos=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        ButterKnife.bind(this);
        mContext = this;
        if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT)
        {
            StatusBarBulider.setStatusBarForKITKAT(this, R.color.colorPrimaryDark);
        }
        TAG = getClass().getName();
        initData();
        initView();
    }

    @Override
    protected void initData() {
        super.initData();
        mDatas = new ArrayList<>();
        mDatas.add("选择主题");
        mDatas.add("选择时间");
        mDatas.add("选择地点");
        mDatas.add("选择对象");
        mDatas.add("选择买单方式");
        mDatas.add("输入认识");
        mDatas.add("输入活动要求的最低等级");
        mTitle = new ArrayList<>();
        mTitle.add("主题");
        mTitle.add("时间");
        mTitle.add("地点");
        mTitle.add("对象");
        mTitle.add("买单");
        mTitle.add("人数");
        mTitle.add("等级");
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogBuilder.builderNormalDialog(mContext, "提示", "是否真的退出（您还没发布这条活动）", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
                    }
                }).create().show();
            }
        });
        recylerCreateActivity.setLayoutManager(new LinearLayoutManager(mContext));
        recylerCreateActivity.setAdapter(adapter = new CreateActivityAdapter(CreateActivity.this, mDatas, mTitle));
        recylerCreateActivity.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).build());
        adapter.setOnItemClick(new CreateActivityAdapter.OnItemClick() {
            @Override
            public void onItemClick(final View targetView, final int position) {
                if (position < 7) {
                    switch (position) {
                        case 0:
                            builder = DialogBuilder.builderItemDialog(mContext, R.array.theme, "请选择主题", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    setValue(targetView, R.array.theme, which);
                                    avActivity.setTheme(which);
                                }
                            });
                            break;
                        case 1:
                            builder = DialogBuilder.builderItemDialog(mContext, R.array.time, "请选择时间", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    setValue(targetView, R.array.time, which);
                                    avActivity.setTime(getResources().getStringArray(R.array.time)[which]);
                                }
                            });
                            break;
                        case 2:
                            View view = getLayoutInflater().inflate(R.layout.pop_input, null);
                            final EditText txt_value = (EditText) view.findViewById(R.id.txt_value);
                            builder = DialogBuilder.builderInputDialog(mContext, view, "请输入地点", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    TextView txtChoose = (TextView) targetView.findViewById(R.id.txt_choose);
                                    txtChoose.setText(txt_value.getText());
                                    avActivity.setAddr(txt_value.getText().toString());
                                }
                            });
                            break;
                        case 3:
                            builder = DialogBuilder.builderItemDialog(mContext, R.array.object, "请选择对象", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    setValue(targetView, R.array.object, which);
                                    avActivity.setObjectSex(which);
                                }
                            });
                            break;
                        case 4:
                            builder = DialogBuilder.builderItemDialog(mContext, R.array.buy, "请选择谁买单", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    setValue(targetView, R.array.buy, which);
                                    avActivity.setWhoBuy(which);
                                }
                            });
                            break;
                        case 5:
                            View view1 = getLayoutInflater().inflate(R.layout.pop_input, null);
                            final EditText txt_value1 = (EditText) view1.findViewById(R.id.txt_value);
                            builder = DialogBuilder.builderInputDialog(mContext, view1, "请输入人数", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    TextView txtChoose = (TextView) targetView.findViewById(R.id.txt_choose);
                                    txtChoose.setText(txt_value1.getText());
                                    avActivity.setPeopleNum(Integer.parseInt(txt_value1.getText().toString()));
                                }
                            });
                            break;
                        case 6:
                            View view2 = getLayoutInflater().inflate(R.layout.pop_input, null);
                            final EditText txt_value2 = (EditText) view2.findViewById(R.id.txt_value);
                            builder = DialogBuilder.builderInputDialog(mContext, view2, "请输入等级", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    TextView txtChoose = (TextView) targetView.findViewById(R.id.txt_choose);
                                    txtChoose.setText(txt_value2.getText());
                                    avActivity.setLimitRank(Integer.parseInt(txt_value2.getText().toString()));
                                }
                            });
                            break;
                    }
                    builder.create().show();
                } else if (position == 7) {
                    EditText editText = (EditText) targetView.findViewById(R.id.txt_description);
                    editText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            avActivity.setContentStr(s.toString());
                        }
                    });
                } else {
                    CheckBox checkBox = (CheckBox) targetView.findViewById(R.id.checkbox_create);
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            avActivity.setHideName(buttonView.isChecked());
                        }
                    });
                }
            }
        });
    }

    private void setValue(View targetView, int stringId, int which) {
        String value = getResources().getStringArray(stringId)[which];
        if (!value.equals("取消")) {
            TextView txtChoose = (TextView) targetView.findViewById(R.id.txt_choose);
            txtChoose.setText(value);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_create) {
            avActivity.setCreater(User.getCurrentUser(User.class));
            avActivity.setState(1);
            avActivity.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        Toast.makeText(CreateActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("error", e.getMessage());
                    }
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<String> photos = null;
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (selectedPhotos.size()>0)
            {
                selectedPhotos.remove(selectedPhotos.size() - 1);//移除最后一位的添加按钮
            }
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
            }
            if (photos != null) {
                selectedPhotos.addAll(photos);
            }
            selectedPhotos.add("drawable://" + R.drawable.image_add);
            ActivityImageEvent event=new ActivityImageEvent();
            event.images=selectedPhotos;
            EventBus.getDefault().post(event);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        EventBus.getDefault().unregister(this);
        mDatas = null;
        mTitle = null;
        adapter = null;
        builder = null;
    }
}
