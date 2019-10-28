package com.hlbd.electric.task;


import android.graphics.Bitmap;

import com.hlbd.electric.ElectricApplication;
import com.hlbd.electric.api.Callback;
import com.hlbd.electric.api.HttpApi;
import com.hlbd.electric.api.ITask;
import com.hlbd.electric.base.IRequest;
import com.hlbd.electric.util.LogUtil;
import com.hlbd.electric.util.ThreadUtil;

public class BitmapTask implements ITask<Bitmap> {

  private static final String TAG = "BitmapTask";
  public static int DEFAULT_LENGTH = 400;

  @Override
  public void startTask(final IRequest request, final Callback<Bitmap> c) {
    ThreadUtil.startRunnable(new Runnable() {
      @Override
      public void run() {
        LogUtil.d(TAG, "startTask() --- loadBitmap begin");
        Bitmap bmp = HttpApi.requestBitmap(request);
        LogUtil.d(TAG, "startTask() --- loadBitmap end, bmp=" + bmp);
        ElectricApplication.sHandler.postData(c, bmp);
      }
    });
  }

  @Override
  public void startTask(String url, Callback<Bitmap> c) {
    //
  }

}
