package com.don.bilibili.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.don.bilibili.utils.EmptyUtil;

import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected Context mContext;
    private List<T> mList;

    public BaseAdapter(Context context, List<T> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getItemCount() {
        return EmptyUtil.isEmpty(mList) ? 0 : mList.size();
    }

    public T get(int position){
        return  mList.get(position);
    }

    public List<T> getAll(){return  mList;}
}
