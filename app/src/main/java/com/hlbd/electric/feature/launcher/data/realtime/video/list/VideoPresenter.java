package com.hlbd.electric.feature.launcher.data.realtime.video.list;

import com.hlbd.electric.api.Callback;
import com.hlbd.electric.api.ITask;
import com.hlbd.electric.task.RequestDataTask;

public class VideoPresenter implements VideoContract.Presenter {
  private static final String TAG = "VideoPresenter";
  private VideoContract.View<VideoInfo> view;
  private VideoRequest mRequest;
  private ITask<VideoInfo> videoTask;

  VideoPresenter(VideoContract.View<VideoInfo> view, VideoRequest request) {
    this.view = view;
    this.mRequest = request;
    this.view.setPersonal(this);
  }

  @Override
  public void start() {
    loadVideoInfo();
  }

  @Override
  public void loadVideoInfo() {
    if (videoTask == null) {
      videoTask = new RequestDataTask<>(VideoInfo.class);
    }
    videoTask.startTask(mRequest, new Callback<VideoInfo>() {
      @Override
      public void result(VideoInfo info) {
        view.notifyVideoInfo(info);
      }

    });
  }
}
