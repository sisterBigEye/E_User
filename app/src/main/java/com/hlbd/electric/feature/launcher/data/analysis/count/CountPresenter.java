package com.hlbd.electric.feature.launcher.data.analysis.count;

import com.hlbd.electric.api.Callback;
import com.hlbd.electric.api.ITask;
import com.hlbd.electric.feature.launcher.data.analysis.count.data.CountCostData;
import com.hlbd.electric.feature.launcher.data.analysis.count.data.CountPowerData;
import com.hlbd.electric.task.RequestDataTask;
import com.hlbd.electric.util.LogUtil;

public class CountPresenter implements CountContract.Presenter {
  private static final String TAG = "PowerPresenter";
  private CountContract.View view;

  private CountPowerRequest powerRequest;
  private CountCostRequest costRequest;

  private ITask<CountPowerData> powerTask;
  private ITask<CountCostData> costTask;

  CountPresenter(CountContract.View view, CountPowerRequest powerRequest, CountCostRequest costRequest) {
    this.view = view;
    this.powerRequest = powerRequest;
    this.costRequest = costRequest;
    this.view.setPersonal(this);
  }

  @Override
  public void start() {
    loadCostData();
    loadPowerData();
  }


  @Override
  public void loadCostData() {
    if (view == null || costRequest == null) {
      LogUtil.e(TAG, "loadCostData() --- view is null");
      return;
    }
    if (costTask == null) {
      costTask = new RequestDataTask<>(CountCostData.class);
    }
    LogUtil.d(TAG, "loadCostData() --- map = " + costRequest.toString());
    costTask.startTask(costRequest, new Callback<CountCostData>() {

      @Override
      public void result(CountCostData data) {
        view.updateCountCostData(data);
      }
    });
  }

  @Override
  public void loadPowerData() {
    if (view == null || powerRequest == null) {
      LogUtil.e(TAG, "loadPowerData() --- view is null");
      return;
    }
    if (powerTask == null) {
      powerTask = new RequestDataTask<>(CountPowerData.class);
    }
    LogUtil.d(TAG, "loadPowerData() --- map = " + powerRequest.toString());
    powerTask.startTask(powerRequest, new Callback<CountPowerData>() {

      @Override
      public void result(CountPowerData data) {
        view.updateCountPowerData(data);
      }
    });
  }
}
