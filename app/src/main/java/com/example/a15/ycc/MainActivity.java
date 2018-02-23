package com.example.a15.ycc;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;


public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener,NavigationView.OnNavigationItemSelectedListener{


    private BottomNavigationBar bottomNavigationBar;
    private NewsFragment newsFragment;
    private MapFragment mapFragment;
    private CommunicationFragment communicationFragment;
    private ListFragment listFragment;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    int lastSelectedPosition = 0;
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
                    Intent intent=new Intent(getApplicationContext(),loginActivity.class);
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
            Intent intent=new Intent(this,loginActivity.class);
            startActivity(intent);
        }else if(id==R.id.nav_icon){

        }
        drawerLayout.closeDrawers();
        return true;
    }
}
