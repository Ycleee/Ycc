package com.example.a15.ycc;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContentFragment extends AppCompatActivity {

    private TextView textView;

    public ContentFragment() {
        // Required empty public constructor
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_content);
        textView=(TextView)findViewById(R.id.textView2);
        Intent intent =getIntent();
        String text=intent.getStringExtra("content");
        textView.setText(text);
    }
}
