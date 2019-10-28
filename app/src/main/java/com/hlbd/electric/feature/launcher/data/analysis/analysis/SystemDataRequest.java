package com.hlbd.electric.feature.launcher.data.analysis.analysis;

import com.hlbd.electric.base.BaseResult;

public class SystemDataRequest extends BaseResult {

  public long datetime;

  public String dateType;

  @Override
  public String toString() {
    return "SystemDataRequest{" +
            "datetime='" + datetime + '\'' +
            "dateType" + dateType + '\'' +
            '}';
  }
}
