package com.hlbd.electric.feature.launcher.event.main;

import com.google.gson.annotations.SerializedName;
import com.hlbd.electric.base.BaseResult;

public class EventMainCommitRequest extends BaseResult {

  @SerializedName("AlertName")
  public String alertName;

  @SerializedName("currentUser")
  public String currentUser;

  @SerializedName("AlertSourceType")
  public String alertSourceType;

  @SerializedName("DescriptionStr")
  public String descriptionStr;

  @SerializedName("AlertDescription")
  public String alertDescription;
}
