package com.hlbd.electric.feature.launcher.data.realtime.video;

import com.hlbd.electric.base.BaseResult;

public class VideoPlaybackRequest extends BaseResult {

  public long startTime;

  public long endTime;

  public String videourl;

  @Override
  public String toString() {
    return "VideoPlaybackRequest{" +
            "startTime=" + startTime +
            ", endTime=" + endTime +
            ", videourl='" + videourl + '\'' +
            '}';
  }
}
