package com.hlbd.electric.feature.launcher.data.realtime;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TabInfo {

  public final LinearLayout root;

  public final ImageView iv;

  public final TextView tv;

  public final View line;

  public TabInfo(LinearLayout root, ImageView iv, TextView tv, View line) {
    this.root = root;
    this.iv = iv;
    this.tv = tv;
    this.line = line;
  }
}
