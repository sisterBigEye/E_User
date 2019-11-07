package com.hlbd.electric.feature.launcher.data.realtime.flooding.data;

import com.google.gson.annotations.SerializedName;
import com.hlbd.electric.R;
import com.hlbd.electric.feature.launcher.data.realtime.common.IData;
import com.hlbd.electric.util.LogUtil;

import java.util.List;

public class FloodingData implements IData {

  private static final String TAG = "FloodingData";

  public transient int resId = R.drawable.ic_flooding;

  public transient String title = "水浸状态";

  public DataShape dataShape;

  public List<Row> rows;

  @Override
  public String getDesc() {
    try {
      return title;
    } catch (Exception e) {
      LogUtil.e(TAG, "getDesc() error", e);
    }
    return null;
  }

  @Override
  public String getValue() {
    if (rows == null || rows.size() == 0) {
      return null;
    }
    for (Row row : rows) {
      switch ((int) row.value) {
        case 0:
          return "正常";
      }
    }
    return "浸水";
  }

  @Override
  public int getResId() {
    return resId;
  }

  public static class DataShape {

    public FieldDefinitions fieldDefinitions;

    public static class FieldDefinitions {


      @SerializedName(value = "WaterOutStatus")
      public Content desc;

      public static class Content {

        public String name;

        public String description;

        public String baseType;

        public int ordinal;

        public Aspects aspects;

        public static class Aspects {

          public boolean isReadOnly;

          public int dataChangeThreshold;

          public boolean isPersistent;

          public boolean isLogged;

          public String dataChangeType;

          public int cacheTime;

        }

        @Override
        public String toString() {
          return "Content{" +
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
        return "FieldDefinitions{" +
                "desc=" + desc +
                '}';
      }
    }
  }

  public static class Row {

    @SerializedName(value = "WaterOutStatus")
    public float value;

    @Override
    public String toString() {
      return "Row{" +
              "value=" + value +
              '}';
    }
  }

  @Override
  public String toString() {
    return "DetailInfo{" +
            "resId=" + resId +
            ", dataShape=" + dataShape +
            ", rows=" + rows +
            '}';
  }
}
