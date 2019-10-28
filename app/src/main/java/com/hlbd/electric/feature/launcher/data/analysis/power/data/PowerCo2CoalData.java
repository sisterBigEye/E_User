package com.hlbd.electric.feature.launcher.data.analysis.power.data;

import java.util.List;

public class PowerCo2CoalData {

  private static final String TAG = "PowerCo2CoalData";

  public DataShape dataShape;

  public List<Row> rows;

  public static class DataShape {

    public FieldDefinitions fieldDefinitions;

    public static class FieldDefinitions {

    }
  }

  public static class Row {

    public float co2;

    public float coal;

    @Override
    public String toString() {
      return "Row{" +
              "co2=" + co2 +
              ", coal=" + coal +
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
