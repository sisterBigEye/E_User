package com.hlbd.electric.feature.launcher.mime.main.request;

import com.hlbd.electric.base.BaseResult;

public class MimeInfoRequest extends BaseResult {

  public String username;

  @Override
  public String toString() {
    return "MimeInfoRequest{" +
            "username='" + username + '\'' +
            ", url='" + url + '\'' +
            '}';
  }
}
