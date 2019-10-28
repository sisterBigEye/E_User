package com.hlbd.electric.feature.launcher.data.realtime.env;

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
import com.hlbd.electric.feature.launcher.data.realtime.common.CommonRequest;
import com.hlbd.electric.feature.launcher.data.realtime.common.DetailAdapter;
import com.hlbd.electric.feature.launcher.data.realtime.common.DetailInfo;
import com.hlbd.electric.feature.launcher.data.realtime.common.IData;
import com.hlbd.electric.model.Information;
import com.hlbd.electric.feature.launcher.data.realtime.env.data.EnvDataIHumidity;
import com.hlbd.electric.feature.launcher.data.realtime.env.data.EnvDataITemp;
import com.hlbd.electric.helper.GridDividerItemDecoration;
import com.hlbd.electric.util.LogUtil;

import java.lang.ref.WeakReference;

public class EnvFragment extends BaseFragment implements EnvContract.View<Information> {

  private static final String TAG = "EnvFragment";
  private View mParentView;
  private EnvContract.Presenter mPresenter;
  private EventHandler mHandler;

  private TextView mTempDescTv;
  private TextView mTempValueTv;

  private TextView mHumidityDescTv;
  private TextView mHumidityValueTv;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    if (mParentView == null) {
      mParentView = inflater.inflate(R.layout.fragment_info_env, container, false);
    }
    initView();
    init();
    return mParentView;
  }

  @Override
  public void updateEnvironmentData(Information information) {
    LogUtil.d(TAG, "updateEnvironmentData() --- information=" + information);
    String name = null;
    if (information != null) {
      name = getName(information.rows);
      requestEnvironmentDetail(name);
    }
  }

  @Override
  public void updateEnvironmentTemp(EnvDataITemp info) {
    if (info == null) {
      return;
    }
    mTempDescTv.setText(info.getDesc());
    mTempValueTv.setText(info.getValue());
  }

  @Override
  public void updateEnvironmentHumidity(EnvDataIHumidity info) {
    if (info == null) {
      return;
    }
    mHumidityDescTv.setText(info.getDesc());
    mHumidityValueTv.setText(info.getValue());
  }

  @Override
  public void setPersonal(EnvContract.Presenter p) {
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

    CommonRequest environmentRequest = new CommonRequest();
    environmentRequest.userTag = HttpApi.getUserName();
    environmentRequest.url = "Baoding_Overview_DataSupport/Services/GetUsersEnvDevice?";

    mPresenter = new EnvPresenter(this, environmentRequest);
  }

  private void initView() {
    mTempDescTv = mParentView.findViewById(R.id.tv_temp_desc_info_env);
    mTempValueTv = mParentView.findViewById(R.id.tv_temp_value_info_env);

    mHumidityDescTv = mParentView.findViewById(R.id.tv_humidity_desc_info_env);
    mHumidityValueTv = mParentView.findViewById(R.id.tv_humidity_value_info_env);
  }

  private void requestEnvironmentDetail(String url) {
    if (url == null || mPresenter == null) {
      return;
    }
    mPresenter.loadEnvironmentDetail(url);
  }

  private static class EventHandler extends Handler {

    private final WeakReference<EnvFragment> mRef;
    private static final int REQUEST = 1;

    private EventHandler(EnvFragment fragment) {
      mRef = new WeakReference<>(fragment);
    }

    @Override
    public void handleMessage(Message msg) {
      EnvFragment fragment = mRef.get();
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
