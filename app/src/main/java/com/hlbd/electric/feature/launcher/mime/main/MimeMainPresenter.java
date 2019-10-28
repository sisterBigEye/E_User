package com.hlbd.electric.feature.launcher.mime.main;

import android.graphics.Bitmap;

import com.hlbd.electric.api.Callback;
import com.hlbd.electric.api.ITask;
import com.hlbd.electric.feature.BmpRequest;
import com.hlbd.electric.feature.launcher.mime.main.data.MimeAlertQuantityData;
import com.hlbd.electric.feature.launcher.mime.main.data.MimeInfoData;
import com.hlbd.electric.feature.launcher.mime.main.data.MimeOrderData;
import com.hlbd.electric.feature.launcher.mime.main.request.MimeInfoRequest;
import com.hlbd.electric.feature.launcher.mime.main.request.MimeOrderRequest;
import com.hlbd.electric.feature.launcher.mime.main.request.MimeAlertQuantityRequest;
import com.hlbd.electric.task.BitmapTask;
import com.hlbd.electric.task.RequestDataTask;
import com.hlbd.electric.util.LogUtil;

public class MimeMainPresenter implements MimeMainContract.Presenter {
  private static final String TAG = "ReportPresenter";
  private MimeMainContract.View view;
  private MimeOrderRequest orderRequest;
  private MimeAlertQuantityRequest warrantyRequest;
  private MimeInfoRequest mimeInfoRequest;
  private BmpRequest bmpRequest;
  private ITask<MimeOrderData> orderTask;
  private ITask<MimeAlertQuantityData> warrantyTask;
  private ITask<MimeInfoData> mimeInfoTask;
  private ITask<Bitmap> bmpTask;

  public MimeMainPresenter(MimeMainContract.View view, MimeOrderRequest orderRequest,
                    MimeAlertQuantityRequest warrantyRequest,
                    MimeInfoRequest mimeInfoRequest,
                    BmpRequest bmpRequest) {
    this.view = view;
    this.orderRequest = orderRequest;
    this.warrantyRequest = warrantyRequest;
    this.mimeInfoRequest = mimeInfoRequest;
    this.bmpRequest = bmpRequest;

    this.view.setPersonal(this);
  }

  @Override
  public void start() {
    loadOrderTimes();
    loadWarrantyTimes();
    loadMimeInfo();
    loadBmp();
  }

  @Override
  public void loadOrderTimes() {
    if (view == null || orderRequest == null) {
      LogUtil.e(TAG, "loadOrderTimes() --- view is null");
      return;
    }
    if (orderTask == null) {
      orderTask = new RequestDataTask<>(MimeOrderData.class);
    }
    LogUtil.d(TAG, "loadOrderTimes() --- map = " + orderRequest.toString());
    orderTask.startTask(orderRequest, new Callback<MimeOrderData>() {

      @Override
      public void result(MimeOrderData data) {
        view.updateMimeOrderData(data);
      }
    });
  }

  @Override
  public void loadWarrantyTimes() {
    if (view == null || warrantyRequest == null) {
      LogUtil.e(TAG, "loadWarrantyTimes() --- view is null");
      return;
    }
    if (warrantyTask == null) {
      warrantyTask = new RequestDataTask<>(MimeAlertQuantityData.class);
    }
    LogUtil.d(TAG, "loadWarrantyTimes() --- map = " + warrantyRequest.toString());
    warrantyTask.startTask(warrantyRequest, new Callback<MimeAlertQuantityData>() {

      @Override
      public void result(MimeAlertQuantityData data) {
        view.updateAlertQuantityData(data);
      }
    });
  }

  @Override
  public void loadMimeInfo() {
    if (view == null || mimeInfoRequest == null) {
      LogUtil.e(TAG, "loadMimeInfo() --- view is null");
      return;
    }
    if (mimeInfoTask == null) {
      mimeInfoTask = new RequestDataTask<>(MimeInfoData.class);
    }
    LogUtil.d(TAG, "loadMimeInfo() --- map = " + mimeInfoRequest.toString());
    mimeInfoTask.startTask(mimeInfoRequest, new Callback<MimeInfoData>() {

      @Override
      public void result(MimeInfoData data) {
        view.updateMimeInfo(data);
      }
    });
  }

  @Override
  public void loadBmp() {
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
}
