package com.hlbd.electric.feature.launcher.data.realtime.video.list;

import java.util.List;

public class VideoInfo {

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
    public String videourl;

    public String specificname;

    @Override
    public String toString() {
      return "Row{" +
              "videourl='" + videourl + '\'' +
              ", specificname='" + specificname + '\'' +
              '}';
    }
  }

  @Override
  public String toString() {
    return "VideoInfo{" +
            "dataShape=" + dataShape +
            ", rows=" + rows +
            '}';
  }
}
