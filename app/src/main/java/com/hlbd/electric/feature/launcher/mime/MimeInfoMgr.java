package com.hlbd.electric.feature.launcher.mime;

import android.graphics.Bitmap;

public class MimeInfoMgr {

  private static volatile MimeInfoMgr sInstance;

  private MimeInfoMgr() {

  }

  public static MimeInfoMgr getsInstance() {
    if (sInstance == null) {
      synchronized (MimeInfoMgr.class) {
        if (sInstance == null) {
          sInstance = new MimeInfoMgr();
        }
      }
    }

    return sInstance;
  }

  public Bitmap userAvatarBmp;

  public String eventType;

  public String userName;

  public String phoneNumber;

  public String city;

  public String address;

  public int id;

  public String passwordStr;

  public String fullName;

  public String department;

  public String authority;

}
