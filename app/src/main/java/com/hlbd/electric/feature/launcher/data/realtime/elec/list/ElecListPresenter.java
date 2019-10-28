package com.hlbd.electric.feature.launcher.data.realtime.elec.list;

import com.hlbd.electric.api.Callback;
import com.hlbd.electric.api.ITask;
import com.hlbd.electric.feature.launcher.data.realtime.common.CommonRequest;
import com.hlbd.electric.model.Information;
import com.hlbd.electric.task.RequestDataTask;
import com.hlbd.electric.util.LogUtil;

public class ElecListPresenter implements ElecListContract.Presenter {
  private static final String TAG = "ElecPresenter";
  private ElecListContract.View<Information> view;
  private CommonRequest mElectricRequest;

  private ITask<Information> mElectricTask;

  ElecListPresenter(ElecListContract.View<Information> view,
                    CommonRequest electricRequest) {
    this.view = view;
    this.mElectricRequest = electricRequest;
    this.view.setPersonal(this);
  }

  @Override
  public void start() {
    loadElectricData();
  }

  @Override
  public void loadElectricData() {
    if (view == null || mElectricRequest == null) {
      LogUtil.e(TAG, "loadElectricData() --- view is null");
      return;
    }

    if (mElectricTask == null) {
      mElectricTask = new RequestDataTask<>(Information.class);
    }
    LogUtil.d(TAG, "loadElectricData() --- ElectricRequest = " + mElectricRequest.toString());
    mElectricTask.startTask(mElectricRequest, new Callback<Information>() {

      @Override
      public void result(Information information) {
        view.updateElectricData(information);
      }
    });
  }
}
