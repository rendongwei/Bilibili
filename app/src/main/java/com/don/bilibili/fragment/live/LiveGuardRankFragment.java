package com.don.bilibili.fragment.live;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;

import com.don.bilibili.Json.Json;
import com.don.bilibili.R;
import com.don.bilibili.adapter.LiveGuardRankAdapter;
import com.don.bilibili.annotation.Id;
import com.don.bilibili.fragment.base.BindFragment;
import com.don.bilibili.http.HttpManager;
import com.don.bilibili.model.LiveGuardRank;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveGuardRankFragment extends BindFragment implements
		OnClickListener {

	@Id(id = R.id.live_guard_rank_lv_display)
	private RecyclerView mLvDisplay;

	private int mId;
	private List<LiveGuardRank> mGuardRanks = new ArrayList<LiveGuardRank>();
	private LiveGuardRankAdapter mAdapter;

	public LiveGuardRankFragment(int id) {
		super();
		mId = id;
	}

	@Override
	protected int getContentView() {
		return R.layout.fragment_live_guard_rank;
	}

	@Override
	protected void bindListener() {

	}

	@Override
	protected void init() {
		initRecyclerView();
		getLiveGuardRank();
	}

	@Override
	public void onClick(View v) {

	}

	private void initRecyclerView() {
		mLvDisplay.setNestedScrollingEnabled(false);
		GridLayoutManager layoutManager = new GridLayoutManager(mContext, 3);
		layoutManager.setAutoMeasureEnabled(true);
		layoutManager.setSmoothScrollbarEnabled(true);
		layoutManager.setSpanSizeLookup(new SpanSizeLookup() {

			@Override
			public int getSpanSize(int position) {
				return position < 3 ? 1 : 3;
			}
		});
		mLvDisplay.setLayoutManager(layoutManager);
	}

	private void getLiveGuardRank() {
		Call<JSONObject> call = HttpManager.getInstance().getApiSevice().getUrl("http://api.live.bilibili.com/AppRoom/guardRank?_device=android&_hwid=9edc79b18c3cf6f9&access_key=bda109fc53f39041fab6cbe2bd043ec1&appkey=1d8b6e7d45233436&build=508000&mobi_app=android&page=1&page_size=15&platform=android&ruid=331520&src=bili&trace_id=20170707091400012&ts=1499390052&version=5.8.0.508000&sign=328b98645b4c7e3891fb7b2b7123dc3d");
		call.enqueue(new Callback<JSONObject>() {
			@Override
			public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
				if (response.body().optInt("code", -1) == 0) {
					JSONArray array = response.body().optJSONArray("data");
					mGuardRanks = Json.parseJsonArray(
							LiveGuardRank.class, array);
					mAdapter = new LiveGuardRankAdapter(mContext,
							mGuardRanks);
					mLvDisplay.setAdapter(mAdapter);
				}
			}

			@Override
			public void onFailure(Call<JSONObject> call, Throwable t) {

			}
		});
	}
}
