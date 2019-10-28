package com.hlbd.electric.feature.launcher.report;

import com.hlbd.electric.base.BaseResult;
import com.hlbd.electric.base.IRequest;

public class ReportTypeRequest extends BaseResult {

  @Override
  public int getContentType() {
    return IRequest.EMPTY;
  }

  @Override
  public String toString() {
    return "LoginRequest{"
            + ", url='" + url + '\'' +
            '}';
  }

}
