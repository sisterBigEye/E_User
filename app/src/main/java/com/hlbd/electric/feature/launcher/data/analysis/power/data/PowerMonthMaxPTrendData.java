package com.hlbd.electric.feature.launcher.data.analysis.power.data;

import java.util.List;

public class PowerMonthMaxPTrendData {

  private static final String TAG = "PowerMonthMaxPTrendData";

  public DataShape dataShape;

  public List<Row> rows;

  public static class DataShape {

    public FieldDefinitions fieldDefinitions;

    public static class FieldDefinitions {

    }
  }

  public static class Row {

    public float PMaxLastMonth;

    public float PMaxThisMonth;

    public int trendID;

    @Override
    public String toString() {
      return "Row{" +
              "PMaxLastMonth=" + PMaxLastMonth +
              "PMaxThisMonth=" + PMaxThisMonth +
              "trendID=" + trendID +
              '}';
    }
  }

  @Override
  public String toString() {
    return TAG + "{" +
            ", dataShape=" + dataShape +
            ", rows=" + rows +
            '}';
  }
}
