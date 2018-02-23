package com.example.a15.ycc;


import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class registerActivity extends AppCompatActivity implements View.OnClickListener {
    private SQLiteDatabase db;
    private MySqliteHelper mySqliteHelper;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        mySqliteHelper = new MySqliteHelper(this,"test7.db",null,5);
        db = mySqliteHelper.getWritableDatabase();
        findViewById(R.id.register_button).setOnClickListener(this);
    }

    public void onClick(View view) {
        TextView username_register = (TextView) findViewById(R.id.username_register);
        TextView password_register = (TextView) findViewById(R.id.password_register);
        String name = username_register.getText().toString();
        String password = password_register.getText().toString();
        if (checkExist(name)) {
            Toast.makeText(this, "用户名已注册过", Toast.LENGTH_SHORT).show();
        } else {
            if (register(name, password)) {
                Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(registerActivity.this, loginActivity.class);
                SharedPreferences sharedPreferences=getSharedPreferences("sharedPreferences",MODE_PRIVATE);
                sharedPreferences.edit().putString("name",name).apply();
                startActivity(intent);
                finish();
            }
        }
    }
    public boolean register(String username,String password)
    {
        ContentValues values =new ContentValues();
        values.put("username",username);
        values.put("password",password);
        db=mySqliteHelper.getWritableDatabase();
        db.insert("user",null,values);
        db.close();
        return true;
    }
    public boolean checkExist(String value)
    {
        db=mySqliteHelper.getWritableDatabase();
        String Query = "Select * from user where username =?";
        Cursor cursor = db.rawQuery(Query,new String[] { value });
        if(cursor.getCount()>0){
            cursor.close();
            return true;
        }else {
            cursor.close();
            return false;
        }
    }
}