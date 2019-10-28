package com.hlbd.electric.feature.launcher.data.realtime.video.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.hlbd.electric.R;
import com.hlbd.electric.api.HttpApi;
import com.hlbd.electric.util.LogUtil;
import com.hlbd.electric.util.ToastUtil;

import java.util.List;

public class VideoListActivity extends AppCompatActivity implements View.OnClickListener, VideoContract.View<VideoInfo> {

  private VideoListAdapter mAdapter;
  private VideoRequest mRequest;
  private VideoContract.Presenter mPresenter;
  public static final int REQUEST_CODE = 1;
  public static final int RESULT_CODE = 100;
  private EditText mSearchEt;
  private ImageView mSearchIv;

  private static final String TAG = "VideoListActivity";

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_video_list);
    initView();
    init();
  }

  private void init() {
    mRequest = new VideoRequest(HttpApi.getUserName());
    mRequest.url = "Baoding_VideoThing/Services/SelectVideoByKeyword?";
    mRequest.keyword = "";
    mPresenter = new VideoPresenter(this, mRequest);
  }

  private void initView() {
    mSearchEt = findViewById(R.id.et_search_video_list);
    mSearchIv = findViewById(R.id.iv_search_video_list);
    mSearchIv.setOnClickListener(this);

    mAdapter = new VideoListAdapter();
    ListView lv = findViewById(R.id.lv_list_video_list);
    lv.setAdapter(mAdapter);
    findViewById(R.id.iv_cencel_video_list).setOnClickListener(this);
    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        VideoInfo.Row row = mAdapter.mInfoList.get(position);
        if (row == null || row.videourl == null) {
          finish();
          return;
        }
        Intent i = new Intent();
        i.putExtra("value", row.videourl);
        setResult(RESULT_CODE, i);
        finish();
      }
    });
  }

  @Override
  public void showLoading() {
    ToastUtil.toast("正在加载视频列表");
  }

  @Override
  public void dismissLoading() {
    ToastUtil.toast("视频列表加载完成");
  }

  @Override
  public void notifyVideoInfo(VideoInfo info) {
    LogUtil.d(TAG, "notifyVideoInfo() info=" + info);
    if (info == null) {
      ToastUtil.toast("获取数据失败");
      return;
    }
    List<VideoInfo.Row> rows = info.rows;
    if (rows == null || rows.size() == 0) {
      ToastUtil.toast("没有找到相关数据");
      return;
    }
    mAdapter.addDataList(rows);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.iv_cencel_video_list:
        finish();
        break;

      case R.id.iv_search_video_list:
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
          imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        mAdapter.addDataList(null);
        mSearchEt.clearFocus();
        mRequest.keyword = mSearchEt.getText().toString().trim();
        mPresenter.start();
        break;

      default:
        break;
    }
  }

  @Override
  public void setPersonal(VideoContract.Presenter p) {
    mPresenter = p;
  }
}
