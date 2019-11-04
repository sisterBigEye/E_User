package com.hlbd.electric.feature.launcher.data.realtime.video.list;

import java.util.List;

public class VideoListInfo {

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
    public String cameratype;
    public String specificname;
    public String username;
    public String videourl;

    @Override
    public String toString() {
      return "Row{" +
              "cameratype='" + cameratype + '\'' +
              ", specificname='" + specificname + '\'' +
              ", username='" + username + '\'' +
              ", videourl='" + videourl + '\'' +
              '}';
    }
  }

  @Override
  public String toString() {
    return "VideoListInfo{" +
            "dataShape=" + dataShape +
            ", rows=" + rows +
            '}';
  }
}
