package com.example.a15.ycc;


import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class CommunicationFragment extends Fragment implements View.OnClickListener{

    private ListView mListView;
    private AdapterComment mAdapter;
    private TextView hide_down;
    private EditText comment_content;
    private Button comment_send;
    private RelativeLayout rl_comment;
    MySqliteHelper mySqliteHelper=null;
    SQLiteDatabase db = null;

    public CommunicationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_communication, container, false);
        mListView = (ListView) view.findViewById(R.id.comment_list);
        hide_down = (TextView) view.findViewById(R.id.hide_down);
        comment_content = (EditText) view.findViewById(R.id.comment_content);
        comment_send = (Button) view.findViewById(R.id.comment_send);
        rl_comment = (RelativeLayout)view.findViewById(R.id.rl_comment);
        hide_down.setOnClickListener(this);
        comment_send.setOnClickListener(this);
        rl_comment.setVisibility(View.VISIBLE);
        mySqliteHelper = new MySqliteHelper(getContext(),"test7.db",null,5);
        db =mySqliteHelper.getWritableDatabase();
        mAdapter =new AdapterComment(getContext(),db);
        mListView.setAdapter(mAdapter);
        mAdapter.setOnMyItemClickListener(new AdapterComment.OnItemClickListener() {
            @Override
            public void onItemLongClick(View view, final int position) {
                PopupMenu menu =new PopupMenu(getContext(),view);
                menu.getMenuInflater().inflate(R.menu.comment_menu,menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        comment_content.setText("回复"+position+"楼--");
                        comment_content.setFocusable(true);
                        comment_content.requestFocus();
                        InputMethodManager imm =(InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
                        comment_content.setSelection(comment_content.getText().length());
                        return false;
                    }
                });
                menu.show();
            }
        });
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hide_down:
                rl_comment.setVisibility(View.GONE);
                InputMethodManager im = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(comment_content.getWindowToken(), 0);
                break;
            case R.id.comment_send:
                sendComment();
                break;
            default:
                break;
        }
    }

    private void sendComment() {
        SharedPreferences sharedPreferences =this.getActivity().getSharedPreferences("sharedPreferences",Context.MODE_PRIVATE);
        String name =sharedPreferences.getString("name",null);
        if(comment_content.getText().toString().equals("")){
            Toast.makeText(getContext(), "评论不能为空！", Toast.LENGTH_SHORT).show();
        }
        else if(comment_content.getText().toString().length()>120){
            Toast.makeText(getContext(), "字数不能超过120字！", Toast.LENGTH_SHORT).show();
        }
        else{
            mySqliteHelper = new MySqliteHelper(getContext(),"test7.db",null,5);
            db =mySqliteHelper.getWritableDatabase();
            String text =comment_content.getText().toString();
            Date date =new Date();
            SimpleDateFormat format =new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
            String time =format.format(date);
            ContentValues values =new ContentValues();
            values.put("username",name+":");
            values.put("text",text);
            values.put("time",time);
            db.insert("comment",null,values);
            comment_content.setText("");
            Toast.makeText(getContext(), "评论成功！", Toast.LENGTH_SHORT).show();
        }
    }
}
