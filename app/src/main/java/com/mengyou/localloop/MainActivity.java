package com.mengyou.localloop;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mengyou.library.module.BaseActivity;
import com.mengyou.library.util.StatusBarBulider;
import com.mengyou.localloop.fragment.ActivityGroupFragment;
import com.mengyou.localloop.fragment.ContactsFragment;
import com.mengyou.localloop.fragment.DiscoverGroupFragment;
import com.mengyou.localloop.fragment.MessageFragment;
import com.mengyou.localloop.fragment.UserFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {


    @Bind(R.id.nav_menu)
    RadioGroup navMenu;
    private ActivityGroupFragment activityGroupFragment;
    private DiscoverGroupFragment discoverGroupFragment;
    private ContactsFragment contactsFragment;
    private MessageFragment messageFragment;
    private UserFragment userFragment;
    private String whereFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;
        if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT)
        {
            StatusBarBulider.setStatusBarForKITKAT(this, R.color.colorPrimaryDark);
        }
        initData();
        initView();
    }

    @Override
    protected void initData() {
        super.initData();
        whereFragment=getIntent().getStringExtra("whereFragment");
    }

    @Override
    protected void initView() {
        super.initView();
        if (whereFragment!=null&&!whereFragment.equals(""))
        {
            switch (whereFragment)
            {
                case "1":
                    break;
                case "2":
                    break;
                case "3":
                    messageFragment=new MessageFragment();
                    replaceFragment(messageFragment);
                    break;
            }
        }
        else
        {
            activityGroupFragment = new ActivityGroupFragment();
            replaceFragment(activityGroupFragment);
        }
        navMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_activity:
                        if (activityGroupFragment == null) {
                            activityGroupFragment = new ActivityGroupFragment();
                            replaceFragment(activityGroupFragment);
                        } else {
                            replaceFragment(activityGroupFragment);
                        }
                        break;
                    case R.id.rb_discover:
                        if (discoverGroupFragment == null) {
                            discoverGroupFragment = new DiscoverGroupFragment();
                            replaceFragment(discoverGroupFragment);
                        } else {
                            replaceFragment(discoverGroupFragment);
                        }
                        break;
                    case R.id.rb_mainframe:
                        if (messageFragment == null) {
                            messageFragment = new MessageFragment();
                            replaceFragment(messageFragment);
                        } else {
                            replaceFragment(messageFragment);
                        }
                        break;
                    case R.id.rb_contacts:
                        if (contactsFragment == null) {
                            contactsFragment = new ContactsFragment();
                            replaceFragment(contactsFragment);
                        } else {
                            replaceFragment(contactsFragment);
                        }
                        break;
                    case R.id.rb_me:
                        if (userFragment == null) {
                            userFragment = new UserFragment();
                            replaceFragment(userFragment);
                        } else {
                            replaceFragment(userFragment);
                        }
                        break;
                }
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragment).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                .setTitle("提示")
                .setMessage("是否退出")
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                });
        builder.create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityGroupFragment = null;
        discoverGroupFragment = null;
        contactsFragment = null;
        messageFragment = null;
        userFragment = null;
    }
}
