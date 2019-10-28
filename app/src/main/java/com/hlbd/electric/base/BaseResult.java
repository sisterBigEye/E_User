package com.hlbd.electric.base;

import com.google.gson.annotations.Expose;
import com.hlbd.electric.api.HttpApi;

import java.util.HashMap;
import java.util.Map;

public class BaseResult implements IRequest {


  @Expose(serialize = false, deserialize = false)
  public transient String url;

  public transient String fistUrl;

  public transient String secondUrl;

  private transient HashMap<String, String> mParamMap = new HashMap<>();

  @Override
  public String getUrl() {
    if (fistUrl != null && secondUrl != null) {
      return fistUrl + HttpApi.getUserName() + secondUrl;
    }
    return url;
  }

  @Override
  public int getContentType() {
    return IRequest.JSON;
  }

  @Override
  public Map<String, String> getParamMap() {
    return mParamMap;
  }

  @Override
  public String toString() {
    return "BaseResult{" +
            "url='" + getUrl() + '\'' +
            '}';
  }
}
