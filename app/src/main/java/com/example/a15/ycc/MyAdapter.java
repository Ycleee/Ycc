package com.example.a15.ycc;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements Filterable{
    private List<String> mData,tempData;
    private CardView cardView;
    private OnMyItemClickListener listener ;
    private Context context;



    public MyAdapter(Context context,List<String> data) {
        this.context=context;
        this.mData = data;
        tempData=data;
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<String> newDate=new ArrayList<>();

                if(constraint!=null&&constraint.toString().trim().length()>0){
                    for (int i=0;i<tempData.size();i++){
                        String content =tempData.get(i);
                        if(content.contains(constraint)){
                            newDate.add(content);
                        }
                    }
                }else {
                    newDate=tempData;
                }
                FilterResults filterResults =new FilterResults();
                filterResults.count=newDate.size();
                filterResults.values=newDate;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mData=(List)results.values;
                if(results.count>0){
                    notifyDataSetChanged();
                }else {
                    mData.add("没有符合的结果");
                    notifyDataSetChanged();
                }
            }
        };
    }



    public  interface OnMyItemClickListener {
        void onItemClick(View view , int position);
    }
    public void setOnMyItemClickListener(OnMyItemClickListener listener){
        this.listener = listener;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.textview,parent,false);
        cardView=(CardView) view.findViewById(R.id.cardView);
        cardView.setRadius(8);
        cardView.setCardElevation(8);
        cardView.setContentPadding(5,5,5,5);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.textView.setText(mData.get(position));
        if(listener!=null){
            holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v,position);
            }
        });

        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.textView);
        }
    }
}
