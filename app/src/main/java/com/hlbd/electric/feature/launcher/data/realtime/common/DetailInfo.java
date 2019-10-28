package com.hlbd.electric.feature.launcher.data.realtime.common;


public class DetailInfo {

  public int resId;

  public String desc;

  public String value;

  public DetailInfo(IData data) {
    if (data != null) {
      resId = data.getResId();
      desc = data.getDesc();
      value = data.getValue();
    }
  }

  @Override
  public String toString() {
    return "DetailInfo{" +
            "resId=" + resId +
            ", desc='" + desc + '\'' +
            ", value='" + value + '\'' +
            '}';
  }
}
