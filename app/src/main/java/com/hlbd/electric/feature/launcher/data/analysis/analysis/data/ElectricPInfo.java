package com.hlbd.electric.feature.launcher.data.analysis.analysis.data;

public class ElectricPInfo {

  public float pMax;

  public float pMin;

  public String xTime;

  public ElectricPInfo(float pMax, float pMin) {
    this(pMax, pMin, "");
  }

  public ElectricPInfo(float pMax, float pMin, String xTime) {
    this.pMax = pMax;
    this.pMin = pMin;
    this.xTime = xTime;
  }

  @Override
  public String toString() {
    return "ElectricPInfo{" +
            "pMax=" + pMax +
            ", pMin=" + pMin +
            ", xTime='" + xTime + '\'' +
            '}';
  }
}
