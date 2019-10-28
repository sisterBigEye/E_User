package com.hlbd.electric.feature.launcher.data.analysis.power.data;

import java.util.List;

public class PowerPMaxData {

  private static final String TAG = "PowerPMaxData";

  public DataShape dataShape;

  public List<Row> rows;

  public static class DataShape {

    public FieldDefinitions fieldDefinitions;

    public static class FieldDefinitions {

    }
  }

  public static class Row {

    public float pmax;
    public long recordtimeslot;

    @Override
    public String toString() {
      return "Row{" +
              "pmax=" + pmax +
              ", recordtimeslot=" + recordtimeslot +
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
