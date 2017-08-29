package com.don.bilibili.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class DiffuseView extends View {

    private Paint mPaint;
    private int mCenterX;
    private int mCenterY;
    private int mRadius;

    public DiffuseView(Context context) {
        this(context, null);
    }

    public DiffuseView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public DiffuseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#FB7299"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);
    }

    public double getMaxRadius() {
        return Math.sqrt(getWidth() * getWidth() + getHeight() * getHeight());
    }

    public void setCenterX(int cx) {
        mCenterX = cx;
        invalidate();
    }

    public void setCenterY(int cy) {
        mCenterY = cy;
        invalidate();
    }

    public void setRadius(int radius) {
        double maxRadius = getMaxRadius();
        if (radius < 0) {
            mRadius = 0;
        } else if (radius >= maxRadius) {
            mRadius = (int) maxRadius;
        } else {
            mRadius = radius;
        }
        invalidate();
    }
}
