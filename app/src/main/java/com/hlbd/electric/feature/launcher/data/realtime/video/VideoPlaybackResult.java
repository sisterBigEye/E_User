package com.hlbd.electric.feature.launcher.data.realtime.video;

import java.util.List;

public class VideoPlaybackResult {

  public DataShape dataShape;

  public List<Row> rows;

  public static class DataShape {
    public FieldDefinitions fieldDefinitions;

    public static class FieldDefinitions {

      public Videourl videourl;

      public Specificname specificname;

      public static class Videourl {

        public String name;
        public String description;
        public String baseType;
        public int ordinal;

      }

      public static class Specificname {
        public String name;
        public String description;
        public String baseType;
        public int ordinal;
      }

    }
  }

  public static class Row {
    public String result;

    @Override
    public String toString() {
      return "Row{" +
              "result='" + result + '\'' +
              '}';
    }
  }

  @Override
  public String toString() {
    return "VideoPlaybackResult{" +
            "dataShape=" + dataShape +
            ", rows=" + rows +
            '}';
  }
}
