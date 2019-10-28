package com.hlbd.electric.feature.launcher.event.order.data;

import java.util.List;

public class EventOrderData {

  private static final String TAG = "EventOrderData";

  public DataShape dataShape;

  public List<Row> rows;

  public static class DataShape {

    public FieldDefinitions fieldDefinitions;

    public static class FieldDefinitions {

    }
  }

  public static class Row {

    public String result;


    @Override
    public String toString() {
      return "Row{" +
              "result=" + result +
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
