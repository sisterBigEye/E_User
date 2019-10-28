package com.hlbd.electric.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

  private static DateFormat sDateTimeFormat = DateFormat.getDateTimeInstance();

  private static DateFormat sDateFormat = DateFormat.getDateInstance();

  private static DateFormat sTimeFormat = new SimpleDateFormat("HH:mm:ss");

  public static final long ONE_DAY_MILLISECOND = 1000 * 60 * 60 * 24;

  public static String getDateTime(long time) {
    if (time <= 0) {
      return "未知时间";
    }
    return sDateTimeFormat.format(new Date(time));
  }

  public static String getData(long time) {
    if (time <= 0) {
      return "未知时间";
    }
    return sDateFormat.format(new Date(time));
  }

  public static String getTime(long time) {
    if (time <= 0) {
      return "未知时间";
    }
    return sTimeFormat.format(new Date(time));
  }
}
