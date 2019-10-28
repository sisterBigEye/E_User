package com.hlbd.electric.feature.launcher.data.realtime.busbar;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hlbd.electric.R;
import com.hlbd.electric.api.HttpApi;
import com.hlbd.electric.base.BaseFragment;
import com.hlbd.electric.feature.launcher.data.realtime.busbar.data.BusBarData;
import com.hlbd.electric.feature.launcher.data.realtime.common.CommonRequest;
import com.hlbd.electric.feature.launcher.data.realtime.common.DetailAdapter;
import com.hlbd.electric.feature.launcher.data.realtime.common.DetailInfo;
import com.hlbd.electric.feature.launcher.data.realtime.common.IData;
import com.hlbd.electric.model.Information;
import com.hlbd.electric.helper.GridDividerItemDecoration;
import com.hlbd.electric.util.LogUtil;

import java.lang.ref.WeakReference;

public class BusBarFragment extends BaseFragment implements BusBarContract.View<Information> {

  private static final String TAG = "EnvFragment";
  private View mParentView;
  private BusBarContract.Presenter mPresenter;
  private TextView mTempTv;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    if (mBaseView == null) {
      mBaseView = inflater.inflate(R.layout.fragment_info_bus_bar, container, false);
    }
    initView();
    init();
    return mBaseView;
  }

  public void startTask() {
    LogUtil.d(TAG, "startTask() Presenter=" + mPresenter);
    if (mPresenter == null) {
      return;
    }
    requestEnvironmentData();
  }

  private void init() {
    CommonRequest busBarRequest = new CommonRequest();
    busBarRequest.userTag = HttpApi.getUserName();
    busBarRequest.url = "Baoding_Overview_DataSupport/Services/GetUsersTemDevice?";

    mPresenter = new BusBarPresenter(this, busBarRequest);
  }

  private void initView() {
    mTempTv = mBaseView.findViewById(R.id.tv_temp_info_bus_bar);
  }

  private void requestBusBarDetail(String url) {
    if (url == null || mPresenter == null) {
      return;
    }
    mPresenter.loadBusBarDetail(url);
  }

  @Override
  public void updateBusBarData(Information information) {
    LogUtil.d(TAG, "updateBusBarData() --- information=" + information);
    String name = null;
    if (information != null) {
      name = getName(information.rows);
      requestBusBarDetail(name);
    }
  }

  @Override
  public void updateBusBarTemp(BusBarData info) {
    if (info == null) {
      return;
    }
    mTempTv.setText(info.getValue());
  }

  @Override
  public void setPersonal(BusBarContract.Presenter p) {

  }

  private void requestEnvironmentData() {
    mPresenter.start();
  }

}
