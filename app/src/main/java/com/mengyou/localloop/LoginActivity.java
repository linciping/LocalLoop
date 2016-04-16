package com.mengyou.localloop;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.mengyou.library.Constant;
import com.mengyou.library.module.BaseActivity;
import com.mengyou.library.util.StatusBarBulider;
import com.mengyou.library.util.ValidateUtil;
import com.mengyou.localloop.leancloudim.AVImClientManager;
import com.mengyou.localloop.model.User;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 用户登录
 */
public class LoginActivity extends BaseActivity {


    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.txt_password)
    TextInputLayout txtPassword;
    @Bind(R.id.txt_username)
    TextInputLayout txtUsername;
    private ValidateUtil userNameValidate;
    private ValidateUtil passwordValudate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        ButterKnife.bind(this);
        if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT)
        {
            StatusBarBulider.setStatusBarForKITKAT(this,R.color.colorPrimaryDark);
        }
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        btnLogin.setEnabled(false);
        userNameValidate = new ValidateUtil(txtUsername, "用户名不能为空");
        userNameValidate.setView(btnLogin);
        userNameValidate.startValidate();
        passwordValudate = new ValidateUtil(txtPassword, "密码不能小于6位", Constant.Regular.REGULAR_PASSWORD, "密码不能为空");
        passwordValudate.setView(btnLogin);
        passwordValudate.startValidate();
    }

    @OnClick(R.id.btn_login)
    public void login() {
            final String username = txtUsername.getEditText().getText().toString();
            String password = txtPassword.getEditText().getText().toString();
            AVUser.logInInBackground(username, password, new LogInCallback<User>() {
                @Override
                public void done(User user, AVException e) {
                    if (e == null) {
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        AVImClientManager manager = AVImClientManager.getInstance();
                        manager.open(user.getObjectId(), new AVIMClientCallback() {
                            @Override
                            public void done(AVIMClient avimClient, AVIMException e) {
                                if (filterException(e))
                                {
                                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    Log.e("error", e.getMessage());
                                }
                            }
                        });
                    } else {
                        Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    }
                }
            }, User.class);
        }
}
