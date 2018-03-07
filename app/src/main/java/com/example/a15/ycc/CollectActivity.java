package com.example.a15.ycc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class CollectActivity extends AppCompatActivity {

    private List<String> mData;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private SharedPreferences preferences;
    private String strs[];
    private int mTitle[]=new int[]{R.string.title1,R.string.title2,R.string.title3,R.string.title4
            ,R.string.title5,R.string.title6,R.string.title7
            ,R.string.title8,R.string.title9,R.string.title10,R.string.title11};
    private int mContent[]=new int[]{R.string.content1,R.string.content2,R.string.content3,
            R.string.content4,R.string.content5,R.string.content6,R.string.content7,
            R.string.content8,R.string.content9,R.string.content10,R.string.content11
    };
    int num[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        recyclerView=(RecyclerView)findViewById(R.id.recycleView3);
        preferences =this.getSharedPreferences("List", Context.MODE_PRIVATE);
        final String str = preferences.getString("key","");
        strs=str.split("#");
        num=new int[strs.length];
        if(strs[0]!=""){
            for(int i=0;i<strs.length;i++){
                num[i]=Integer.parseInt(strs[i]);
            }
        }

        if(mData==null){
            mData=new ArrayList<String>();
        }

        for (int i=0;i<num.length;i++){
            mData.add(getString(mTitle[num[i]]));
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter =new MyAdapter(this,mData,2);
        recyclerView.setAdapter(myAdapter);
        myAdapter.setOnMyItemClickListener(new MyAdapter.OnMyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String content =getString(mContent[num[position]]);
                Intent intent =new Intent();
                intent.setClass(getApplicationContext(),ContentFragment.class);
                intent.putExtra("content",content);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, final int position) {

            }

            @Override
            public List<String> getData(List<String> data) {
                return data;
            }
        });

    }

}
