package com.hlbd.electric.feature.launcher.data.realtime.video;

import com.hlbd.electric.base.BaseResult;
import com.hlbd.electric.base.IRequest;

public class VideoControlRequest extends BaseResult {

  public int direction;

  public String channelNo;

  public String deviceSerial;

  public int speed;

  @Override
  public int getContentType() {
    return IRequest.DEFAULT;
  }

  @Override
  public String toString() {
    return "VideoControlRequest{" +
            "direction='" + direction + '\'' +
            ", channelNo='" + channelNo + '\'' +
            ", deviceSerial='" + deviceSerial + '\'' +
            ", speed='" + speed + '\'' +
            '}';
  }
}
