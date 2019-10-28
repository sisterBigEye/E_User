package com.hlbd.electric.feature.launcher.event.history;

import com.hlbd.electric.base.BaseResult;

public class EventHistoryRequest extends BaseResult {

  public String username;

  @Override
  public String toString() {
    return "EventHistoryRequest{" +
            "username='" + username + '\'' +
            ", url='" + url + '\'' +
            '}';
  }

}
