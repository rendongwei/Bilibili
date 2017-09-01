package com.don.bilibili.activity.recommend;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.cloud.media.player.IMediaPlayer;
import com.don.bilibili.R;
import com.don.bilibili.activity.base.TranslucentStatusBarActivity;
import com.don.bilibili.adapter.TabAdapter;
import com.don.bilibili.annotation.Id;
import com.don.bilibili.annotation.OnClick;
import com.don.bilibili.constants.BackupUrl;
import com.don.bilibili.fragment.recommend.CommentFragment;
import com.don.bilibili.fragment.recommend.SynopsisFragment;
import com.don.bilibili.image.ImageManager;
import com.don.bilibili.model.HomeRecommend;
import com.don.bilibili.utils.DisplayUtil;
import com.don.bilibili.utils.ToastUtil;
import com.don.bilibili.view.DiffuseView;
import com.don.bilibili.view.media.BDCloudVideoView;

import java.util.ArrayList;
import java.util.List;

import static com.don.bilibili.utils.Util.toggleHideyBar;

public class RecommendActivity extends TranslucentStatusBarActivity implements View.OnClickListener {

    @Id(id = R.id.recommend_layout_appbar)
    private AppBarLayout mLayoutAppBar;
    @Id(id = R.id.recommend_layout_toolbar)
    private CollapsingToolbarLayout mLayoutToolBar;
    @Id(id = R.id.recommend_layout_display)
    private FrameLayout mLayoutDisplay;
    @Id(id = R.id.recommend_layout_top)
    private Toolbar mLayoutTop;
    @Id(id = R.id.recommend_iv_back)
    @OnClick
    private ImageView mIvBack;
    @Id(id = R.id.recommend_tv_title)
    private TextView mTvTitle;
    @Id(id = R.id.recommend_layout_title_play)
    @OnClick
    private LinearLayout mLayoutTitlePlay;
    @Id(id = R.id.recommend_v_display)
    private BDCloudVideoView mBdCloudVideoView;
    @Id(id = R.id.recommend_layout_empty)
    private LinearLayout mLayoutEmpty;
    @Id(id = R.id.recommend_layout_bottom)
    private LinearLayout mLayoutBottom;
    @Id(id = R.id.recommend_iv_play)
    @OnClick
    private ImageView mIvPlay;
    @Id(id = R.id.recommend_iv_fullscreen)
    @OnClick
    private ImageView mIvFullScreen;
    @Id(id = R.id.recommend_iv_image)
    @OnClick
    private ImageView mIvImage;
    @Id(id = R.id.recommend_v_ripple)
    private DiffuseView mVRipple;
    @Id(id = R.id.recommend_layout_tab)
    private TabLayout mLayoutTab;
    @Id(id = R.id.recommend_vp_display)
    private ViewPager mVpDisplay;
    @Id(id = R.id.recommend_fab_play)
    @OnClick
    private FloatingActionButton mFabPlay;

    private HomeRecommend mRecommend;

    private boolean mIsClickFab;
    private AnimatorSet mAnimatorSet;
    private GestureDetectorCompat mGestureDetectorCompat;
    private boolean mIsFullScreen = false;
    private boolean mAnimation = false;

    private SynopsisFragment mSynopsisFragment;
    private CommentFragment mCommentFragment;

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
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        AudioManager mManager = null;
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                mManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                mManager.adjustStreamVolume(android.media.AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                mManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                mManager.adjustStreamVolume(android.media.AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, android.media.AudioManager.FLAG_SHOW_UI);
                return true;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_recommend;
    }

