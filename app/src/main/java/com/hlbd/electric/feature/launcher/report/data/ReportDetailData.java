package com.hlbd.electric.feature.launcher.report.data;

import java.util.List;

public class ReportDetailData {

  private static final String TAG = "OrderDetailData";

  public DataShape dataShape;

  public List<Row> rows;

  public static class DataShape {

    public FieldDefinitions fieldDefinitions;

    public static class FieldDefinitions {

    }
  }

  public static class Row {

    public int id;

    public String usersource;

    public String devicesource;

    public double imax;

    public double imin;

    public double umax;

    public double umin;

    public double pmax;

    public double pmin;

    public double qmax;

    public double qmin;

    public double pfmax;

    public double pfmin;

    public long recordtimeslot;

    @Override
    public String toString() {
      return "Row{" +
              "id=" + id +
              ", usersource='" + usersource + '\'' +
              ", devicesource='" + devicesource + '\'' +
              ", imax=" + imax +
              ", imin=" + imin +
              ", umax=" + umax +
              ", umin=" + umin +
              ", pmax=" + pmax +
              ", pmin=" + pmin +
              ", qmax=" + qmax +
              ", qmin=" + qmin +
              ", pfmax=" + pfmax +
              ", pfmin=" + pfmin +
              ", recordtimeslot=" + recordtimeslot +
              '}';
    }
  }

  @Override
  public String toString() {
    return "OrderDetailData{" +
            ", dataShape=" + dataShape +
            ", rows=" + rows +
            '}';
  }
}
