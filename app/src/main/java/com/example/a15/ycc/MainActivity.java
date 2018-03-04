package com.example.a15.ycc;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener,NavigationView.OnNavigationItemSelectedListener{


    private BottomNavigationBar bottomNavigationBar;
    private NewsFragment newsFragment;
    private MapFragment mapFragment;
    private CommunicationFragment communicationFragment;
    private ListFragment listFragment;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Bitmap head;
    private CircleImageView imageView;
    private TextView textView;
    private static String path = "/sdcard/myHead/";
    int lastSelectedPosition = 0;
    String TAG ="AAAAA";
    static boolean isLogin=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences =getSharedPreferences("sharedPreferences",MODE_PRIVATE);
        isLogin = sharedPreferences.getBoolean("isLogin",false);
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.ic_language_black_48dp,"资讯"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_announcement_black_48dp,"交流区"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_grade_black_48dp,"资料"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_map_black_48dp,"地图"))
                .setFirstSelectedPosition(lastSelectedPosition)
                .setActiveColor(R.color.blue)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);
        navigationView=(NavigationView)findViewById(R.id.nav);
        View headView =navigationView.getHeaderView(0);
        imageView=(CircleImageView) headView.findViewById(R.id.circleImageview);
        textView=(TextView)headView.findViewById(R.id.Name);
        Intent i =getIntent();
        textView.setText(i.getStringExtra("name"));
        drawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout.closeDrawers();
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        newsFragment=new NewsFragment();
        ft.replace(R.id.fragment_main,newsFragment);
        ft.commit();
    }

    @Override
    public void onTabSelected(int position) {
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        switch (position){
            case 0:
                if(newsFragment==null){
                    newsFragment=new NewsFragment();
                }
                ft.replace(R.id.fragment_main,newsFragment);
                break;
            case 1:
                if(isLogin){
                    if(communicationFragment==null){
                        communicationFragment=new CommunicationFragment();
                    }
                    ft.replace(R.id.fragment_main,communicationFragment);
                }else {
                    Toast.makeText(this, "请先登入", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);

                }

                break;
            case 2:
                if(listFragment==null){
                    listFragment=new ListFragment();
                }
                ft.replace(R.id.fragment_main,listFragment);
                break;
            case 3:
                if(mapFragment==null){
                    mapFragment=new MapFragment();
                }
                ft.replace(R.id.fragment_main,mapFragment);
                break;
            default:
                break;
        }
        ft.commit();
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle("确认退出吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.finish();
            }
        })
                .setNegativeButton("返回", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPreferences =getSharedPreferences("sharedPreferences",MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("isLogin",false).apply();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();
        if(id==R.id.nav_login){
            Intent intent=new Intent(this,LoginActivity.class);
            startActivity(intent);
        }else if(id==R.id.nav_icon){
            Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
            startActivityForResult(intent,1);
        }
        drawerLayout.closeDrawers();
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if(data==null){
                    return;
                }else {
                    cropPhoto(data.getData());
                }
                break;
            case 3:
                if(data==null){
                    return;
                }else {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    setPictoView(head);
                    imageView.setImageBitmap(head);
                    Toast.makeText(this, "设置成功", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    private void setPictoView(Bitmap bitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName = path +"head.jpg";// 图片名字
        try {
            b = new FileOutputStream(fileName);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
