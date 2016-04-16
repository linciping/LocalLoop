package com.mengyou.localloop;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.mengyou.library.util.MyImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 图片显示
 */
public class ImageActivity extends Activity {

    private ImageView imageView;
    private String imageUrl;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
        getWindow().setNavigationBarColor(Color.parseColor("#454545"));
        setContentView(R.layout.content_image);

        imageView= (ImageView) findViewById(R.id.image_value);
        if (getIntent()!=null)
        {
            imageUrl=getIntent().getStringExtra("imageUrl");
        }

        ImageLoader.getInstance().displayImage(imageUrl,imageView, MyImageLoader.newOption());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        imageView=null;
    }
}
