package com.don.bilibili.activity.live;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.cloud.media.player.IMediaPlayer;
import com.don.bilibili.Json.Json;
import com.don.bilibili.R;
import com.don.bilibili.activity.base.TranslucentStatusBarActivity;
import com.don.bilibili.adapter.TabAdapter;
import com.don.bilibili.annotation.Id;
import com.don.bilibili.annotation.OnClick;
import com.don.bilibili.constants.BackupUrl;
import com.don.bilibili.fragment.live.LiveDanmakuFragment;
import com.don.bilibili.fragment.live.LiveGuardRankFragment;
import com.don.bilibili.fragment.live.LiveRankFragment;
import com.don.bilibili.http.HttpManager;
import com.don.bilibili.image.ImageManager;
import com.don.bilibili.model.HomeLiveCategoryLive;
import com.don.bilibili.model.LiveMessage;
import com.don.bilibili.utils.DisplayUtil;
import com.don.bilibili.utils.EmptyUtil;
import com.don.bilibili.utils.EncryptUtil;
import com.don.bilibili.utils.TimeUtil;
import com.don.bilibili.utils.ToastUtil;
import com.don.bilibili.utils.Util;
import com.don.bilibili.view.CircularImageView;
import com.don.bilibili.view.media.BDCloudVideoView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveActivity extends TranslucentStatusBarActivity implements View.OnClickListener {

    @Id(id = R.id.live_layout_display)
    private RelativeLayout mLayoutDisplay;
    @Id(id = R.id.live_v_display)
    private BDCloudVideoView mBdCloudVideoView;
    @Id(id = R.id.live_v_danmaku)
    private DanmakuView mDanmakuView;
    @Id(id = R.id.live_iv_watermark)
    private ImageView mIvWaterMark;
    @Id(id = R.id.live_layout_title_top)
    private LinearLayout mLayoutTitleTop;
    @Id(id = R.id.live_iv_back)
    @OnClick
    private ImageView mIvBack;
    @Id(id = R.id.live_tv_title)
    private TextView mTvTitle;
    @Id(id = R.id.live_iv_more)
    @OnClick
    private ImageView mIvMore;
    @Id(id = R.id.live_layout_empty)
    @OnClick
    private LinearLayout mLayoutEmpty;
    @Id(id = R.id.live_layout_title_bottom)
    private LinearLayout mLayoutTitleBottom;
    @Id(id = R.id.live_iv_play)
    @OnClick
    private ImageView mIvPlay;
    @Id(id = R.id.live_tv_time)
    private TextView mTvTime;
    @Id(id = R.id.live_iv_danmaku)
    @OnClick
    private ImageView mIvDanmaku;
    @Id(id = R.id.live_iv_fullscreen)
    @OnClick
    private ImageView mIvFullScreen;
    @Id(id = R.id.live_iv_head)
    private CircularImageView mIvHead;
    @Id(id = R.id.live_tv_title_name)
    private TextView mTvTitleName;
    @Id(id = R.id.live_tv_level)
    private TextView mTvLevel;
    @Id(id = R.id.live_tv_name)
    private TextView mTvName;
    @Id(id = R.id.live_tv_round)
    private TextView mTvRound;
    @Id(id = R.id.live_tv_watch_count)
    private TextView mTvWatchCount;
    @Id(id = R.id.live_tv_follow_count)
    private TextView mTvFollowCount;
    @Id(id = R.id.live_tv_follow)
    @OnClick
    private TextView mTvFollow;
    @Id(id = R.id.live_layout_tab)
    private TabLayout mLayoutTab;
    @Id(id = R.id.live_vp_display)
    private ViewPager mVpDisplay;

    private HomeLiveCategoryLive mLive;
    private GestureDetectorCompat mGestureDetectorCompat;
    private DanmakuContext mDanmakuContext;
    private BaseDanmakuParser mBaseDanmakuParser;

    private boolean mIsFullScreen = false;
    private boolean mAnimation = false;
    private boolean mIsFinish = false;
    private List<String> mUrls = new ArrayList<>();
    private int mPlayUrlPosition = -1;

    private LiveDanmakuFragment mDanmakuFragment;
    private LiveRankFragment mRankFragment;
    private LiveGuardRankFragment mGuardRankFragment;

    private List<String> mLiveMessageFilter = new ArrayList<String>();

    private Call<JSONObject> mMessageCall;

    @Override
    public void onBackPressed() {
        back();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mBdCloudVideoView != null) {
            mBdCloudVideoView.enterForeground();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDanmakuView != null && mDanmakuView.isPrepared()
                && mDanmakuView.isPaused()) {
            mDanmakuView.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mDanmakuView != null && mDanmakuView.isPrepared()) {
            mDanmakuView.pause();
        }
    }

    @Override
    protected void onStop() {
        if (mBdCloudVideoView != null) {
            mBdCloudVideoView.enterBackground();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBdCloudVideoView != null) {
            mBdCloudVideoView.stopPlayback();
        }
        if (mDanmakuView != null) {
            mDanmakuView.release();
            mDanmakuView = null;
        }
        unregisterReceiver(receiver);
        mHandler.removeMessages(1);
        if (mMessageCall != null) {
            mMessageCall.cancel();
        }
        mIsFinish = true;
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_live;
    }

    @Override
    protected void bindListener() {
        mBdCloudVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(IMediaPlayer mp) {
            }
        });
        mBdCloudVideoView.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(IMediaPlayer mp) {
            }
        });
        mBdCloudVideoView.setOnErrorListener(new IMediaPlayer.OnErrorListener() {

            @Override
            public boolean onError(IMediaPlayer mp, int what, int extra) {
                if (!EmptyUtil.isEmpty(mUrls)) {
                    if (mPlayUrlPosition < mUrls.size() - 1) {
                        mPlayUrlPosition++;
                        String url = mUrls.get(mPlayUrlPosition);
                        if (!EmptyUtil.isEmpty(url)) {
                            mBdCloudVideoView.setVideoPath(url);
                            mBdCloudVideoView.start();
                            return false;
                        }
                    }
                }
                ToastUtil.showToast(mContext, "原地址播放失败,播放默认地址");
                mBdCloudVideoView.setVideoPath(BackupUrl.ZHANQI);
                mBdCloudVideoView.start();
                return false;
            }
        });

        mBdCloudVideoView.setOnInfoListener(new IMediaPlayer.OnInfoListener() {

            @Override
            public boolean onInfo(IMediaPlayer mp, int what, int extra) {
                return false;
            }
        });
        mBdCloudVideoView
                .setOnBufferingUpdateListener(new IMediaPlayer.OnBufferingUpdateListener() {

                    @Override
                    public void onBufferingUpdate(IMediaPlayer mp, int percent) {
                    }
                });
        mBdCloudVideoView.setOnPlayerStateListener(new BDCloudVideoView.OnPlayerStateListener() {

            @Override
            public void onPlayerStateChanged(BDCloudVideoView.PlayerState nowState) {
            }
        });

        findViewById(R.id.live_layout_empty).setOnTouchListener(
                new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        mHandler.removeMessages(0);
                        return mGestureDetectorCompat.onTouchEvent(event);
                    }
                });
        mGestureDetectorCompat = new GestureDetectorCompat(mContext,
                new GestureDetector.OnGestureListener() {

                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        mHandler.sendEmptyMessageDelayed(0, 3000);
                        if (!mAnimation) {
                            if (mLayoutTitleTop.getVisibility() == View.VISIBLE
                                    && mLayoutTitleBottom.getVisibility() == View.VISIBLE) {
                                dismiss();
                            } else {
                                show();
                            }
                        }
                        return true;
                    }

                    @Override
                    public void onShowPress(MotionEvent e) {
                    }

                    @Override
                    public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                            float distanceX, float distanceY) {
                        return true;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2,
                                           float velocityX, float velocityY) {
                        return true;
                    }

                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }
                });
        setOnGetSignCallBack(new OnGetSignCallBack() {
            @Override
            public void callback(String method, String sign) {
                if ("http://api.live.bilibili.com/AppRoom/guardRank?".equals(method) && mGuardRankFragment != null) {
                    mGuardRankFragment.getLiveGuardRank(sign);
                }
                if (("http://api.live.bilibili.com/AppRoom/medalRankList?".equals(method)
                        || "http://api.live.bilibili.com/AppRoom/opTop?".equals(method)
                        || "http://api.live.bilibili.com/AppRoom/getGiftTop?".equals(method)) && mRankFragment != null) {
                    mRankFragment.getSignCallBack(method, sign);
                }
            }
        });
    }

    @Override
    protected void init() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);
        registerReceiver(receiver, filter);
        mTvTime.setText(TimeUtil.getCurrentTime("HH:mm"));

        mLive = getIntent().getParcelableExtra("live");

        BDCloudVideoView.setAK("ff8dde0b5ad14d4fa297749bb02a0256");
        mBdCloudVideoView
                .setVideoScalingMode(BDCloudVideoView.AR_ASPECT_WRAP_CONTENT);

        initDanmaku();

        mTvTitle.setText(mLive.getOwner().getName() + "的直播间");
        mHandler.sendEmptyMessageDelayed(0, 3000);

        List<Fragment> mFragments = new ArrayList<Fragment>();
        mDanmakuFragment = new LiveDanmakuFragment();
        mRankFragment = new LiveRankFragment(mLive.getRoomId());
        mGuardRankFragment = new LiveGuardRankFragment(mLive.getOwner()
                .getMid());
        mFragments.add(mDanmakuFragment);
        mFragments.add(mRankFragment);
        mFragments.add(mGuardRankFragment);
        mVpDisplay.setOffscreenPageLimit(3);
        mVpDisplay.setAdapter(new TabAdapter(getSupportFragmentManager(),
                mFragments, "互动", "排行榜", "舰队"));
        mLayoutTab.setupWithViewPager(mVpDisplay);
        mVpDisplay.setCurrentItem(0);
        getInfo();
        getMessage();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.live_iv_back:
                back();
                break;

            case R.id.live_iv_more:

                break;

            case R.id.live_iv_play:
                if (mBdCloudVideoView.isPlaying()) {
                    mIvPlay.setImageResource(R.drawable.bili_player_play_can_play);
                    mBdCloudVideoView.pause();
                } else {
                    mIvPlay.setImageResource(R.drawable.bili_player_play_can_pause);
                    mBdCloudVideoView.start();
                }
                break;

            case R.id.live_iv_danmaku:
                if (mDanmakuView != null && mDanmakuView.isPrepared()) {
                    if (mDanmakuView.isShown()) {
                        mIvDanmaku
                                .setImageResource(R.drawable.bili_player_danmaku_is_closed);
                        mDanmakuView.pause();
                        mDanmakuView.hide();
                    } else {
                        mIvDanmaku
                                .setImageResource(R.drawable.bili_player_danmaku_is_open);
                        mDanmakuView.resume();
                        mDanmakuView.show();
                    }
                }
                break;

            case R.id.live_iv_fullscreen:
                if (mIsFullScreen) {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            DisplayUtil.dip2px(mContext, 200));
                    mLayoutDisplay.setLayoutParams(params);
                    mBdCloudVideoView
                            .setVideoScalingMode(BDCloudVideoView.AR_ASPECT_WRAP_CONTENT);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    toggleHideyBar(mActivity);
                    changeStatusBarVisibility(View.VISIBLE);
                    mIsFullScreen = false;
                } else {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    mLayoutDisplay.setLayoutParams(params);
                    mBdCloudVideoView
                            .setVideoScalingMode(BDCloudVideoView.AR_ASPECT_FIT_PARENT);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    toggleHideyBar(mActivity);
                    changeStatusBarVisibility(View.GONE);
                    mIsFullScreen = true;
                }
                break;
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            mTvTime.setText(TimeUtil.getCurrentTime("HH:mm"));
        }
    };

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

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (!mAnimation) {
                        if (mLayoutTitleTop.getVisibility() == View.VISIBLE
                                && mLayoutTitleBottom.getVisibility() == View.VISIBLE) {
                            dismiss();
                        }
                    }
                    break;

                case 1:
                    getMessage();
                    break;
            }
        }

    };

    private void back() {
        if (mIsFullScreen) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(
                    mContext, 200));
            mLayoutDisplay.setLayoutParams(params);
            mBdCloudVideoView
                    .setVideoScalingMode(BDCloudVideoView.AR_ASPECT_WRAP_CONTENT);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            toggleHideyBar(mActivity);
            changeStatusBarVisibility(View.VISIBLE);
            mIsFullScreen = false;
        } else {
            finish();
        }
    }

    private void show() {
        TranslateAnimation titleAnimation = getTopInAnimation(mLayoutTitleTop);
        TranslateAnimation contorllerAnimation = getBottomInAnimation(mLayoutTitleBottom);
        titleAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                mLayoutTitleTop.setVisibility(View.VISIBLE);
                mAnimation = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mAnimation = false;
            }
        });
        contorllerAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                mLayoutTitleBottom.setVisibility(View.VISIBLE);
                mAnimation = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mAnimation = false;
            }
        });
        mLayoutTitleTop.startAnimation(titleAnimation);
        mLayoutTitleBottom.startAnimation(contorllerAnimation);
    }

    private void dismiss() {
        TranslateAnimation titleAnimation = getTopOutAnimation(mLayoutTitleTop);
        TranslateAnimation contorllerAnimation = getBottomOutAnimation(mLayoutTitleBottom);
        titleAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                mAnimation = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mLayoutTitleTop.setVisibility(View.GONE);
                mAnimation = false;
            }
        });
        contorllerAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                mAnimation = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mLayoutTitleBottom.setVisibility(View.GONE);
                mAnimation = false;
            }
        });
        mLayoutTitleTop.startAnimation(titleAnimation);
        mLayoutTitleBottom.startAnimation(contorllerAnimation);
    }

    private TranslateAnimation getTopInAnimation(ViewGroup top) {
        TranslateAnimation translate = new TranslateAnimation(0f, 0f,
                -top.getHeight(), 0f);
        translate.setFillAfter(true);
        translate.setDuration(500);
        return translate;
    }

    private TranslateAnimation getTopOutAnimation(ViewGroup top) {
        TranslateAnimation translate = new TranslateAnimation(0f, 0f, 0f,
                -top.getHeight());
        translate.setDuration(500);
        return translate;
    }

    private TranslateAnimation getBottomInAnimation(ViewGroup bottom) {
        TranslateAnimation translate = new TranslateAnimation(0f, 0f,
                bottom.getHeight(), 0f);
        translate.setFillAfter(true);
        translate.setDuration(500);
        return translate;
    }

    private TranslateAnimation getBottomOutAnimation(ViewGroup bottom) {
        TranslateAnimation translate = new TranslateAnimation(0f, 0f, 0f,
                bottom.getHeight());
        translate.setDuration(500);
        return translate;
    }

    private void initDanmaku() {
        mDanmakuContext = DanmakuContext.create();
        // 设置最大显示行数
        HashMap<Integer, Integer> maxLinesPair = new HashMap<Integer, Integer>();
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 5); // 滚动弹幕最大显示5行
        // 设置是否禁止重叠
        HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<Integer, Boolean>();
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, true);

        mDanmakuContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3)
                .setDuplicateMergingEnabled(false).setScrollSpeedFactor(1.2f)
                .setScaleTextSize(1.2f).setMaximumLines(maxLinesPair)
                .preventOverlapping(overlappingEnablePair).setDanmakuMargin(40);
        mDanmakuView.enableDanmakuDrawingCache(true);
        mDanmakuView.setCallback(new DrawHandler.Callback() {

            @Override
            public void updateTimer(DanmakuTimer timer) {

            }

            @Override
            public void prepared() {
                mDanmakuView.start();
            }

            @Override
            public void drawingFinished() {

            }

            @Override
            public void danmakuShown(BaseDanmaku danmaku) {

            }
        });
        mBaseDanmakuParser = new BaseDanmakuParser() {

            @Override
            protected IDanmakus parse() {
                return new Danmakus();
            }
        };
        mDanmakuView.prepare(mBaseDanmakuParser, mDanmakuContext);
    }

    private void addDanmaku(String content) {
        BaseDanmaku danmaku = mDanmakuContext.mDanmakuFactory
                .createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        if (mDanmakuView == null || danmaku == null) {
            return;
        }
        danmaku.text = content;
        danmaku.padding = 5;
        danmaku.textSize = DisplayUtil.sp2px(mContext, 14);
        danmaku.textColor = Color.WHITE;
        danmaku.setTime(mDanmakuView.getCurrentTime());
        mDanmakuView.addDanmaku(danmaku);
    }

    public void getInfo() {
        Call<JSONObject> call = HttpManager.getInstance().getApiSevice().getUrl("http://api.live.bilibili.com/AppRoom/index?_device=android&appkey=1d8b6e7d45233436&build=506000&buld=506000&jumpFrom=24000&mobi_app=android&platform=android&room_id=" + mLive.getRoomId() + "&scale=xxhdpi&ts=1499216815&sign=c17733d913d0a24036a950a68bb75d19");
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if (response.body().optInt("code", -1) == 0) {
                    JSONObject object = response.body().optJSONObject("data");
                    if (object != null) {
                        if ("ROUND".equals(object.optString("status"))) {
                            mTvRound.setVisibility(View.VISIBLE);
                            mIvWaterMark
                                    .setImageResource(R.drawable.ic_watermark_round_small);
                            getRoundInfo();
                        } else {
                            mIvWaterMark
                                    .setImageResource(R.drawable.ic_watermark_live_small);
                            mBdCloudVideoView.setVideoPath(mLive
                                    .getUrl());
                            mBdCloudVideoView.start();
                        }
                        ImageManager.getInstance(mContext).showHead(
                                mIvHead, object.optString("face"));
                        mTvTitleName.setText(object.optString("title"));
                        mTvLevel.setText("UP"
                                + object.optString("master_level"));
                        int levelColor = object
                                .optInt("master_level_color");
                        mTvLevel.setTextColor(Util
                                .parseColor(levelColor));
                        mTvLevel.setBackgroundDrawable(Util
                                .getRoundLine(mContext,
                                        Util.parseColor(levelColor),
                                        Color.WHITE));
                        mTvName.setText(object.optString("uname"));
                        int online = object.optInt("online");
                        String count = "";
                        DecimalFormat df = new DecimalFormat("0.##");
                        if (online > 10000) {
                            double d = (double) online / 10000d;
                            count = df.format(d) + "万";
                        } else {
                            count = online + "";
                        }
                        mTvWatchCount.setText(count);
                        count = "";
                        int follow = object.optInt("attention");
                        if (follow > 10000) {
                            double d = (double) follow / 10000d;
                            count = df.format(d) + "万";
                        } else {
                            count = follow + "";
                        }
                        mTvFollowCount.setText(count);
                        boolean isFollow = object
                                .optInt("is_attention") == 1;
                        mTvFollow
                                .setBackgroundResource(isFollow ? R.drawable.bg_pink
                                        : R.drawable.bg_gray);
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {

            }
        });
    }

    private void getRoundInfo() {
        Call<JSONObject> call = HttpManager.getInstance().getApiSevice().getUrl("http://api.live.bilibili.com/live/getRoundPlayVideo?_device=android&_hwid=TXkfLxcmRXdGfkgqVipLeU18SCocfz9MKF9uWD9DJR1oDHlIfkp6Q3NDekJ1Qw&appkey=1d8b6e7d45233436&build=509000&mobi_app=android&platform=android&room_id=" + mLive.getRoomId() + "&src=bili&trace_id=20170711151600003&ts=1499757363&version=5.9.0.509000&sign=d5c5e039e09dd7e279260259bbfbfa2a");
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if (response.body().optInt("code", -1) == 0) {
                    JSONObject object = response.body().optJSONObject("data");
                    if (object != null) {
                        String url = object.optString("play_url");
                        if (!EmptyUtil.isEmpty(url)) {
                            getRoundPlayUrl(url);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {

            }
        });
    }

    private void getRoundPlayUrl(String url) {
        Call<JSONObject> call = HttpManager.getInstance().getApiSevice().getUrl(url);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                JSONArray array = response.body().optJSONArray("durl");
                if (array != null) {
                    String s = array.optJSONObject(0).optString("url");
                    JSONArray urls = array.optJSONObject(0).optJSONArray("backup_url");
                    if (urls != null) {
                        for (int i = 0; i < urls.length(); i++) {
                            mUrls.add(urls.optString(i));
                        }
                    }
                    if (!EmptyUtil.isEmpty(s)) {
                        mBdCloudVideoView.setVideoPath(s);
                        mBdCloudVideoView.start();
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {

            }
        });
    }

    private void getMessage() {
        Callback<JSONObject> callback = new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if (response.body().optInt("code", -1) == 0) {
                    JSONObject object = response.body().optJSONObject("data");
                    if (object != null) {
                        List<LiveMessage> messages = Json
                                .parseJsonArray(LiveMessage.class,
                                        object.optJSONArray("room"));
                        for (LiveMessage message : messages) {
                            String content = message.getUid() + "/"
                                    + message.getMessage() + "/"
                                    + message.getTime();
                            String filter = EncryptUtil.getMD5(content);
                            if (mLiveMessageFilter.contains(filter)) {
                                continue;
                            }
                            mLiveMessageFilter.add(filter);
                            addDanmaku(message.getMessage());
                            mDanmakuFragment.add(message);
                        }
                    }
                }
                mHandler.sendEmptyMessageDelayed(1, 300);
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                mHandler.sendEmptyMessageDelayed(1, 300);
            }
        };
        if (!mIsFinish) {
            if (mMessageCall == null) {
                mMessageCall = HttpManager.getInstance().getApiSevice().getUrl("http://api.live.bilibili.com/AppRoom/msg?_device=android&appkey=1d8b6e7d45233436&build=506000&mobi_app=android&platform=android&room_id=" + mLive.getRoomId() + "&ts=1499146112&sign=b5ff78c13763f307d7fbd3cb7b7b5467");
            } else {
                mMessageCall.cancel();
                mMessageCall = mMessageCall.clone();
            }
            mMessageCall.enqueue(callback);
        }
    }
}
