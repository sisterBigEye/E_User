package com.hlbd.electric.feature.launcher.data.realtime.busbar;

import com.hlbd.electric.api.Callback;
import com.hlbd.electric.api.ITask;
import com.hlbd.electric.feature.launcher.data.realtime.busbar.data.BusBarData;
import com.hlbd.electric.feature.launcher.data.realtime.common.CommonRequest;
import com.hlbd.electric.model.Information;
import com.hlbd.electric.task.RequestDataTask;
import com.hlbd.electric.util.LogUtil;

public class BusBarPresenter implements BusBarContract.Presenter {

  private static final String TAG = "EnvPresenter";
  private BusBarContract.View<Information> view;
  private CommonRequest mBusBarRequest;
  private ITask<Information> mBusBarTask;

  BusBarPresenter(BusBarContract.View<Information> view,
               CommonRequest busBarRequest) {
    this.view = view;
    this.mBusBarRequest = busBarRequest;
    this.view.setPersonal(this);
  }

  @Override
  public void start() {
    loadBusBarData();
  }



  @Override
  public void loadBusBarData() {
    if (view == null || mBusBarRequest == null) {
      LogUtil.e(TAG, "loadBusBarData() --- view is null");
      return;
    }

    if (mBusBarTask == null) {
      mBusBarTask = new RequestDataTask<>(Information.class);
    }
    LogUtil.d(TAG, "loadBusBarData() --- BusBarRequest = " + mBusBarRequest.toString());
    mBusBarTask.startTask(mBusBarRequest, new Callback<Information>() {

      @Override
      public void result(Information information) {
        view.updateBusBarData(information);
      }
    });
  }

  @Override
  public void loadBusBarDetail(String url) {
    String tempUrl = url + "/Properties/TempBusbar";
    new RequestDataTask<>(BusBarData.class).startTask(tempUrl, new Callback<BusBarData>() {
      @Override
      public void result(BusBarData info) {
        view.updateBusBarTemp(info);
      }
    });

  }
}
