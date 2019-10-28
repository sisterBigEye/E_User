package com.hlbd.electric.feature.launcher.mime.modify.password;

import com.hlbd.electric.base.BaseResult;

public class ModifyPasswordRequest extends BaseResult {

  public String username;

  public String password;

  @Override
  public String toString() {
    return "ModifyPasswordRequest{" +
            "username='" + username + '\'' +
            ", url='" + url + '\'' +
            '}';
  }
}
