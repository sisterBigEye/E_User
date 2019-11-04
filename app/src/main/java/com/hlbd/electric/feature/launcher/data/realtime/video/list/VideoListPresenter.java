package com.hlbd.electric.feature.launcher.data.realtime.video.list;

import com.hlbd.electric.api.Callback;
import com.hlbd.electric.api.ITask;
import com.hlbd.electric.task.RequestDataTask;

public class VideoListPresenter implements VideoListContract.Presenter {
  private static final String TAG = "VideoListPresenter";
  private VideoListContract.View<VideoListInfo> view;
  private VideoListRequest mRequest;
  private ITask<VideoListInfo> videoTask;

  VideoListPresenter(VideoListContract.View<VideoListInfo> view, VideoListRequest request) {
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
      videoTask = new RequestDataTask<>(VideoListInfo.class);
    }
    videoTask.startTask(mRequest, new Callback<VideoListInfo>() {
      @Override
      public void result(VideoListInfo info) {
        view.notifyVideoInfo(info);
      }

    });
  }
}
