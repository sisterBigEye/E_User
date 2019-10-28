package com.hlbd.electric.feature.launcher.event.order;

import com.hlbd.electric.base.BaseResult;

public class EventOrderRequest extends BaseResult {

  public String username;

  @Override
  public String toString() {
    return "EventOrderRequest{" +
            "username='" + username + '\'' +
            ", url='" + url + '\'' +
            '}';
  }

}
