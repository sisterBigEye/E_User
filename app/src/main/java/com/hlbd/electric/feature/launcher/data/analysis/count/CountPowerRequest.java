package com.hlbd.electric.feature.launcher.data.analysis.count;

import com.hlbd.electric.base.BaseResult;

public class CountPowerRequest extends BaseResult {

  public String date;

  @Override
  public String toString() {
    return "CountPowerRequest{" +
            "date='" + date + '\'' +
            ", url='" + url + '\'' +
            '}';
  }
}
