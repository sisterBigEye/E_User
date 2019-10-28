package com.hlbd.electric.feature.launcher.order;

import com.hlbd.electric.base.BaseResult;

public class OrderRequest extends BaseResult {

  public String user;

  @Override
  public String toString() {
    return "LoginRequest{" +
            "user='" + user + '\'' +
            ", url='" + url + '\'' +
            '}';
  }

}
