package com.hlbd.electric.feature.launcher.event.history;

import com.hlbd.electric.api.Callback;
import com.hlbd.electric.api.ITask;
import com.hlbd.electric.feature.launcher.event.history.data.EventHistoryData;
import com.hlbd.electric.task.RequestDataTask;
import com.hlbd.electric.util.LogUtil;

public class EventHistoryPresenter implements EventHistoryContract.Presenter {
  private static final String TAG = "EventHistoryPresenter";
  private EventHistoryContract.View view;
  private EventHistoryRequest request;
  private ITask<EventHistoryData> task;

  EventHistoryPresenter(EventHistoryContract.View view, EventHistoryRequest request) {
    this.view = view;
    this.request = request;
    this.view.setPersonal(this);
  }

  @Override
  public void start() {
    getUserAllAlertHistory();
  }

  @Override
  public void getUserAllAlertHistory() {
    if (view == null || request == null) {
      LogUtil.e(TAG, "getUserAllAlertHistory() --- view is null");
      return;
    }
    if (task == null) {
      task = new RequestDataTask<>(EventHistoryData.class);
    }
    LogUtil.d(TAG, "getUserAllAlertHistory() --- map = " + request.toString());
    task.startTask(request, new Callback<EventHistoryData>() {

      @Override
      public void result(EventHistoryData data) {
        view.userAllAlertHistoryResult(data);
      }
    });
  }
}
