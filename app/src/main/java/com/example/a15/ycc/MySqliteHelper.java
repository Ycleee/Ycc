package com.example.a15.ycc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class MySqliteHelper extends SQLiteOpenHelper {

    public MySqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql1="create table if not exists user(id integer primary key autoincrement,username varchar(20),password varchar(20))";
        db.execSQL(sql1);
        String sql2="create table if not exists fare(type varchar(20),fare varchar(20),fare1 varchar(20),fare2 varchar(20),total varchar(20),year varchar(20))";
        db.execSQL(sql2);
        String sql3="create table if not exists comment(id integer primary key autoincrement,username varchar(20),text varchar(300),time varchar)";
        db.execSQL(sql3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}