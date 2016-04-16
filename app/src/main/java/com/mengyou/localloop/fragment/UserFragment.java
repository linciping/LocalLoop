package com.mengyou.localloop.fragment;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetDataCallback;
import com.avos.avoscloud.SaveCallback;
import com.mengyou.library.module.BaseFragment;
import com.mengyou.library.util.MyImageLoader;
import com.mengyou.localloop.PhotosActivity;
import com.mengyou.localloop.R;
import com.mengyou.localloop.SettingActivity;
import com.mengyou.localloop.UserInfoActivity;
import com.mengyou.localloop.adapter.ImageAdapter;
import com.mengyou.localloop.model.User;
import com.mengyou.localloop.viewbuilder.SelectCityDialog;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 * 个人页面
 */
public class UserFragment extends BaseFragment {

    private ViewHolder viewHolder;
    private ImageAdapter adapter;
    private List<String> imageUrls;

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        initData();
    }

    @Override
    protected void initData() {
        super.initData();
        imageUrls = new ArrayList<>();
        imageUrls.add("drawable://" + R.drawable.icon1);
        imageUrls.add("drawable://" + R.drawable.icon2);
        imageUrls.add("drawable://" + R.drawable.icon3);
        imageUrls.add("drawable://" + R.drawable.icon4);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        TextView txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setText("我");
        final User user = AVUser.getCurrentUser(User.class);
        viewHolder = new ViewHolder(view);

        AVFile file = user.getAVFile("icon");
        if (file!=null) {
            ImageLoader.getInstance().displayImage(file.getUrl(), viewHolder.imageIcon, MyImageLoader.newOption());
        }
        viewHolder.txtUsername.setText(user.getNickName());
        viewHolder.btnLevel.setText("v" + user.getRank());
        viewHolder.txtIntegralcan.setText(Integer.toString(user.getIntegralb()));
        viewHolder.txtIntegral.setText(Integer.toString(user.getIntegrala()));
        viewHolder.txtCertificationinfo.setText(user.getCertificationinfo());
        viewHolder.txtId.setText(user.getUsername());
        viewHolder.txtWhere.setText(user.getArea());
        if (user.getSex() == 1) {
            viewHolder.imageSexIcon.setImageResource(R.drawable.icon_man);
        } else {
            viewHolder.imageSexIcon.setImageResource(R.drawable.icon_woman);
        }

        viewHolder.btnToSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toOtherActivity(SettingActivity.class);
            }
        });

        viewHolder.btnToUserinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toOtherActivity(UserInfoActivity.class);
            }
        });
        viewHolder.btnToWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        viewHolder.btnToPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        viewHolder.recyclerPhoto.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        viewHolder.recyclerPhoto.setAdapter(adapter = new ImageAdapter(imageUrls, mContext));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewHolder = null;
        imageUrls = null;
    }

    static class ViewHolder {
        @Bind(R.id.image_icon)
        ImageView imageIcon;
        @Bind(R.id.txt_username)
        TextView txtUsername;
        @Bind(R.id.image_sex_icon)
        ImageView imageSexIcon;
        @Bind(R.id.btn_level)
        Button btnLevel;
        @Bind(R.id.txt_id)
        TextView txtId;
        @Bind(R.id.btn_to_userinfo)
        RelativeLayout btnToUserinfo;
        @Bind(R.id.btn_to_withdraw)
        RelativeLayout btnToWithdraw;
        @Bind(R.id.btn_to_setting)
        RelativeLayout btnToSetting;
        @Bind(R.id.recyler_photo)
        RecyclerView recyclerPhoto;
        @Bind(R.id.txt_integral)
        TextView txtIntegral;
        @Bind(R.id.txt_integralcan)
        TextView txtIntegralcan;
        @Bind(R.id.txt_certificationinfo)
        TextView txtCertificationinfo;
        @Bind(R.id.txt_where)
        TextView txtWhere;
        @Bind(R.id.btn_photo)
        LinearLayout btnToPhoto;
        @Bind(R.id.btn_where)
        LinearLayout btnWhere;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
