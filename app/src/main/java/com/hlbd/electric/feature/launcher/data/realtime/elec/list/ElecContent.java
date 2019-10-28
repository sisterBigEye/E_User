package com.hlbd.electric.feature.launcher.data.realtime.elec.list;

import com.hlbd.electric.common.dialog.IContent;

public class ElecContent implements IContent {

  private String description;

  private String value;

  public ElecContent(String description, String value) {
    this.description = description;
    this.value = value;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public String getValue() {
    return value;
  }
}
