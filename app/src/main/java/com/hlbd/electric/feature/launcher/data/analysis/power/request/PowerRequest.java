package com.hlbd.electric.feature.launcher.data.analysis.power.request;

import com.hlbd.electric.base.BaseResult;

public class PowerRequest extends BaseResult {

  public String username;

  public String password;

  @Override
  public String toString() {
    return "PowerRequest{" +
            "username='" + username + '\'' +
            ", url='" + url + '\'' +
            '}';
  }
}
