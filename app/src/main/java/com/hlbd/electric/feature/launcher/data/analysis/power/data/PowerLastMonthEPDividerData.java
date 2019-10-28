package com.hlbd.electric.feature.launcher.data.analysis.power.data;

import java.util.List;

public class PowerLastMonthEPDividerData {

  private static final String TAG = "PowerLastMonthEPDividerData";

  public DataShape dataShape;

  public List<Row> rows;

  public static class DataShape {

    public FieldDefinitions fieldDefinitions;

    public static class FieldDefinitions {

    }
  }

  public static class Row {

    public int ep;

    public String type;

    @Override
    public String toString() {
      return "Row{" +
              "ep=" + ep +
              "type=" + type +
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
