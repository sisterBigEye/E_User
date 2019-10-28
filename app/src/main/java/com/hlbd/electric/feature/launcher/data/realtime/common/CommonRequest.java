package com.hlbd.electric.feature.launcher.data.realtime.common;

import com.hlbd.electric.base.BaseResult;

public class CommonRequest extends BaseResult {

  public static final int DEFAULT_RESULT_TIME = 2 * 60 * 1000;

  public String userTag;

  @Override
  public String toString() {
    return "CommonRequest{" +
            "url='" + url + '\'' +
            ", userTag='" + userTag + '\'' +
            '}';
  }
}
