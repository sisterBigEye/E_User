package com.hlbd.electric.feature.login;

import java.util.List;

public class UserInfo {

  public DataShape dataShape;

  public List<Row> rows;

  public static class DataShape {

    public Result result;

    public static class Result {
      public String name;
      public String description;
      public String baseType;
      public int ordinal;
      public Aspects aspects;

      public static class Aspects {
        @Override
        public String toString() {
          return "Aspects{}";
        }
      }

      @Override
      public String toString() {
        return "Result{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", baseType='" + baseType + '\'' +
                ", ordinal=" + ordinal +
                ", aspects=" + aspects +
                '}';
      }
    }

    @Override
    public String toString() {
      return "DataShape{" +
              "result=" + result +
              '}';
    }
  }

  public static class Row {
    public boolean result;

    @Override
    public String toString() {
      return "Row{" +
              "result=" + result +
              '}';
    }
  }

  @Override
  public String toString() {
    return "UserInfo{" +
            "dataShape=" + dataShape +
            ", rows=" + rows +
            '}';
  }
}
