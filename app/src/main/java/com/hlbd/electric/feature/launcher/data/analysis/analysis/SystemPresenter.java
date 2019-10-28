package com.hlbd.electric.feature.launcher.data.analysis.analysis;

import com.hlbd.electric.api.Callback;
import com.hlbd.electric.api.ITask;
import com.hlbd.electric.feature.launcher.data.analysis.analysis.data.SystemData;
import com.hlbd.electric.task.RequestDataTask;
import com.hlbd.electric.util.LogUtil;

public class SystemPresenter implements SystemContract.Presenter {
  private static final String TAG = "PowerPresenter";
  private SystemContract.View view;
  private SystemDataRequest dataRequest;
  private ITask<SystemData> dataTask;

  SystemPresenter(SystemContract.View view, SystemDataRequest dataRequest) {
    this.view = view;
    this.dataRequest = dataRequest;
    this.view.setPersonal(this);
  }

  @Override
  public void start() {
    loadSystemData();
  }


  @Override
  public void loadSystemData() {
    if (view == null || dataRequest == null) {
      LogUtil.e(TAG, "loadSystemData() --- view is null");
      return;
    }
    if (dataTask == null) {
      dataTask = new RequestDataTask<>(SystemData.class);
    }
    LogUtil.d(TAG, "loadCostData() --- map = " + dataRequest.toString());
    dataTask.startTask(dataRequest, new Callback<SystemData>() {

      @Override
      public void result(SystemData data) {
        view.updateSystemData(data);
      }
    });
  }
}
