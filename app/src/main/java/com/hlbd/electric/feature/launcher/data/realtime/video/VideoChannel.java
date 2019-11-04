package com.hlbd.electric.feature.launcher.data.realtime.video;

import java.util.List;

public class VideoChannel {

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
    public String channelNo;
    public String deviceSerial;

    @Override
    public String toString() {
      return "Row{" +
              "channelNo='" + channelNo + '\'' +
              ", deviceSerial='" + deviceSerial + '\'' +
              '}';
    }
  }

  @Override
  public String toString() {
    return "VideoChannel{" +
            "dataShape=" + dataShape +
            ", rows=" + rows +
            '}';
  }
}
