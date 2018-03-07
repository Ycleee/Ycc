package com.example.a15.ycc;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterComment extends BaseAdapter{

    private Context context;
    private OnItemClickListener listener;
    SQLiteDatabase db = null ;

    public AdapterComment(Context c, SQLiteDatabase db)
    {
        super();
        this.context=c;
        this.db=db;
    }
    public  interface OnItemClickListener{
        void onItemLongClick(View view , int position);
    }
    public void setOnMyItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    @Override
    public int getCount() {
        Cursor cursor =db.query("comment",null,null,null,null,null,null);
        return cursor.getCount();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_comment,null);
            holder.comment_name = (TextView) convertView.findViewById(R.id.comment_name);
            holder.comment_content = (TextView) convertView.findViewById(R.id.comment_content);
            holder.comment_time=(TextView) convertView.findViewById(R.id.comment_time);
            holder.comment_louceng=(TextView)convertView.findViewById(R.id.comment_louceng);
            convertView.setTag(holder);
            holder.comment_content.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemLongClick(v,i);
                    return true;
                }
            });
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        Cursor cursor =db.query("comment",null,null,null,null,null,null);

        if(cursor.move(i+1)) {
            int name = cursor.getColumnIndex("username");
            int text = cursor.getColumnIndex("text");
            int time = cursor.getColumnIndex("time");
            holder.comment_name.setText(cursor.getString(name));
            holder.comment_content.setText(cursor.getString(text));
            holder.comment_time.setText(cursor.getString(time));
            holder.comment_louceng.setText("#"+String.valueOf(i));
        }
        cursor.close();
        return convertView;
    }

    public static class ViewHolder{
        TextView comment_name;
        TextView comment_content;
        TextView comment_time;
        TextView comment_louceng;
    }
}
