package com.don.bilibili.fragment.live;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.don.bilibili.model.LiveMessage;
import com.don.bilibili.model.LiveTitle;
import com.don.bilibili.R;
import com.don.bilibili.adapter.LiveDanmakuAdapter;
import com.don.bilibili.annotation.Id;
import com.don.bilibili.annotation.OnClick;
import com.don.bilibili.fragment.base.BindFragment;
import com.don.bilibili.http.HttpManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveDanmakuFragment extends BindFragment implements
		OnClickListener {

	@Id(id = R.id.live_danmaku_lv_display)
	private RecyclerView mLvDisplay;
	@Id(id = R.id.live_danmaku_layout_gift)
	@OnClick
	private LinearLayout mLayoutGift;
	@Id(id = R.id.live_danmaku_et_content)
	private EditText mEtContent;
	@Id(id = R.id.live_danmaku_layout_send)
	@OnClick
	private LinearLayout mLayoutSend;

	private List<LiveMessage> mMessages = new ArrayList<LiveMessage>();
	private LiveDanmakuAdapter mAdapter;

	private Map<String, LiveTitle> mTitles = new HashMap<String, LiveTitle>();

	@Override
	protected int getContentView() {
		return R.layout.fragment_live_danmaku;
	}

	@Override
	protected void bindListener() {

	}

	@Override
	protected void init() {
		getLiveTitle();
		initRecyclerView();
		mAdapter = new LiveDanmakuAdapter(mContext, mMessages, mTitles);
		mLvDisplay.setAdapter(mAdapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.live_danmaku_layout_gift:

			break;

		case R.id.live_danmaku_layout_send:

			break;
		}
	}

	private void initRecyclerView() {
		mLvDisplay.setNestedScrollingEnabled(false);
		LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		layoutManager.setAutoMeasureEnabled(true);
		layoutManager.setSmoothScrollbarEnabled(true);
		mLvDisplay.setLayoutManager(layoutManager);
	}

	public void add(LiveMessage message) {
		mMessages.add(message);
		mAdapter.notifyItemInserted(mMessages.size() - 1);
		mLvDisplay.scrollToPosition(mMessages.size() - 1);
	}

	private void getLiveTitle() {
		Call<JSONObject> call = HttpManager.getInstance().getApiSevice().getLiveMessageTitle("android", "1d8b6e7d45233436", "506000", "android", "android", "xxhdpi", "1499216815", "1c68ab0a9e79b4af5aaf582917fbcdea");
		call.enqueue(new Callback<JSONObject>() {
			@Override
			public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
				if (response.body().optInt("code", -1) == 0) {
					JSONArray array = response.body().optJSONArray("data");
					if (array != null) {
						for (int i = 0; i < array.length(); i++) {
							LiveTitle title = new LiveTitle();
							title.parse(array.optJSONObject(i));
							mTitles.put(title.getId(), title);
						}
					}
				}
			}

			@Override
			public void onFailure(Call<JSONObject> call, Throwable t) {

			}
		});
	}
}
