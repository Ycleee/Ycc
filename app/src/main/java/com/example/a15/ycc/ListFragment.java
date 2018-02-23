package com.example.a15.ycc;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;

public class ListFragment extends Fragment {

    private ViewPager vpager_one;
    private ArrayList<View> aList;
    private MyPagerAdapter mAdapter;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_list, container, false);
        vpager_one=(ViewPager) view.findViewById(R.id.viewPager);
        aList=new ArrayList<View>();
        LayoutInflater li = getLayoutInflater(savedInstanceState);
        View t1=li.inflate(R.layout.text1,null);
        View t2=li.inflate(R.layout.text2,null);
        View t3=li.inflate(R.layout.text3,null);
        textView2=(TextView) t1.findViewById(R.id.textView2);
        TextPaint paint2 =textView2.getPaint();
        paint2.setFakeBoldText(true);
        textView3=(TextView) t2.findViewById(R.id.textView2);
        TextPaint paint3 =textView3.getPaint();
        paint3.setFakeBoldText(true);
        textView4=(TextView) t3.findViewById(R.id.textView2);
        TextPaint paint4 =textView4.getPaint();
        paint4.setFakeBoldText(true);
        aList.add(t1);
        aList.add(t2);
        aList.add(t3);
        mAdapter=new MyPagerAdapter(aList);
        vpager_one.setAdapter(mAdapter);
        return view;
    }

}
