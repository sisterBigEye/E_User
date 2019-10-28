package com.hlbd.electric.feature.launcher.event.main.data;

import java.util.List;

public class EventMainCommitResponse {

  private static final String TAG = "EventMainCommitResponse";

  public DataShape dataShape;

  public List<Row> rows;

  public static class DataShape {

    public FieldDefinitions fieldDefinitions;

    public static class FieldDefinitions {

    }
  }

  public static class Row {
    String result;

    @Override
    public String toString() {
      return "Row{" +
              "result='" + result + '\'' +
              '}';
    }
  }

  @Override
  public String toString() {
    return "EventMainData{" +
            ", dataShape=" + dataShape +
            ", rows=" + rows +
            '}';
  }
}
