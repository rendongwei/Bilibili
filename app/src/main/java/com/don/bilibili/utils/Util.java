package com.don.bilibili.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;

import com.don.bilibili.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Util {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void toggleHideyBar(Activity activity) {
        if (Build.VERSION.SDK_INT < 11) {
            return;
        }
        int uiOptions = activity.getWindow().getDecorView()
                .getSystemUiVisibility();
        int newUiOptions = uiOptions;
        boolean isImmersiveModeEnabled = ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.i("live", "Turning immersive mode mode off. ");
        } else {
            Log.i("live", "Turning immersive mode mode on.");
        }

        // Navigation bar hiding: Backwards compatible to ICS.
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }
        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        activity.getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }

    public static String[] getTs() {
        long time = System.currentTimeMillis();
        long ts = time / 1000;
        Date date = new Date();
        date.setTime(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String s = format.format(date);
        return new String[]{ts + "", s};
    }

    public static void initRefresh(SwipeRefreshLayout layout) {
        layout.setProgressBackgroundColor(R.color.white);
        layout.setColorSchemeResources(R.color.pink);
        layout.setSize(SwipeRefreshLayout.DEFAULT);
        layout.setProgressViewEndTarget(true,
                DisplayUtil.dip2px(layout.getContext(), 60));
    }

    public static GridLayoutManager getGridLayoutManager(Context context, int spanCount, GridLayoutManager.SpanSizeLookup spanSizeLookup) {
        GridLayoutManager manager = new GridLayoutManager(context, spanCount);
        manager.setAutoMeasureEnabled(true);
        manager.setSmoothScrollbarEnabled(true);
        if (spanSizeLookup != null) manager.setSpanSizeLookup(spanSizeLookup);
        return manager;
    }

    public static int parseColor(int color) {
        return Color.parseColor("#" + Integer.toHexString(color));
    }

    public static Drawable getRoundLine(Context context, int strokeColor,
                                        int fillColor) {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(fillColor);
        gd.setCornerRadius(DisplayUtil.dip2px(context, 4));
        gd.setStroke(DisplayUtil.dip2px(context, 1), strokeColor);
        return gd;
    }

    public static Drawable getMedalName(Context context, int fillColor) {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(fillColor);
        int radius = DisplayUtil.dip2px(context, 4);
        gd.setCornerRadii(new float[]{radius, radius, 0, 0, 0, 0, radius,
                radius});
        return gd;
    }

    public static Drawable getMedalLevel(Context context, int strokeColor) {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.WHITE);
        gd.setStroke(DisplayUtil.dip2px(context, 1), strokeColor);
        int radius = DisplayUtil.dip2px(context, 4);
        gd.setCornerRadii(new float[]{0, 0, radius, radius, radius, radius,
                0, 0});
        return gd;
    }

    public static String getDanmakuEmptyString(Context context,
                                               boolean hasMedal, boolean hasLevel, boolean hasTitle) {
        int dp = 0;
        if (hasMedal) {
            dp += 45;
        }
        if (hasLevel) {
            dp += 30;
        }
        if (hasTitle) {
            dp += 60;
        }
        if (dp == 0) {
            return "";
        }
        if (hasMedal && (hasLevel || hasTitle)) {
            dp += 8;
        } else {
            dp += 4;
        }
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(DisplayUtil.dip2px(context, 12));
        int lenth = DisplayUtil.dip2px(context, dp);
        float fontLength = getFontlength(paint, " ");
        int c = (int) (lenth / fontLength);
        int count = lenth % fontLength > 0 ? c + 1 : c;
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < count; i++) {
            buffer.append("&nbsp;");
        }
        return buffer.toString();
    }

    /**
     * @return 返回指定笔和指定字符串的长度
     */
    public static float getFontlength(Paint paint, String str) {
        return paint.measureText(str);
    }

    /**
     * @return 返回指定笔的文字高度
     */
    public static float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.descent - fm.ascent;
    }

    /**
     * @return 返回指定笔离文字顶部的基准距离
     */
    public static float getFontLeading(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.leading - fm.ascent;
    }

    public static Bitmap createDanmakuMedal(Context context, String name,
                                            int level, int color) {
        int dip1 = DisplayUtil.dip2px(context, 1);
        int dip4 = DisplayUtil.dip2px(context, 4);
        int dip8 = DisplayUtil.dip2px(context, 8);
        int dip20 = DisplayUtil.dip2px(context, 20);
        int dip25 = DisplayUtil.dip2px(context, 25);

        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(dip8);
        Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();
        int width = dip20 + dip25;
        int height = dip20;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 背景
        RectF rectF = new RectF(0, 0, width, height);
        paint.setColor(color);
        paint.setStrokeWidth(dip1);
        canvas.drawRoundRect(rectF, dip4, dip4, paint);

        // 名字
        float nameLength = textPaint.measureText(name);
        paint.setTextSize(dip8);
        paint.setColor(Color.WHITE);
        float x = dip25 / 2 - nameLength / 2;
        float y = height / 2 - (fontMetricsInt.bottom - fontMetricsInt.top) / 2
                - fontMetricsInt.top;
        canvas.drawText(name, x, y, paint);

        // level背景
        rectF = new RectF(dip25, dip1, width - dip1, height - dip1);
        float[] radii = new float[]{0, 0, dip4, dip4, dip4, dip4, 0, 0};
        Path path = new Path();
        path.addRoundRect(rectF, radii, Path.Direction.CW);
        canvas.drawPath(path, paint);

        // level
        paint.setColor(color);
        String levelName = String.format(Locale.CHINA, "%02d", level);
        float levelLength = textPaint.measureText(levelName);
        x = dip25 + dip20 / 2 - levelLength / 2;
        canvas.drawText(levelName, x, y, paint);
        return bitmap;
    }

    public static Bitmap createDanmakuLevel(Context context, int level,
                                            int color) {
        int dip1 = DisplayUtil.dip2px(context, 1);
        int dip4 = DisplayUtil.dip2px(context, 4);
        int dip8 = DisplayUtil.dip2px(context, 8);
        int dip20 = DisplayUtil.dip2px(context, 20);
        int dip30 = DisplayUtil.dip2px(context, 30);

        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(dip8);
        Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();
        int width = dip30;
        int height = dip20;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 背景
        RectF rectF = new RectF(0, 0, width, height);
        paint.setColor(color);
        paint.setStrokeWidth(dip1);
        canvas.drawRoundRect(rectF, dip4, dip4, paint);

        rectF = new RectF(dip1, dip1, width - dip1, height - dip1);
        paint.setColor(Color.WHITE);
        canvas.drawRoundRect(rectF, dip4, dip4, paint);

        // level
        String levelName = "UL" + String.format(Locale.CHINA, "%02d", level);
        float nameLength = textPaint.measureText(levelName);
        paint.setTextSize(dip8);
        paint.setColor(color);
        float x = width / 2 - nameLength / 2;
        float y = height / 2 - (fontMetricsInt.bottom - fontMetricsInt.top) / 2
                - fontMetricsInt.top;
        canvas.drawText(levelName, x, y, paint);

        return bitmap;
    }

    public static Bitmap createRecommendCommentLabel(Context context, String label) {

        int dip4 = DisplayUtil.dip2px(context, 4);
        int dip8 = DisplayUtil.dip2px(context, 8);
        int dip15 = DisplayUtil.dip2px(context, 15);
        int dip20 = DisplayUtil.dip2px(context, 20);

        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(dip8);
        Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();
        int width = dip20;
        int height = dip15;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 背景
        RectF rectF = new RectF(0, 0, width, height);
        paint.setColor(ContextCompat.getColor(context, R.color.pink));
        paint.setStrokeWidth(1);
        canvas.drawRoundRect(rectF, dip4, dip4, paint);

        rectF = new RectF(1, 1, width - 1, height - 1);
        paint.setColor(Color.WHITE);
        canvas.drawRoundRect(rectF, dip4, dip4, paint);
        // level
        float nameLength = textPaint.measureText(label);
        paint.setTextSize(dip8);
        paint.setColor(ContextCompat.getColor(context, R.color.pink));
        float x = width / 2 - nameLength / 2;
        float y = height / 2 - (fontMetricsInt.bottom - fontMetricsInt.top) / 2
                - fontMetricsInt.top;
        canvas.drawText(label, x, y, paint);

        return bitmap;
    }

    public static Bitmap createRecommendCommentLevel(Context context, int level) {
        Bitmap bitmap = null;
        int id = context.getResources().getIdentifier("ic_lv" + level, "drawable", context.getPackageName());
        if (id == 0) {
            bitmap = createDrawRecommendCommentLevel(context, level);
        } else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), id);
        }
        return bitmap;
    }

    public static Bitmap createDrawRecommendCommentLevel(Context context, int level) {

        int dip1 = DisplayUtil.dip2px(context, 1);
        int dip2 = DisplayUtil.dip2px(context, 2);
        int dip8 = DisplayUtil.dip2px(context, 8);
        int dip10 = DisplayUtil.dip2px(context, 10);
        int dip12 = DisplayUtil.dip2px(context, 12);

        TextPaint lvTextPaint = new TextPaint();
        lvTextPaint.setTextSize(dip8);
        Paint.FontMetricsInt lvFontMetricsInt = lvTextPaint.getFontMetricsInt();
        int lvWidth = (int) lvTextPaint.measureText("LV") + dip2;
        int lvHeight = Math.abs(lvFontMetricsInt.bottom - lvFontMetricsInt.top);

        TextPaint levelTextPaint = new TextPaint();
        levelTextPaint.setTextSize(dip10);
        Paint.FontMetricsInt levelFontMetricsInt = levelTextPaint.getFontMetricsInt();
        int levelWidth = (int) levelTextPaint.measureText(level + "") + dip2;
        int levelHeight = Math.abs(levelFontMetricsInt.bottom - levelFontMetricsInt.top);

        int width = lvWidth + levelWidth;
        int height = Math.max(lvHeight, levelHeight);

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        RectF rectF = new RectF(0, Math.abs(lvHeight - levelHeight), lvWidth, Math.abs(lvHeight - levelHeight) + lvHeight);
        paint.setColor(Color.parseColor("#FFB37C"));
        canvas.drawRect(rectF, paint);
        rectF = new RectF(lvWidth, 0, width, height);
        canvas.drawRect(rectF, paint);

        paint.setTextSize(dip8);
        paint.setColor(Color.WHITE);
        float x = dip1;
        float y = height / 2 - lvFontMetricsInt.descent + (lvFontMetricsInt.descent - lvFontMetricsInt.ascent) / 2 + Math.abs(lvHeight - levelHeight);
        canvas.drawText("LV", x, y, paint);

        paint.setTextSize(dip12);
        paint.setColor(Color.WHITE);
        x = lvWidth + dip1 / 2;
        y = height / 2 - lvFontMetricsInt.descent + levelHeight / 2;
        canvas.drawText(level + "", x, y, paint);

        return bitmap;
    }
}
