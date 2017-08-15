package com.don.bilibili.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.style.ImageSpan;

public class VerticalCenterImageSpan extends ImageSpan {

	public VerticalCenterImageSpan(Context context, Bitmap b,
			int verticalAlignment) {
		super(context, b, verticalAlignment);
	}

	public VerticalCenterImageSpan(Context context, Bitmap b) {
		super(context, b);
	}

	public VerticalCenterImageSpan(Context context, int resourceId,
			int verticalAlignment) {
		super(context, resourceId, verticalAlignment);
	}

	public VerticalCenterImageSpan(Context context, int resourceId) {
		super(context, resourceId);
	}

	public VerticalCenterImageSpan(Context context, Uri uri,
			int verticalAlignment) {
		super(context, uri, verticalAlignment);
	}

	public VerticalCenterImageSpan(Context context, Uri uri) {
		super(context, uri);
	}

	public VerticalCenterImageSpan(Drawable d, int verticalAlignment) {
		super(d, verticalAlignment);
	}

	public VerticalCenterImageSpan(Drawable d, String source,
			int verticalAlignment) {
		super(d, source, verticalAlignment);
	}

	public VerticalCenterImageSpan(Drawable d, String source) {
		super(d, source);
	}

	public VerticalCenterImageSpan(Drawable d) {
		super(d);
	}

	public int getSize(Paint paint, CharSequence text, int start, int end,
			FontMetricsInt fm) {
		Drawable d = getDrawable();
		Rect rect = d.getBounds();
		if (fm != null) {
			FontMetricsInt fmPaint = paint.getFontMetricsInt();
			int fontHeight = fmPaint.bottom - fmPaint.top;
			int drHeight = rect.bottom - rect.top;

			int top = drHeight / 2 - fontHeight / 3;
			int bottom = drHeight / 2 + fontHeight / 3;

			fm.ascent = -bottom;
			fm.top = -bottom;
			fm.bottom = top;
			fm.descent = top;
		}
		return rect.right;
	}

	@Override
	public void draw(Canvas canvas, CharSequence text, int start, int end,
			float x, int top, int y, int bottom, Paint paint) {
		Drawable b = getDrawable();
		canvas.save();
		int transY = 0;
		transY = ((bottom - top) - b.getBounds().bottom) / 2 + top;
		canvas.translate(x, transY);
		b.draw(canvas);
		canvas.restore();
	}
}
