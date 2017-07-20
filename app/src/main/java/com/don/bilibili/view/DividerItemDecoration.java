package com.don.bilibili.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class DividerItemDecoration extends RecyclerView.ItemDecoration{

    private OnDividerItemDecorationListener mListener;

    public DividerItemDecoration(OnDividerItemDecorationListener listener) {
        mListener = listener;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams())
                .getViewLayoutPosition();
        Rect rect = mListener == null ? new Rect() : mListener.getOffsets(position);
        outRect.set(rect);
    }


    public interface  OnDividerItemDecorationListener{
        public abstract  Rect getOffsets(int position);
    }
}
