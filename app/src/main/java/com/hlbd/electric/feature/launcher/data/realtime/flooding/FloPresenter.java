package com.hlbd.electric.feature.launcher.data.realtime.flooding;

import com.hlbd.electric.api.Callback;
import com.hlbd.electric.api.ITask;
import com.hlbd.electric.feature.launcher.data.realtime.common.CommonRequest;
import com.hlbd.electric.model.Information;
import com.hlbd.electric.feature.launcher.data.realtime.flooding.data.FloodingData;
import com.hlbd.electric.task.RequestDataTask;
import com.hlbd.electric.util.LogUtil;

public class FloPresenter implements FloContract.Presenter {
  private static final String TAG = "FloContract";
  private FloContract.View<Information> view;
  private CommonRequest mFloodingRequest;
  private ITask<Information> mEnvironmentTask;

  FloPresenter(FloContract.View<Information> view,
               CommonRequest floodingRequest) {
    this.view = view;
    this.mFloodingRequest = floodingRequest;
    this.view.setPersonal(this);
  }

  @Override
  public void start() {
    loadFloodingData();
  }


  @Override
  public void loadFloodingData() {
    if (view == null || mFloodingRequest == null) {
      LogUtil.e(TAG, "loadFloodingData() --- view is null");
      return;
    }

    if (mEnvironmentTask == null) {
      mEnvironmentTask = new RequestDataTask<>(Information.class);
    }
    LogUtil.d(TAG, "loadFloodingData() --- FloodingRequest = " + mFloodingRequest);
    mEnvironmentTask.startTask(mFloodingRequest, new Callback<Information>() {

      @Override
      public void result(Information information) {
        view.updateFloodingData(information);
      }
    });
  }

  @Override
  public void loadFloodingDetail(String url) {
    String tempUrl = url + "/Properties/WaterOutStatus";
    new RequestDataTask<>(FloodingData.class).startTask(tempUrl, new Callback<FloodingData>() {
      @Override
      public void result(FloodingData data) {
        view.updateFloodingDetail(data);
      }
    });
  }

}
