package com.hlbd.electric.feature.launcher.event.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hlbd.electric.R;
import com.hlbd.electric.api.HttpApi;
import com.hlbd.electric.base.BaseFragment;
import com.hlbd.electric.feature.launcher.event.EventFragment;
import com.hlbd.electric.feature.launcher.event.order.data.EventOrderData;
import com.hlbd.electric.util.LogUtil;

public class EventOrderFragment extends BaseFragment implements EventOrderContract.View, View.OnClickListener {

  private static final String TAG = "ModifyPasswordFragment";
  private EventOrderContract.Presenter mPresenter;
  private EventOrderRequest request;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    if (mBaseView == null) {
      mBaseView = inflater.inflate(R.layout.fragment_event_order, container, false);
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
  public void setPersonal(EventOrderContract.Presenter p) {
    mPresenter = p;
  }

  public void startTask() {
    if (mPresenter != null) {
      mPresenter.start();
    }
  }

  private void init() {
    request = new EventOrderRequest();
    request.username = HttpApi.getUserName();
    request.url = "Baoding_Overview_DataSupport/Services/GetAckAlert?";
    request.username = HttpApi.getUserName();

    mPresenter = new EventOrderPresenter(this, request);
  }

  private void initView() {
    mTitleTv.setText("已派单报警");
    mBackIv.setVisibility(View.VISIBLE);
    mBackIv.setOnClickListener(this);

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
  public void ackAlertResult(EventOrderData data) {
    LogUtil.d(TAG, "ackAlertResult() data=" + data);
  }
}