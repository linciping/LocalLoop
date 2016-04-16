package com.mengyou.localloop;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.SaveCallback;
import com.mengyou.library.module.BaseActivity;
import com.mengyou.library.util.MyImageLoader;
import com.mengyou.localloop.model.AVDynamic;
import com.mengyou.localloop.model.User;
import com.mengyou.localloop.viewbuilder.DialogBuilder;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

/**
 * 创建动态
 */
public class CreateDynamicActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    ArrayList<String> selectedPhotos = new ArrayList<>();
    public final static int REQUEST_CODE = 1;
    @Bind(R.id.txt_content)
    EditText txtContent;
    @Bind(R.id.grid_images)
    GridView gridImages;
    private PhotoPickAdapter adapter;

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_dynamic);
        ButterKnife.bind(this);
        mContext = this;
        initData();
        initView();
    }

    @Override
    protected void initData() {
        super.initData();
        selectedPhotos.add("drawable://" + R.drawable.image_add);
        checkPermission();
    }

    @Override
    protected void initView() {
        super.initView();
        toolbar.setTitle("创建动态");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogBuilder.builderNormalDialog(mContext, "提示", "是否真的退出（您还没发布这条动态）", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
                    }
                }).create().show();
            }
        });
        gridImages.setAdapter(adapter = new PhotoPickAdapter());
        gridImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == adapter.getCount() - 1) {
                    PhotoPickerIntent intent = new PhotoPickerIntent(mContext);
                    intent.setColumn(4);
                    intent.setPhotoCount(9);
                    startActivityForResult(intent, REQUEST_CODE);
                } else {
                    String url="file://"+selectedPhotos.get(position);
                    Intent intent=new Intent(mContext, ImageActivity.class);
                    intent.putExtra("imageUrl",url);
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            // permission was granted, yay!
            PhotoPickerIntent intent = new PhotoPickerIntent(mContext);
            intent.setColumn(4);
            intent.setPhotoCount(9);
            startActivityForResult(intent, REQUEST_CODE);

        } else {
            // permission denied, boo! Disable the
            // functionality that depends on this permission.
            Toast.makeText(this, "No read storage permission! Cannot perform the action.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        switch (permission) {
            case Manifest.permission.READ_EXTERNAL_STORAGE:
                // No need to explain to user as it is obvious
                return false;

            default:
                return true;
        }
    }

    private void checkPermission() {

        int permissionState = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionState != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);

            }
        } else {
            // Permission granted
            PhotoPickerIntent intent = new PhotoPickerIntent(mContext);
            intent.setColumn(4);
            intent.setPhotoCount(9);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<String> photos = null;
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (selectedPhotos.size()>0)
            {
                selectedPhotos.remove(selectedPhotos.size()-1);//移除最后一位的添加按钮
            } 
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
            }
            if (photos != null) {
                selectedPhotos.addAll(photos);
            }
            selectedPhotos.add("drawable://" + R.drawable.image_add);
            adapter.notifyDataSetChanged();
        }
    }

    class PhotoPickAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return selectedPhotos.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView==null)
            {
                convertView=getLayoutInflater().inflate(R.layout.item_cover, parent, false);
                viewHolder=new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            else
            {
                viewHolder= (ViewHolder) convertView.getTag();
            }
            if (position==getCount()-1)
            {
                ImageLoader.getInstance().displayImage(selectedPhotos.get(position),viewHolder.imageView, MyImageLoader.newOption());
            }
            else {
                ImageLoader.getInstance().displayImage("file://"+selectedPhotos.get(position),viewHolder.imageView, MyImageLoader.newOption());
            }
            return convertView;
        }

        class ViewHolder{
            private ImageView imageView;

            public ViewHolder( View itemView) {
                imageView= (ImageView) itemView.findViewById(R.id.image);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_dynamic,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.action_create_dynamic)
        {//此处为创建动态
            dialog= DialogBuilder.loadDialog(mContext,"正在上传");
            dialog.show();
            final List<AVFile> files=new ArrayList<>();
            for (int i=0;i<selectedPhotos.size()-1;i++)
            {//是否需要上传进度
                try {
                    final AVFile file=AVFile.withAbsoluteLocalPath("dynamicpic"+i,selectedPhotos.get(i));
                    final int finalI = i;
                    file.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e!=null)
                            {
                                dialog.dismiss();
                                Toast.makeText(CreateDynamicActivity.this, "图片上传失败", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                files.add(file);
                                if (finalI ==selectedPhotos.size()-2)
                                {
                                    final AVDynamic dynamic=new AVDynamic();
                                    dynamic.setContentStr(txtContent.getText().toString());
                                    dynamic.addAll("contentPic", files);
                                    dynamic.put("creater", User.getCurrentUser(User.class));
                                    dynamic.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(AVException e) {
                                            dialog.dismiss();
                                            if (e!=null)
                                            {
                                                Toast.makeText(CreateDynamicActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                            {
                                                Toast.makeText(CreateDynamicActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                                                onBackPressed();
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });
                } catch (FileNotFoundException e) {
                    Log.e("error",e.getMessage());
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
