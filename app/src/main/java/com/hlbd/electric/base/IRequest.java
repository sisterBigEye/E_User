package com.hlbd.electric.base;

import android.support.annotation.IntDef;

import java.util.Map;

public interface IRequest {

  String getUrl();

  @PostBodyType
  int getContentType();

  Map<String, String> getParamMap();

  int JSON = 0;
  int EMPTY = 1;
  int DEFAULT = 3;

  @IntDef({JSON, EMPTY, DEFAULT})
  public @interface PostBodyType {
  }

}
