package com.hlbd.electric.feature;

import java.util.List;

public class BitmapInfo {

  public DataShape dataShape;

  public static class DataShape {
    public FieldDefinitions fieldDefinitions;

    public static class FieldDefinitions {

      public ResultInternal result;

      public static class ResultInternal {

        public String name;

        public String description;

        public String baseType;

        public int ordinal;
      }
    }
  }


  public List<Result> rows;

  public static class Result {
    public String result;
  }
}
