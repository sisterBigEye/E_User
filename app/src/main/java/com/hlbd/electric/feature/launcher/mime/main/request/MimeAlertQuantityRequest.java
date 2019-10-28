package com.hlbd.electric.feature.launcher.mime.main.request;

import com.hlbd.electric.base.BaseResult;

public class MimeAlertQuantityRequest extends BaseResult {

  public String user;

  public String type;

  @Override
  public String toString() {
    return "MimeAlertQuantityRequest{" +
            "user='" + user + '\'' +
            ", type='" + type + '\'' +
            ", url='" + url + '\'' +
            '}';
  }
}
