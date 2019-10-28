package com.hlbd.electric.feature.launcher.report.data;

import java.util.List;

public class ReportTypeData {
  private static final String TAG = "ReportTypeData";

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

    public String repairstatus;

    public String usersource;

    public String devicesource;

    public String repairsn;

    public long createtime;

    public String exceptiondesc;

    public String createperson;

    public int priority;

    public String sourceProperty;

    @Override
    public String toString() {
      return "Row{" +
              "id=" + id +
              ", repairstatus='" + repairstatus + '\'' +
              ", usersource='" + usersource + '\'' +
              ", devicesource='" + devicesource + '\'' +
              ", repairsn='" + repairsn + '\'' +
              ", createtime='" + createtime + '\'' +
              ", exceptiondesc='" + exceptiondesc + '\'' +
              ", createperson='" + createperson + '\'' +
              ", priority=" + priority +
              ", sourceProperty='" + sourceProperty + '\'' +
              '}';
    }
  }

  @Override
  public String toString() {
    return "OrderData{" +
            ", dataShape=" + dataShape +
            ", rows=" + rows +
            '}';
  }
}
