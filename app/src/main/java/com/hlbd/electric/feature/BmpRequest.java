package com.hlbd.electric.feature;

import com.hlbd.electric.base.BaseResult;

public class BmpRequest extends BaseResult {

  public static final int DEFAULT_RESULT_TIME = 2 * 60 * 1000;

  public String username;

  @Override
  public String toString() {
    return "BmpRequest{" +
            "username='" + username + '\'' +
            ", url='" + url + '\'' +
            '}';
  }
}
