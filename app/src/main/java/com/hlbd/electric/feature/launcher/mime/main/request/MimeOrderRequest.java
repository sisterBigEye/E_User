package com.hlbd.electric.feature.launcher.mime.main.request;

import com.hlbd.electric.base.BaseResult;

public class MimeOrderRequest extends BaseResult {

  public String user;

  public String type;

  @Override
  public String toString() {
    return "MimeOrderRequest{" +
            "user='" + user + '\'' +
            ", type='" + type + '\'' +
            ", url='" + url + '\'' +
            '}';
  }
}
