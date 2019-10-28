package com.hlbd.electric.feature.launcher.event.history;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hlbd.electric.R;
import com.hlbd.electric.api.HttpApi;
import com.hlbd.electric.base.BaseFragment;
import com.hlbd.electric.feature.launcher.event.EventFragment;
import com.hlbd.electric.feature.launcher.event.history.data.EventHistoryData;
import com.hlbd.electric.feature.launcher.event.main.EventMainAdapter;
import com.hlbd.electric.util.LogUtil;

public class EvenHistoryFragment extends BaseFragment implements EventHistoryContract.View, View.OnClickListener {

  private static final String TAG = "ModifyPasswordFragment";
  private EventHistoryContract.Presenter mPresenter;
  private EventHistoryRequest request;
  private EventHistoryAdapter mAdapter;
  private ListView mLv;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    if (mBaseView == null) {
      mBaseView = inflater.inflate(R.layout.fragment_event_history, container, false);
    }
    return mBaseView;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    initView();
    init();
  }

  @Override
  public void setPersonal(EventHistoryContract.Presenter p) {
    mPresenter = p;
  }

  public void startTask() {
    if (mPresenter != null) {
      mPresenter.start();
    }
  }

  private void init() {
    request = new EventHistoryRequest();
    request.username = HttpApi.getUserName();
    request.url = "Baoding_Overview_DataSupport/Services/GetUserAllAlertHistory?";
    request.username = HttpApi.getUserName();

    mPresenter = new EventHistoryPresenter(this, request);
  }

  private void initView() {
    mTitleTv.setText("历史报警");
    mBackIv.setVisibility(View.VISIBLE);
    mBackIv.setOnClickListener(this);

    mAdapter = new EventHistoryAdapter();
    mLv = mBaseView.findViewById(R.id.lv_history_event_history);
    mLv.setAdapter(mAdapter);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.iv_back:
        if (mPage != null) {
          mPage.turnPage(EventFragment.PAGE_EVENT_MAIN);
        }
        break;
      default:
        break;
    }
  }

  @Override
  public void userAllAlertHistoryResult(EventHistoryData data) {
    LogUtil.d(TAG, "userAllAlertHistoryResult() data=" + data);
    if (data == null) {
      mAdapter.clear();
      return;
    }
    mAdapter.addDataList(data.rows);
  }
}