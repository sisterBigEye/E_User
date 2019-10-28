package com.hlbd.electric.feature.launcher.data.analysis.power.data;

import java.util.List;

public class PowerMonthEPPriceData {

  private static final String TAG = "PowerMonthEPPriceData";

  public DataShape dataShape;

  public List<Row> rows;

  public static class DataShape {

    public FieldDefinitions fieldDefinitions;

    public static class FieldDefinitions {

    }
  }

  public static class Row {

    public String lastMonth;

    public String thisMonth;

    @Override
    public String toString() {
      return "Row{" +
              "lastMonth=" + lastMonth +
              "thisMonth=" + thisMonth +
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
