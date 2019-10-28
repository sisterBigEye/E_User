package com.hlbd.electric.task;

import com.hlbd.electric.ElectricApplication;
import com.hlbd.electric.api.Callback;
import com.hlbd.electric.api.HttpApi;
import com.hlbd.electric.api.ITask;
import com.hlbd.electric.base.IRequest;
import com.hlbd.electric.util.LogUtil;
import com.hlbd.electric.util.ThreadUtil;

public class RequestDataTask<T> implements ITask<T> {

  private static final String TAG = "RequestTask";

  private Class<T> clazz;

  public RequestDataTask(Class<T> clazz) {
    this.clazz = clazz;
  }

  @Override
  public void startTask(final IRequest request, final Callback<T> c) {
    LogUtil.d(TAG, "startTask()");
    ThreadUtil.startRunnable(new Runnable() {
      @Override
      public void run() {
        T t = HttpApi.requestData(request, clazz);
        ElectricApplication.sHandler.postData(c, t);
      }
    });

  }

  @Override
  public void startTask(final String url, final Callback<T> c) {
    LogUtil.d(TAG, "startTask() url=" + url);
    ThreadUtil.startRunnable(new Runnable() {
      @Override
      public void run() {
        T t = HttpApi.requestData(url, clazz);
        ElectricApplication.sHandler.postData(c, t);
      }
    });
  }

}
