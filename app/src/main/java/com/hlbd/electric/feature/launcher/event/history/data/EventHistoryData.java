package com.hlbd.electric.feature.launcher.event.history.data;

import java.util.List;

public class EventHistoryData {

  private static final String TAG = "EventHistoryData";

  public DataShape dataShape;

  public List<Row> rows;

  public static class DataShape {

    public FieldDefinitions fieldDefinitions;

    public static class FieldDefinitions {

    }
  }

  public static class Row {

    public String description;

    public String message;

    public String name;

    public String source;

    public long timestamp;


    @Override
    public String toString() {
      return "Row{" +
              "description='" + description + '\'' +
              ", message='" + message + '\'' +
              ", name='" + name + '\'' +
              ", source='" + source + '\'' +
              ", timestamp=" + timestamp +
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
