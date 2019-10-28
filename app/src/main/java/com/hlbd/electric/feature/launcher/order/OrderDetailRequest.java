package com.hlbd.electric.feature.launcher.order;

import com.hlbd.electric.base.BaseResult;

public class OrderDetailRequest extends BaseResult {

  public String repairSN;

  @Override
  public String toString() {
    return "OrderDetailRequest{" +
            "repairSN='" + repairSN + '\'' +
            ", url='" + url + '\'' +
            '}';
  }
}
