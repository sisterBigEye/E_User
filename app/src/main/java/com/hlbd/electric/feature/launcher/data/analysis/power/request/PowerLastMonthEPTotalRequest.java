package com.hlbd.electric.feature.launcher.data.analysis.power.request;

import com.hlbd.electric.base.BaseResult;
import com.hlbd.electric.base.IRequest;

public class PowerLastMonthEPTotalRequest extends BaseResult {

  @Override
  public int getContentType() {
    return IRequest.EMPTY;
  }
}
