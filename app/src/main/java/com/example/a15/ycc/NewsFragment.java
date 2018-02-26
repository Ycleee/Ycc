package com.example.a15.ycc;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class NewsFragment extends Fragment {

    private RecyclerView mRecyclerView;

    private MyAdapter myAdapter;

    private RecyclerView.LayoutManager mLayoutManager;
    private List<Map<String, Object>> data;
    private final static String MURL="http://jwb.xujc.com/tzgg/list.htm";


    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_news, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
        initView();
        return view;
    }
    private void initView() {

        new AsyncTask<String,String,String>(){

            @Override
            protected String doInBackground(String... params) {
                data=getDate();
                return null;
            }

            @Override
            protected void onPostExecute(String s) {

                initData();
                super.onPostExecute(s);

            }
        }.execute();

    }
    private void initData() {
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        myAdapter = new MyAdapter(getContext(),data);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(myAdapter);
        myAdapter.setOnMyItemClickListener(new MyAdapter.OnMyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Map<String, Object> map = data.get(position);
                String url =(String)(map.get("url"));
                Intent intent =new Intent(getContext(),WebFragment.class);
                intent.putExtra("ExtraUrl",url);
                startActivity(intent);
            }
        });



    }
    private List<Map<String,Object>> getDate(){
        List<Map<String,Object>> result =new ArrayList<Map<String,Object>>();
        String stringDate = getHttp(MURL);
        String Title ="";
        Pattern p =Pattern.compile("(.*?)<ul class=\"wp_article_list\">(.*?)<div id=\"wp_paging_w7\">(.*?)");
        Matcher m =p.matcher(stringDate);
        if(m.matches()){
            Title=m.group(2);
        }

        Pattern p1 =Pattern.compile("a href='(.*?)'(.*?)title='(.*?)'");
        Matcher m1 =p1.matcher(Title);
        while (m1.find()){
            MatchResult mr=m1.toMatchResult();
            Map<String,Object> map =new HashMap<String,Object>();
            map.put("url",mr.group(1));
            map.put("title",mr.group(3));
            result.add(map);
        }
        return result;
    }
    private String getHttp(String httpurl){
        HttpURLConnection connection =null;
        BufferedReader reader =null;
        String responseBody = null;
        try {
            URL url =new URL(httpurl);
            connection =(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            InputStream in =connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder response = new StringBuilder();
            String line;
            while((line=reader.readLine())!=null){
                response.append(line);
            }
            responseBody=response.toString();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(reader!=null){
                try{
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if(connection!=null){
                connection.disconnect();
            }
        }
        return responseBody;
    }

}
