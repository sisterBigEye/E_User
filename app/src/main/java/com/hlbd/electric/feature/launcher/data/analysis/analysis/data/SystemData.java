package com.hlbd.electric.feature.launcher.data.analysis.analysis.data;

import java.util.List;

public class SystemData {

  private static final String TAG = "SystemData";

  public DataShape dataShape;

  public List<Row> rows;

  public static class DataShape {

    public FieldDefinitions fieldDefinitions;

    public static class FieldDefinitions {

    }
  }

  public static class Row {

    public float pmax;
    public float pmin;
    public float ep;
    public String recordtimeslot;

    @Override
    public String toString() {
      return "Row{" +
              "pmax=" + pmax +
              ", pmin=" + pmin +
              ", ep=" + ep +
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
