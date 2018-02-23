package com.example.a15.ycc;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class loginActivity extends AppCompatActivity implements View.OnClickListener {

    private MySqliteHelper mySqliteHelper;
    SharedPreferences sharedPreferences;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mySqliteHelper = new MySqliteHelper(this, "test7.db", null, 5);
        findViewById(R.id.login_button).setOnClickListener(this);
        findViewById(R.id.zhuce_button).setOnClickListener(this);

    }

    public void onClick(View view)
    {
        TextView u=(TextView) findViewById(R.id.username);
        TextView p=(TextView) findViewById(R.id.password);
        String name=u.getText().toString();
        String password=p.getText().toString();

        switch (view.getId())
        {
            case R.id.login_button:
                if(login(name,password))
                {
                    Toast.makeText(this,"登录成功,欢迎用户"+name,Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(loginActivity.this,MainActivity.class);
                    startActivity(intent);
                    sharedPreferences=getSharedPreferences("sharedPreferences",MODE_PRIVATE);
                    sharedPreferences.edit().putBoolean("isLogin",true).apply();
                    finish();
                }
                else
                {
                    Toast.makeText(this,"账号与密码输入不正确",Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.zhuce_button:
                Intent intent1 = new Intent(loginActivity.this, registerActivity.class);
                startActivity(intent1);
                finish();
                break;
        }

    }
    public boolean login(String username,String password){
        SQLiteDatabase db =mySqliteHelper.getReadableDatabase();
        String sql ="select * from user where username=? and password=?";
        Cursor cursor = db.rawQuery(sql,new String []{username,password});
        if(cursor.moveToFirst()){
            cursor.close();
            return true;
        }
        return false;
    }
}
