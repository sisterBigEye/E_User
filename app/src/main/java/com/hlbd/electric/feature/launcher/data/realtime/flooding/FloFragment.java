package com.hlbd.electric.feature.launcher.data.realtime.flooding;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hlbd.electric.R;
import com.hlbd.electric.api.HttpApi;
import com.hlbd.electric.base.BaseFragment;
import com.hlbd.electric.feature.launcher.data.realtime.common.CommonRequest;
import com.hlbd.electric.feature.launcher.data.realtime.flooding.data.FloodingData;
import com.hlbd.electric.model.Information;
import com.hlbd.electric.util.LogUtil;

import java.lang.ref.WeakReference;

public class FloFragment extends BaseFragment implements FloContract.View<Information> {

  private static final String TAG = "FloFragment";
  private View mParentView;
  private FloContract.Presenter mPresenter;
  private EventHandler mHandler;
  private TextView mStateTv;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    if (mBaseView == null) {
      mBaseView = inflater.inflate(R.layout.fragment_info_flo, container, false);
    }
    initView();
    init();
    return mBaseView;
  }


  @Override
  public void setPersonal(FloContract.Presenter p) {
    mPresenter = p;
  }

  public void startTask() {
    LogUtil.d(TAG, "startTask() Presenter=" + mPresenter);
    if (mPresenter == null) {
      return;
    }
    requestEnvironmentData();
  }

  private void init() {
    mHandler = new EventHandler(this);

    CommonRequest floodingRequest = new CommonRequest();
    floodingRequest.userTag = HttpApi.getUserName();
    floodingRequest.url = "Baoding_Overview_DataSupport/Services/GetUsersWaterOutDevice?";

    mPresenter = new FloPresenter(this, floodingRequest);
  }

  private void initView() {
    mStateTv = mBaseView.findViewById(R.id.tv_status_info_flo);
  }

  private void requestEnvironmentDetail(String url) {
    if (url == null || mPresenter == null) {
      return;
    }
    mPresenter.loadFloodingDetail(url);
  }

  @Override
  public void updateFloodingData(Information information) {
    LogUtil.d(TAG, "updateFloodingData() --- information=" + information);
    String name = null;
    if (information != null) {
      name = getName(information.rows);
      requestEnvironmentDetail(name);
    }
  }

  @Override
  public void updateFloodingDetail(FloodingData data) {
    if (data == null) {
      return;
    }

    mStateTv.setText(data.getValue());
  }

  private static class EventHandler extends Handler {

    private final WeakReference<FloFragment> mRef;
    private static final int REQUEST = 1;

    private EventHandler(FloFragment fragment) {
      mRef = new WeakReference<>(fragment);
    }

    @Override
    public void handleMessage(Message msg) {
      FloFragment fragment = mRef.get();
      if (fragment == null) {
        return;
      }
      switch (msg.what) {
        case REQUEST:
          break;
        default:
          break;
      }
    }
  }

  private void requestEnvironmentData() {
    mPresenter.start();
  }

}
