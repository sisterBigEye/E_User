package com.hlbd.electric.feature.launcher.data.realtime.video.list;

import com.hlbd.electric.base.BaseResult;

public class VideoRequest extends BaseResult {

  public String username;

  public String keyword;

  public VideoRequest(String username) {
    this.username = username;
  }
}
