package com.example.a15.ycc;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

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
    private SearchView searchView;
    private MyAdapter myAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Map<String, Object>> data;


    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_news, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
        searchView=(SearchView)view.findViewById(R.id.search);
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
        List<String> mData=new ArrayList<>();
        for(int i=0;i<data.size();i++){
            mData.add(data.get(i).get("title").toString());
        }
        myAdapter = new MyAdapter(getContext(),mData,2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(myAdapter);
        myAdapter.setOnMyItemClickListener(new MyAdapter.OnMyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (view.getId()){
                    case R.id.textView3:
                        Map<String, Object> map = data.get(position);
                        String url =(String)(map.get("url"));
                        Intent intent =new Intent(getContext(),WebFragment.class);
                        intent.putExtra("ExtraUrl",url);
                        startActivity(intent);
                        break;
                    default:break;
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

        searchView.setQueryHint("输入关键字");
        searchView.setIconified(true);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }


    private List<Map<String,Object>> getDate(){
        List<Map<String,Object>> result =new ArrayList<Map<String,Object>>();
        StringBuilder stringBuilder =new StringBuilder();
        for(int i=1;i<6;i++){
            String MURL="http://jwb.xujc.com/tzgg/list"+i+".htm";
            stringBuilder.append(getHttp(MURL));
        }
        Pattern p1 =Pattern.compile("<span class='Article_Title'><a href='(.*?)'(.*?)title='(.*?)'");
        Matcher m1 =p1.matcher(stringBuilder);
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
