package com.hlbd.electric.feature.launcher.mime.main.data;

import java.util.List;

public class MimeInfoData {

  private static final String TAG = "MimeInfoData";

  public DataShape dataShape;

  public List<Row> rows;

  public static class DataShape {

    public FieldDefinitions fieldDefinitions;

    public static class FieldDefinitions {

    }
  }

  public static class Row {

    public int id;
    public String username;
    public String passwordstr;
    public String fullname;
    public String phonenumber;
    public String department;
    public String city;
    public String address;
    public String authority;


    @Override
    public String toString() {
      return "Row{" +
              "id=" + id +
              ", username='" + username + '\'' +
              ", passwordstr='" + passwordstr + '\'' +
              ", fullname='" + fullname + '\'' +
              ", phonenumber='" + phonenumber + '\'' +
              ", department='" + department + '\'' +
              ", city='" + city + '\'' +
              ", address='" + address + '\'' +
              ", authority='" + authority + '\'' +
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
