package com.example.a15.ycc;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements Filterable{
    private List<String> mData,tempData;
    private CardView cardView;
    private OnMyItemClickListener listener ;
    private Context context;
    private int VIEW_TYPE;


    public MyAdapter(Context context,List<String> data,int viewType) {
        this.context=context;
        this.mData = data;
        tempData=data;
        VIEW_TYPE=viewType;
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
        void onItemLongClick(View view , int position);
        List<String> getData(List<String> data);
    }
    public void setOnMyItemClickListener(OnMyItemClickListener listener){
        this.listener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewHolder viewHolder=null;
        switch (viewType){
            case 1:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.textview,parent,false);
                cardView=(CardView) view.findViewById(R.id.cardView);
                cardView.setRadius(8);
                cardView.setCardElevation(8);
                cardView.setContentPadding(5,5,5,5);
                viewHolder = new ViewHolder(view);
                break;
            case 2:
                View view1=LayoutInflater.from(parent.getContext()).inflate(R.layout.textview2,parent,false);
                viewHolder = new ViewHolder(view1);
                break;
        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        switch (holder.getItemViewType()) {
            case 1:
                holder.textView.setText(mData.get(position));
                if (listener != null) {
                    holder.textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listener.onItemClick(v, position);
                        }
                    });
                    holder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listener.onItemClick(v, position);
                        }
                    });
                }
                break;
            case 2:
                if (listener != null){
                    holder.textView3.setText(mData.get(position));
                    holder.textView3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listener.onItemClick(v, position);
                        }
                    });
                    holder.textView3.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            listener.onItemLongClick(v,position);
                            PopupMenu menu =new PopupMenu(context,v);
                            menu.getMenuInflater().inflate(R.menu.list_menu,menu.getMenu());
                            menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    mData.remove(position);
                                    notifyDataSetChanged();
                                    return false;
                                }
                            });
                            menu.show();
                            return false;

                        }
                    });
                }
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView,textView3;
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.textView);
            textView3=(TextView)itemView.findViewById(R.id.textView3);
            imageView=(ImageView)itemView.findViewById(R.id.button_collect);
        }
    }
}
