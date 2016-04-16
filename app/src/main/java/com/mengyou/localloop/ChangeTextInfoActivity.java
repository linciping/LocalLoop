package com.mengyou.localloop;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import com.mengyou.localloop.model.User;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChangeTextInfoActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.txt_change_value)
    EditText txtChangeValue;
    @Bind(R.id.layout_change_value)
    TextInputLayout layoutChangeValue;

    private boolean isChangeName;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_text_info);
        ButterKnife.bind(this);
        user = User.getCurrentUser(User.class);
        if (getIntent().getStringExtra("username") != null) {
            isChangeName = true;
            toolbar.setTitle("修改名字");
        } else if (getIntent().getStringExtra("sign") != null) {
            isChangeName = false;
            toolbar.setTitle("发布个性签名");
        }
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (isChangeName) {
            layoutChangeValue.setHint("请输入姓名（字母和数组,不超过16位）");
            txtChangeValue.setHint("请输入姓名（字母和数组,不超过16位）");
        } else {
            layoutChangeValue.setHint("请输入个性签名");
            txtChangeValue.setHint("请输入个性签名");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_change_text_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_confrim_change_text_info) {
            if (isChangeName) {
                user.put("nickname", txtChangeValue.getText().toString());
                user.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            onBackPressed();
                            Toast.makeText(ChangeTextInfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ChangeTextInfoActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                user.put("sign", txtChangeValue.getText().toString());
                user.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            onBackPressed();
                            Toast.makeText(ChangeTextInfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ChangeTextInfoActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
