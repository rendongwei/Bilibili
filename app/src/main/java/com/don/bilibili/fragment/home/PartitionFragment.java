package com.don.bilibili.fragment.home;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.don.bilibili.Json.Json;
import com.don.bilibili.R;
import com.don.bilibili.adapter.PartitionAdapter;
import com.don.bilibili.adapter.PartitionCategoryAdapter;
import com.don.bilibili.annotation.Id;
import com.don.bilibili.fragment.base.BindFragment;
import com.don.bilibili.http.HttpManager;
import com.don.bilibili.model.Partition;
import com.don.bilibili.model.PartitionBanner;
import com.don.bilibili.utils.DisplayUtil;
import com.don.bilibili.view.DividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PartitionFragment extends BindFragment {

    @Id(id = R.id.partition_lv_category)
    private RecyclerView mLvCategory;
    @Id(id = R.id.partition_lv_display)
    private RecyclerView mLvDisplay;

    private List<Partition> mPartitions = new ArrayList<Partition>();

    @Override
    protected int getContentView() {
        return R.layout.fragment_home_partition;
    }

    @Override
    protected void bindListener() {

    }

    @Override
    protected void init() {
        initRecyclerView();
        mLvCategory.setAdapter(new PartitionCategoryAdapter(mContext));
        getPartition();
    }

    private void initRecyclerView() {
        mLvCategory.setNestedScrollingEnabled(false);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 4);
        layoutManager.setAutoMeasureEnabled(true);
        layoutManager.setSmoothScrollbarEnabled(true);
        mLvCategory.setLayoutManager(layoutManager);
        mLvCategory
                .addItemDecoration(new DividerItemDecoration(new DividerItemDecoration.OnDividerItemDecorationListener() {
                    @Override
                    public Rect getOffsets(int position) {
                        int width = DisplayUtil.dip2px(mContext, 15);
                        int halfWidth = width / 2;
                        int left = halfWidth;
                        int top = 0;
                        int right = halfWidth;
                        int bottom = 0;
                        top = width;
                        if (position > 11) {
                            bottom = width;
                        }
                        return new Rect(left, top, right, bottom);
                    }
                }));

        mLvDisplay.setNestedScrollingEnabled(false);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        manager.setAutoMeasureEnabled(true);
        manager.setSmoothScrollbarEnabled(true);
        mLvDisplay.setLayoutManager(manager);
        mLvDisplay.addItemDecoration(new DividerItemDecoration(new DividerItemDecoration.OnDividerItemDecorationListener() {
            @Override
            public Rect getOffsets(int position) {
                int width = DisplayUtil.dip2px(mContext, 15);
                int halfWidth = width / 2;
                int left = halfWidth;
                int top = halfWidth;
                int right = halfWidth;
                int bottom = 0;
                return new Rect(left, top, right, bottom);
            }
        }));
    }

    private void getPartition() {
        Call<JSONObject> call = HttpManager.getInstance().getApiSevice().getUrl("https://app.bilibili.com/x/v2/show/index?appkey=1d8b6e7d45233436&build=509001&mobi_app=android&platform=android&ts=1500002210&sign=8d57c615867c2ef2e2e20db11a599cbc");
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if (response.body().optInt("code", -1) == 0) {
                    JSONArray array = response.body().optJSONArray("data");
                    if (array != null) {
                        mPartitions.clear();
                        for (int i = 0; i < array.length(); i++) {
                            Partition partition = new Partition();
                            partition.parse(array.optJSONObject(i));
                            if ("medium".equals(partition.getStyle())) {
                                JSONObject object = array
                                        .optJSONObject(i)
                                        .optJSONObject("banner");
                                if (object != null) {
                                    List<PartitionBanner> banners = new ArrayList<PartitionBanner>();
                                    banners = Json.parseJsonArray(
                                            PartitionBanner.class,
                                            object.optJSONArray("top"));
                                    partition.setBanners(banners);
                                }
                                mPartitions.add(partition);
                            }
                        }
                    }
                }
                mLvDisplay.setAdapter(new PartitionAdapter(mContext,
                        mPartitions));
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {

            }
        });
    }
}
