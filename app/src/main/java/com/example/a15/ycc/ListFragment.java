package com.example.a15.ycc;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private RecyclerView recyclerView2;
    private List<String> mData;
    private MyAdapter myAdapter;
    private String str="";
    private int mTitle[]=new int[]{R.string.title1,R.string.title2,R.string.title3,R.string.title4
            ,R.string.title5,R.string.title6,R.string.title7
            ,R.string.title8,R.string.title9,R.string.title10,R.string.title11};
    private int mContent[]=new int[]{R.string.content1,R.string.content2,R.string.content3,
            R.string.content4,R.string.content5,R.string.content6,R.string.content7,
            R.string.content8,R.string.content9,R.string.content10,R.string.content11
    };

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_list, container, false);
        inidata();
        recyclerView2=(RecyclerView)view.findViewById(R.id.recycleView2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
        myAdapter =new MyAdapter(getContext(),mData,1);
        recyclerView2.setAdapter(myAdapter);
        myAdapter.setOnMyItemClickListener(new MyAdapter.OnMyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (view.getId()) {
                    case R.id.textView:
                        String content = getString(mContent[position]);
                        Intent intent = new Intent(getContext(), ContentFragment.class);
                        intent.putExtra("content", content);
                        startActivity(intent);
                        break;
                    case R.id.button_collect:
                        Toast.makeText(getContext(), "收藏成功", Toast.LENGTH_SHORT).show();
                        SharedPreferences preferences = getActivity().getSharedPreferences("List", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();

                        if (str.contains(String.valueOf(position))) {
                            return;
                        } else {
                            str += String.valueOf(position);
                            str += "#";
                        }
                        editor.putString("key", str);
                        editor.apply();
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onItemLongClick(View view, int position) {
            }

            @Override
            public List<String> getData(List<String> data) {
                return data;
            }
        });
        return view;
    }

    private void inidata() {
        mData=new ArrayList<String>();
        for (int i=0;i<11;i++){
            mData.add(this.getString(mTitle[i]));
        }
    }


}
