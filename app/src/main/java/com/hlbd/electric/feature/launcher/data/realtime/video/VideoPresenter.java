package com.hlbd.electric.feature.launcher.data.realtime.video;

import com.hlbd.electric.api.Callback;
import com.hlbd.electric.api.ITask;
import com.hlbd.electric.feature.launcher.data.realtime.video.list.VideoListInfo;
import com.hlbd.electric.feature.launcher.data.realtime.video.list.VideoListRequest;
import com.hlbd.electric.task.RequestDataTask;

public class VideoPresenter implements VideoContract.Presenter {
  private static final String TAG = "VideoListPresenter";
  private VideoContract.View view;
  private VideoControlRequest mControlRequest;
  private VideoChannelRequest mChannelRequest;
  private VideoPlaybackRequest mVideoPlaybackRequest;
  private VideoListRequest mVideoListRequest;

  private ITask<VideoControlResult> videoControlTask;
  private ITask<VideoChannel> videoChannelTask;
  private ITask<VideoPlaybackResult> videoPlaybackTask;

  private ITask<VideoListInfo> videoListTask;

  VideoPresenter(VideoContract.View view,
                 VideoControlRequest controlRequest,
                 VideoChannelRequest channelRequest,
                 VideoPlaybackRequest videoPlaybackRequest,
                 VideoListRequest request) {
    this.view = view;
    this.mControlRequest = controlRequest;
    this.mChannelRequest = channelRequest;
    this.mVideoPlaybackRequest = videoPlaybackRequest;
    this.mVideoListRequest = request;
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

  @Override
  public void playbackRequest() {
    if (videoPlaybackTask == null) {
      videoPlaybackTask = new RequestDataTask<>(VideoPlaybackResult.class);
    }
    videoPlaybackTask.startTask(mVideoPlaybackRequest, new Callback<VideoPlaybackResult>() {
      @Override
      public void result(VideoPlaybackResult result) {
        view.playbackResult(result);
      }

    });
  }

  @Override
  public void loadVideoInfo() {
    if (videoListTask == null) {
      videoListTask = new RequestDataTask<>(VideoListInfo.class);
    }
    videoListTask.startTask(mVideoListRequest, new Callback<VideoListInfo>() {
      @Override
      public void result(VideoListInfo info) {
        view.notifyVideoInfo(info);
      }

    });
  }
}