    @Override
    protected void bindListener() {
        mLayoutAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange() && appBarLayout.getTotalScrollRange() != 0) {
                    mLayoutTitlePlay.setVisibility(View.VISIBLE);
                    mTvTitle.setVisibility(View.INVISIBLE);
                } else {
                    mLayoutTitlePlay.setVisibility(View.INVISIBLE);
                    mTvTitle.setVisibility(View.VISIBLE);
                }
                if (verticalOffset == 0 && mIsClickFab) {
                    clickFab();
                }
            }
        });
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
                if (!BackupUrl.ZHANQI.equals(mBdCloudVideoView.getCurrentPlayingUrl())) {
                    ToastUtil.showToast(mContext, "原地址播放失败,播放默认地址");
                    mBdCloudVideoView.setVideoPath(BackupUrl.ZHANQI);
                    mBdCloudVideoView.start();
                } else {
                    ToastUtil.showToast(mContext, "默认地址播放失败");
                }
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
        mLayoutEmpty.setOnTouchListener(
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
                            if (mLayoutTop.getVisibility() == View.VISIBLE
                                    && mLayoutBottom.getVisibility() == View.VISIBLE) {
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
                if ("https://app.bilibili.com/x/v2/view?".equals(method)) {
                    mSynopsisFragment.getView(sign);
                }
            }
        });
    }

    @Override
    protected void init() {
        mRecommend = getIntent().getParcelableExtra("recommend");

        BDCloudVideoView.setAK("ff8dde0b5ad14d4fa297749bb02a0256");
        mBdCloudVideoView
                .setVideoScalingMode(BDCloudVideoView.AR_ASPECT_WRAP_CONTENT);

        mTvTitle.setText("AV" + mRecommend.getAv().getParam());
        ImageManager.getInstance(mContext).showImage(mIvImage, mRecommend.getAv().getCover());

        List<Fragment> mFragments = new ArrayList<Fragment>();
        mSynopsisFragment = new SynopsisFragment(mRecommend);
        mCommentFragment = new CommentFragment();
        mFragments.add(mSynopsisFragment);
        mFragments.add(mCommentFragment);
        mVpDisplay.setOffscreenPageLimit(2);
        mVpDisplay.setAdapter(new TabAdapter(getSupportFragmentManager(),
                mFragments, "简介", "评论"));
        mLayoutTab.setupWithViewPager(mVpDisplay);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recommend_iv_back:
                back();
                break;

            case R.id.recommend_iv_more:

                break;

            case R.id.recommend_iv_play:
                if (mBdCloudVideoView.isPlaying()) {
                    mIvPlay.setImageResource(R.drawable.bili_player_play_can_play);
                    mBdCloudVideoView.pause();
                } else {
                    mIvPlay.setImageResource(R.drawable.bili_player_play_can_pause);
                    mBdCloudVideoView.start();
                }
                break;

            case R.id.recommend_iv_fullscreen:
                if (mIsFullScreen) {
                    CoordinatorLayout.LayoutParams layoutParams = new CoordinatorLayout.LayoutParams(
                            CollapsingToolbarLayout.LayoutParams.MATCH_PARENT,
                            CollapsingToolbarLayout.LayoutParams.WRAP_CONTENT);
                    mLayoutAppBar.setLayoutParams(layoutParams);
                    CollapsingToolbarLayout.LayoutParams params = new CollapsingToolbarLayout.LayoutParams(
                            CollapsingToolbarLayout.LayoutParams.MATCH_PARENT,
                            DisplayUtil.dip2px(mContext, 200));
                    mLayoutDisplay.setLayoutParams(params);
                    mBdCloudVideoView
                            .setVideoScalingMode(BDCloudVideoView.AR_ASPECT_WRAP_CONTENT);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    toggleHideyBar(mActivity);
                    changeStatusBarVisibility(View.VISIBLE);
                    mIsFullScreen = false;
                } else {
                    CoordinatorLayout.LayoutParams layoutParams = new CoordinatorLayout.LayoutParams(
                            CollapsingToolbarLayout.LayoutParams.MATCH_PARENT,
                            CollapsingToolbarLayout.LayoutParams.MATCH_PARENT);
                    mLayoutAppBar.setLayoutParams(layoutParams);
                    CollapsingToolbarLayout.LayoutParams params = new CollapsingToolbarLayout.LayoutParams(
                            CollapsingToolbarLayout.LayoutParams.MATCH_PARENT,
                            CollapsingToolbarLayout.LayoutParams.MATCH_PARENT);
                    mLayoutDisplay.setLayoutParams(params);
                    mBdCloudVideoView
                            .setVideoScalingMode(BDCloudVideoView.AR_ASPECT_FIT_PARENT);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    toggleHideyBar(mActivity);
                    changeStatusBarVisibility(View.GONE);
                    mIsFullScreen = true;
                }
                break;

            case R.id.recommend_fab_play:
            case R.id.recommend_layout_title_play:
                mIsClickFab = true;
                mLayoutAppBar.setExpanded(true, true);
                break;
        }
    }

    private void back() {
        if (mIsFullScreen) {
            CoordinatorLayout.LayoutParams layoutParams = new CoordinatorLayout.LayoutParams(
                    CollapsingToolbarLayout.LayoutParams.MATCH_PARENT,
                    CollapsingToolbarLayout.LayoutParams.WRAP_CONTENT);
            mLayoutAppBar.setLayoutParams(layoutParams);
            CollapsingToolbarLayout.LayoutParams params = new CollapsingToolbarLayout.LayoutParams(
                    CollapsingToolbarLayout.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(
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

    private void clickFab() {
        mIsClickFab = false;
        if (mAnimatorSet != null && mAnimatorSet.isRunning()) {
            return;
        }
        mAnimatorSet = new AnimatorSet();

        int width = mFabPlay.getWidth();
        int height = mFabPlay.getHeight();

        mVRipple.setCenterX(mVRipple.getWidth() - width / 2 - DisplayUtil.dip2px(mContext, 8));
        mVRipple.setCenterY(mVRipple.getHeight() - height / 2 - DisplayUtil.dip2px(mContext, 8));

        int translationY = height / 2 + DisplayUtil.dip2px(mContext, 8);
        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(mFabPlay, "translationY", 0, -translationY);
        translationAnimator.setDuration(300);
        translationAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mFabPlay, "alpha", 1, 0);
        alphaAnimator.setDuration(300);
        alphaAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mFabPlay.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });

        ValueAnimator rippleAnimator = ValueAnimator.ofInt(width,
                (int) mVRipple.getMaxRadius());
        rippleAnimator.setDuration(300);
        rippleAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mVRipple.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        rippleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                int radius = Integer.parseInt(animator.getAnimatedValue()
                        .toString());
                mVRipple.setRadius(radius);
            }
        });
        mAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mIvImage.setVisibility(View.GONE);
                mVRipple.setVisibility(View.GONE);
                mLayoutBottom.setVisibility(View.VISIBLE);
                CollapsingToolbarLayout.LayoutParams layoutParams = (CollapsingToolbarLayout.LayoutParams) mLayoutTop.getLayoutParams();
                layoutParams.height = DisplayUtil.dip2px(mContext, 40);
                mLayoutTop.setLayoutParams(layoutParams);
                mLayoutTop.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent_black80));
                AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) mLayoutToolBar.getLayoutParams();
                params.setScrollFlags(0);
                mLayoutAppBar.setExpanded(true);
                mBdCloudVideoView.setVideoPath("");
                mBdCloudVideoView.start();
                mHandler.sendEmptyMessageDelayed(0, 3000);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        mAnimatorSet.play(translationAnimator).before(alphaAnimator).before(rippleAnimator);
        mAnimatorSet.start();
    }

    private void show() {
        TranslateAnimation titleAnimation = getTopInAnimation(mLayoutTop);
        TranslateAnimation contorllerAnimation = getBottomInAnimation(mLayoutBottom);
        titleAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                mLayoutTop.setVisibility(View.VISIBLE);
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
                mLayoutBottom.setVisibility(View.VISIBLE);
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
        mLayoutTop.startAnimation(titleAnimation);
        mLayoutBottom.startAnimation(contorllerAnimation);
    }

    private void dismiss() {
        TranslateAnimation titleAnimation = getTopOutAnimation(mLayoutTop);
        TranslateAnimation contorllerAnimation = getBottomOutAnimation(mLayoutBottom);
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
                mLayoutTop.setVisibility(View.GONE);
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
                mLayoutBottom.setVisibility(View.GONE);
                mAnimation = false;
            }
        });
        mLayoutTop.startAnimation(titleAnimation);
        mLayoutBottom.startAnimation(contorllerAnimation);
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

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (!mAnimation) {
                        if (mLayoutTop.getVisibility() == View.VISIBLE
                                && mLayoutBottom.getVisibility() == View.VISIBLE) {
                            dismiss();
                        }
                    }
                    break;
            }
        }

    };

}
