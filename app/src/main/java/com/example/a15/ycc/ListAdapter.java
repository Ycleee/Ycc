package com.example.a15.ycc;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder>{

    private Context context;
    private List<String> mData;
    public ListAdapter(Context context,List<String> data){
        this.context=context;
        this.mData = data;
    }
    @Override
    public ListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder =new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.textview,parent,false));
        return holder;
    }



    public  interface OnItemClickListener {
        void onItemClick(View view , int position);
        void onItemLongClick(View view , int position);
    }
    private OnItemClickListener mOnItemClickListener;
    public void setOnMyItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(final ListAdapter.MyViewHolder holder, final int position) {
        holder.textview.setText(mData.get(position));
        holder.textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v,position);
            }
        });
        holder.textview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int pos=holder.getLayoutPosition();
                mOnItemClickListener.onItemLongClick(v,pos);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textview;
        public MyViewHolder(View itemView) {
            super(itemView);
            textview=(TextView)itemView.findViewById(R.id.textView);
        }
    }
}