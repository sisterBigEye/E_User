package com.hlbd.electric.feature.launcher.event.main;

import com.hlbd.electric.api.Callback;
import com.hlbd.electric.api.ITask;
import com.hlbd.electric.feature.launcher.event.main.data.EventMainCommitResponse;
import com.hlbd.electric.feature.launcher.event.main.data.EventMainData;
import com.hlbd.electric.task.RequestDataTask;
import com.hlbd.electric.util.LogUtil;

public class EventMainPresenter implements EventMainContract.Presenter {
  private static final String TAG = "EventMainPresenter";
  private EventMainContract.View<EventMainData> view;
  private EventMainRequest request;
  private ITask<EventMainData> task;

  EventMainPresenter(EventMainContract.View<EventMainData> view, EventMainRequest request) {
    this.view = view;
    this.request = request;
    this.view.setPersonal(this);
  }

  @Override
  public void start() {
    loadEventData();
  }

  @Override
  public void loadEventData() {
    if (view == null || request == null) {
      LogUtil.e(TAG, "loadEventData() --- view is null");
      return;
    }
    if (task == null) {
      task = new RequestDataTask<>(EventMainData.class);
    }
    LogUtil.d(TAG, "loadEventData() --- map = " + request.toString());
    task.startTask(request, new Callback<EventMainData>() {

      @Override
      public void result(EventMainData data) {
        view.updateEventData(data);
      }
    });
  }

  @Override
  public void commitEvent(EventMainCommitRequest request) {
    new RequestDataTask<>(EventMainCommitResponse.class).startTask(request, new Callback<EventMainCommitResponse>() {
      @Override
      public void result(EventMainCommitResponse eventMainCommitResponse) {
        view.commitResult(eventMainCommitResponse);
      }
    });
  }
}
