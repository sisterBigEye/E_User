package com.hlbd.electric.feature.launcher.data.realtime.video.list;

import com.hlbd.electric.base.BaseResult;

public class VideoListRequest extends BaseResult {

  public String username;

  public String keyword;

  public VideoListRequest(String username) {
    this.username = username;
  }
}
