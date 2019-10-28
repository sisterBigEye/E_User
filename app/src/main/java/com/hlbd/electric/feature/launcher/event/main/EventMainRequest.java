package com.hlbd.electric.feature.launcher.event.main;

import com.hlbd.electric.base.BaseResult;
import com.hlbd.electric.base.IRequest;

public class EventMainRequest extends BaseResult {

  public String userName;

  @Override
  public int getContentType() {
    return IRequest.DEFAULT;
  }

  @Override
  public String toString() {
    return "LoginRequest{" +
            "userName='" + userName + '\'' +
            ", url='" + url + '\'' +
            '}';
  }

}
