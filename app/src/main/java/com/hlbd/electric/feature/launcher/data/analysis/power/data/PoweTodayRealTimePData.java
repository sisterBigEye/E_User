package com.hlbd.electric.feature.launcher.data.analysis.power.data;

import java.util.List;

public class PoweTodayRealTimePData {

  private static final String TAG = "PoweTodayRealTimePData";

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
    public float imax;
    public float imin;
    public float umax;
    public float umin;
    public float pmax;
    public float pmin;
    public float qmax;
    public float qmin;
    public float pfmax;
    public float pfmin;
    public float p;
    public float ep;
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
              ", p=" + p +
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
