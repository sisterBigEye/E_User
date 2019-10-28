package com.hlbd.electric.feature.launcher.event.main.data;

import java.util.List;

public class EventMainData {
  private static final String TAG = "EventMainData";

  public DataShape dataShape;

  public List<Row> rows;

  public static class DataShape {

    public FieldDefinitions fieldDefinitions;

    public static class FieldDefinitions {

    }
  }

  public static class Row {

    public transient boolean isSelect;

    public int id;

    public Location location;

    public String source;

    public String sourceType;

    public List<Tag> tags;

    public long timestamp;

    public String alertType;

    public String description;

    public String eventName;

    public String message;

    public String name;

    public int priority;

    public String sourceProperty;

    public static class Location {
      public int latitude;
      public int longitude;
      public int elevation;
    }

    public static class Tag{}

    @Override
    public String toString() {
      return "Row{" +
              "id=" + id +
              ", location=" + location +
              ", source='" + source + '\'' +
              ", sourceType='" + sourceType + '\'' +
              ", tags=" + tags +
              ", timestamp=" + timestamp +
              ", alertType='" + alertType + '\'' +
              ", description='" + description + '\'' +
              ", eventName='" + eventName + '\'' +
              ", message='" + message + '\'' +
              ", name='" + name + '\'' +
              ", priority=" + priority +
              ", sourceProperty='" + sourceProperty + '\'' +
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
