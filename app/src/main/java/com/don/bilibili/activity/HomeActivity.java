package com.don.bilibili.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.don.bilibili.R;
import com.don.bilibili.activity.base.TranslucentStatusBarActivity;
import com.don.bilibili.annotation.Id;
import com.don.bilibili.fragment.base.FragmentManager;
import com.don.bilibili.service.SignService;
import com.don.bilibili.utils.DisplayUtil;

import java.util.HashMap;

public class HomeActivity extends TranslucentStatusBarActivity {
    @Id(id = R.id.home_layout_drawer)
    private DrawerLayout mDrawerLayout;
    @Id(id = R.id.home_layout_menu)
    private LinearLayout mLayoutMenu;

    private FragmentManager mFragmentManager;

    @Override
    protected int getContentView() {
        return R.layout.activity_home;
    }

    @Override
    protected void bindListener() {

    }

    @Override
    protected void init() {
        mFragmentManager = new FragmentManager(mActivity);

        IntentFilter filter = new IntentFilter();
        filter.addAction("aaaa");
        registerReceiver(receiver, filter);

        ViewGroup.LayoutParams params = mLayoutMenu
                .getLayoutParams();
        params.width = DisplayUtil.getWidth(mContext) / 4 * 3;
        mLayoutMenu.setLayoutParams(params);

//        mFragmentManager.showFragment(R.id.home_layout_content,
//                HomeFragment.class);

//        _device=android&_hwid=9edc79b18c3cf6f9&access_key=bda109fc53f39041fab6cbe2bd043ec1&appkey=1d8b6e7d45233436&build=508000&mobi_app=android&platform=android&room_id=10248&src=bili&trace_id=20170707090700025&ts=1499389645&version=5.8.0.508000&sign=1f58b3ab9fb0e79d3f025106c918697e
        HashMap<String, String> map = new HashMap<>();
        map.put("_device", "android");
        map.put("_hwid", "9edc79b18c3cf6f9");
        map.put("access_key", "bda109fc53f39041fab6cbe2bd043ec1");
        map.put("appkey", "1d8b6e7d45233436");
        map.put("build", "508000");
        map.put("mobi_app", "android");
        map.put("platform", "android");
        map.put("room_id", "10248");
        map.put("src", "bili");
        map.put("trace_id", "20170707090700025");
        map.put("ts", "1499389645");
        map.put("version", "5.8.0.508000");
//        TreeMap<String, String> localTreeMap = new TreeMap(map);
//        StringBuffer buffer = new StringBuffer();
//        for (TreeMap.Entry<String, String> entry : localTreeMap.entrySet()) {
//            buffer.append(entry.getKey() + "=" + entry.getValue()).append("&");
//        }
//        if (buffer.length()>0){
//            buffer.deleteCharAt(buffer.length()-1);
//        }
//        SignedQuery query = LibBili.a(map);
//        if (query == null) {
//            System.out.println("null");
//        } else {
//            System.out.println("签名:" + query.getSign() + "");
//        }
        startService(new Intent(this, SignService.class));
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            System.out.println("onReceive");
        }
    };

    public void showMenu() {
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }
}
