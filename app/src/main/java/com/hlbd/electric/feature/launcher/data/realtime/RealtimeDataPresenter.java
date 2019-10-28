package com.hlbd.electric.feature.launcher.data.realtime;

import android.graphics.Bitmap;

import com.hlbd.electric.api.Callback;
import com.hlbd.electric.api.ITask;
import com.hlbd.electric.feature.BmpRequest;
import com.hlbd.electric.task.BitmapTask;

public class RealtimeDataPresenter implements RealtimeDataContract.Presenter {
  private static final String TAG = "RealtimeDataPresenter";
  private RealtimeDataContract.View view;
  private BmpRequest bmpRequest;
  private ITask<Bitmap> bmpTask;

  public RealtimeDataPresenter(RealtimeDataContract.View view, BmpRequest bmpRequest) {
    this.view = view;
    this.bmpRequest = bmpRequest;
    this.view.setPersonal(this);
  }

  @Override
  public void loadBitmap() {
    if (bmpTask == null) {
      bmpTask = new BitmapTask();
    }
    bmpTask.startTask(bmpRequest, new Callback<Bitmap>() {
      @Override
      public void result(Bitmap bitmap) {
        view.showBitmap(bitmap);
      }

    });
  }

  @Override
  public void start() {
    loadBitmap();
  }

}
