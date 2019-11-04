package com.hlbd.electric.feature.launcher.data.realtime.video;

import com.hlbd.electric.api.Callback;
import com.hlbd.electric.api.ITask;
import com.hlbd.electric.task.RequestDataTask;

public class VideoPresenter implements VideoContract.Presenter {
  private static final String TAG = "VideoListPresenter";
  private VideoContract.View view;
  private VideoControlRequest mControlRequest;
  private VideoChannelRequest mChannelRequest;
  private ITask<VideoControlResult> videoControlTask;
  private ITask<VideoChannel> videoChannelTask;

  VideoPresenter(VideoContract.View view, VideoControlRequest controlRequest,
                 VideoChannelRequest channelRequest) {
    this.view = view;
    this.mControlRequest = controlRequest;
    this.mChannelRequest = channelRequest;
    this.view.setPersonal(this);
  }

  @Override
  public void start() {
    requestChannel();
  }

  @Override
  public void requestChannel() {
    if (videoChannelTask == null) {
      videoChannelTask = new RequestDataTask<>(VideoChannel.class);
    }
    videoChannelTask.startTask(mChannelRequest, new Callback<VideoChannel>() {
      @Override
      public void result(VideoChannel channel) {
        view.channelResult(channel);
      }

    });
  }

  @Override
  public void controlVideo() {
    if (videoControlTask == null) {
      videoControlTask = new RequestDataTask<>(VideoControlResult.class);
    }
    videoControlTask.startTask(mControlRequest, new Callback<VideoControlResult>() {
      @Override
      public void result(VideoControlResult result) {
        view.controlVideoResult(result);
      }

    });
  }
}
