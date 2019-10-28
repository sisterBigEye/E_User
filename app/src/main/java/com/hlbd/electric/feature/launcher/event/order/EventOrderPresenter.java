package com.hlbd.electric.feature.launcher.event.order;

import com.hlbd.electric.api.Callback;
import com.hlbd.electric.api.ITask;
import com.hlbd.electric.feature.launcher.event.order.data.EventOrderData;
import com.hlbd.electric.task.RequestDataTask;
import com.hlbd.electric.util.LogUtil;

public class EventOrderPresenter implements EventOrderContract.Presenter {
  private static final String TAG = "EventOrderPresenter";
  private EventOrderContract.View view;
  private EventOrderRequest request;
  private ITask<EventOrderData> task;

  EventOrderPresenter(EventOrderContract.View view, EventOrderRequest request) {
    this.view = view;
    this.request = request;
    this.view.setPersonal(this);
  }

  @Override
  public void start() {
    getAckAlert();
  }

  @Override
  public void getAckAlert() {
    if (view == null || request == null) {
      LogUtil.e(TAG, "getAckAlert() --- view is null");
      return;
    }
    if (task == null) {
      task = new RequestDataTask<>(EventOrderData.class);
    }
    LogUtil.d(TAG, "getAckAlert() --- map = " + request.toString());
    task.startTask(request, new Callback<EventOrderData>() {

      @Override
      public void result(EventOrderData data) {
        view.ackAlertResult(data);
      }
    });
  }
}
