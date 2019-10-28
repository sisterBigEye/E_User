package com.hlbd.electric.feature.launcher.data.analysis.count;

import com.hlbd.electric.base.BaseResult;

public class CountCostRequest extends BaseResult {

  public String date;

  @Override
  public String toString() {
    return "CountCostRequest{" +
            "date='" + date + '\'' +
            ", url='" + url + '\'' +
            '}';
  }
}
