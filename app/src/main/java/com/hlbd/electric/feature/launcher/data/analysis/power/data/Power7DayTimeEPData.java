package com.hlbd.electric.feature.launcher.data.analysis.power.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Power7DayTimeEPData {

  private static final String TAG = "PoweTodayRealTimePData";

  @SerializedName("xAxis")
  public List<String> x;

  public DataColor color;

  public List<String> legend;

  public UnitData unitdata;

  @SerializedName("YAxisName")
  public String yAxisName;

  public static class DataColor {

    @SerializedName("平")
    public String lowest;

    @SerializedName("谷")
    public String secondLow;

    @SerializedName("峰")
    public String secondHighest;

    @SerializedName("尖")
    public String highest;

    @Override
    public String toString() {
      return "DataColor{" +
              "lowest='" + lowest +
              ", secondLow='" + secondLow +
              ", secondHighest='" + secondHighest +
              ", highest='" + highest +
              '}';
    }
  }

  public static class UnitData {

    @SerializedName("平")
    public List<Float> lowest;

    @SerializedName("谷")
    public List<Float> secondLow;

    @SerializedName("峰")
    public List<Float> secondHighest;

    @SerializedName("尖")
    public List<Float> highest;

    @Override
    public String toString() {
      return "UnitData{" +
              "lowest=" + lowest +
              ", secondLow=" + secondLow +
              ", secondHighest=" + secondHighest +
              ", highest=" + highest +
              '}';
    }
  }

  @Override
  public String toString() {
    return TAG + "{" +
            "x=" + x +
            ", color=" + color +
            ", legend=" + legend +
            ", unitdata=" + unitdata +
            ", yAxisName='" + yAxisName +
            '}';
  }
}
