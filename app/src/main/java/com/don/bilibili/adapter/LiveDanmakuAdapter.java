package com.don.bilibili.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.don.bilibili.Model.LiveMessage;
import com.don.bilibili.Model.LiveTitle;
import com.don.bilibili.R;
import com.don.bilibili.utils.DisplayUtil;
import com.don.bilibili.utils.EmptyUtil;
import com.don.bilibili.utils.Util;
import com.don.bilibili.view.UrlImageSpan;
import com.don.bilibili.view.VerticalCenterImageSpan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.WeakHashMap;

public class LiveDanmakuAdapter extends
        RecyclerView.Adapter<ViewHolder> {

    private Context mContext;
    private List<LiveMessage> mMessages = new ArrayList<LiveMessage>();
    private Map<String, LiveTitle> mTitles = new HashMap<String, LiveTitle>();
    private WeakHashMap<Integer, Bitmap> mGuards = new WeakHashMap<Integer, Bitmap>();

    public LiveDanmakuAdapter(Context context, List<LiveMessage> messages,
                              Map<String, LiveTitle> titles) {
        super();
        mContext = context;
        mMessages = messages;
        mTitles = titles;
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view = null;
        ViewHolder holder = null;
        view = View.inflate(mContext, R.layout.listitem_live_danmaku, null);
        holder = new ItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemHolder itemHolder = (ItemHolder) holder;
        LiveMessage message = mMessages.get(position);

        boolean hasGuard = message.getGuardLevel() > 0
                && message.getGuardLevel() < 4;
        boolean hasMedal = !EmptyUtil.isEmpty(message.getMedal());
        boolean hasTitle = !EmptyUtil.isEmpty(message.getTitle())
                && mTitles.containsKey(message.getTitle().get(0));
        boolean hasLevel = !EmptyUtil.isEmpty(message.getLevel()) && !hasTitle;

        String content = "";
        Bitmap medalBitmap = null;
        Bitmap levelBitmap = null;
        SpannableString spannableString = null;
        String addGuard = "";
        String addMedal = "";
        String addLevel = "";
        String addTitle = "";
        if (hasGuard || hasMedal || hasLevel || hasTitle) {
            int guardStart = 0;
            int guardEnd = 0;
            int medalStart = 0;
            int medalEnd = 0;
            int levelStart = 0;
            int levelEnd = 0;
            int titleStart = 0;
            int titleEnd = 0;
            int colorStart = 0;
            int colorEnd = 0;
            int lastPosition = 0;
            if (hasGuard) {
                addGuard = message.getGuardLevel() + " ";
                guardStart = 0;
                guardEnd = 1;
                content += addGuard;
                lastPosition = guardEnd + 1;
            }
            if (hasMedal) {
                int medalLevel = Integer.parseInt(message.getMedal().get(0));
                String medalName = message.getMedal().get(1);
                int medalColor = Integer.parseInt(message.getMedal().get(4));
                medalBitmap = Util.createDanmakuMedal(mContext, medalName,
                        medalLevel, Util.parseColor(medalColor));
                addMedal = medalName + " " + String.format("%02d", medalLevel);
                content += addMedal + " ";
                medalStart = lastPosition;
                medalEnd = lastPosition + addMedal.length();
                lastPosition = medalEnd + 1;
            }
            if (hasLevel) {
                int level = Integer.parseInt(message.getLevel().get(0));
                int levelColor = Integer.parseInt(message.getLevel().get(2));
                levelBitmap = Util.createDanmakuLevel(mContext, level,
                        Util.parseColor(levelColor));
                addLevel = String.format(Locale.CHINA, "%02d", level);
                content += addLevel + " ";
                levelStart = lastPosition;
                levelEnd = lastPosition + addLevel.length();
                lastPosition = levelEnd + 1;
            }
            if (hasTitle) {
                addTitle = message.getTitle().get(0);
                content += addTitle + " ";
                titleStart = lastPosition;
                titleEnd = lastPosition + addTitle.length();
                lastPosition = titleEnd + 1;
            }
            String changeContent = message.getName() + " : ";
            content += changeContent + message.getMessage();
            colorStart = content.length() - message.getMessage().length()
                    - changeContent.length();
            colorEnd = content.length() - message.getMessage().length();

            spannableString = new SpannableString(content);

            if (hasGuard) {
                spannableString.setSpan(new VerticalCenterImageSpan(mContext,
                                getGuard(message.getGuardLevel())), guardStart,
                        guardEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            if (hasMedal) {
                spannableString.setSpan(new VerticalCenterImageSpan(mContext,
                                medalBitmap), medalStart, medalEnd,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if (hasLevel) {
                spannableString.setSpan(new VerticalCenterImageSpan(mContext,
                                levelBitmap), levelStart, levelEnd,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if (hasTitle) {
                spannableString.setSpan(new UrlImageSpan(mContext,
                                itemHolder.mTvContent, mTitles.get(addTitle).getImg(),
                                60, 20), titleStart, titleEnd,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            spannableString.setSpan(
                    new ForegroundColorSpan(Color.parseColor("#999999")),
                    colorStart, colorEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        } else {
            String changeContent = message.getName() + " : ";
            content = changeContent + message.getMessage();
            spannableString = new SpannableString(content);
            spannableString.setSpan(
                    new ForegroundColorSpan(Color.parseColor("#999999")), 0,
                    changeContent.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        itemHolder.mTvContent.setText(spannableString);
    }

    private Bitmap getGuard(int guardLevel) {
        Bitmap bitmap;
        int id = 0;
        switch (guardLevel) {
            case 1:
                id = R.drawable.ic_live_guard_governor;
                break;
            case 2:
                id = R.drawable.ic_live_guard_commander;
                break;
            case 3:
                id = R.drawable.ic_live_guard_captain;
                break;
        }
        if (mGuards.containsKey(id)) {
            bitmap = mGuards.get(id);
        } else {
            bitmap = getZoom(id);
            mGuards.put(id, bitmap);
        }
        return bitmap;
    }

    private Bitmap getZoom(int id) {
        int widthAndHeight = DisplayUtil.dip2px(mContext, 20);
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),
                id);
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) widthAndHeight / bitmap.getWidth());
        float scaleHeight = ((float) widthAndHeight / bitmap.getHeight());
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        return newBitmap;
    }

    class ItemHolder extends ViewHolder implements OnClickListener {

        TextView mTvContent;

        public ItemHolder(View view) {
            super(view);
            mTvContent = (TextView) view
                    .findViewById(R.id.listitem_live_danmaku_tv_content);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
