package com.hlbd.electric.feature.launcher.data.realtime.elec.data;

import com.google.gson.annotations.SerializedName;
import com.hlbd.electric.R;
import com.hlbd.electric.feature.launcher.data.realtime.common.IData;
import com.hlbd.electric.util.LogUtil;

import java.util.List;

public class ElectDataVC implements IData {

  private static final String TAG = "ElectDataVC";

  public transient int resId = 0;

  public DataShape dataShape;

  public List<Row> rows;

  @Override
  public String getDesc() {
    try {
      return dataShape.fieldDefinitions.desc.description;
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
      try {
        return String.valueOf(row.value);
      } catch (Exception e) {
        LogUtil.e(TAG, "getValue() error", e);
      }
    }
    return null;
  }

  @Override
  public int getResId() {
    return resId;
  }

  public static class DataShape {

    public FieldDefinitions fieldDefinitions;

    public static class FieldDefinitions {


      @SerializedName(value = "VoltageC")
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

    @SerializedName(value = "VoltageC")
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
