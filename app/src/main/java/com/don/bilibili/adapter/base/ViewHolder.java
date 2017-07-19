package com.don.bilibili.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

public class ViewHolder extends RecyclerView.ViewHolder {

    private View mItemView;

    private ViewHolder(View itemView) {
        super(itemView);
        mItemView = itemView;
    }

    public static ViewHolder create(View itemView) {
        return new ViewHolder(itemView);
    }

    protected <T extends View> T getView(int id) {
        if (mItemView == null) return null;
        SparseArray<View> views = (SparseArray<View>) mItemView
                .getTag();
        if (views == null){
            views = new SparseArray<>();
            mItemView.setTag(views);
        }
        View view  = views.get(id);
        if (view == null){
            view = mItemView.findViewById(id);
            views.put(id,view);
        }
        return view == null ? null  : (T)view;
    }
}
