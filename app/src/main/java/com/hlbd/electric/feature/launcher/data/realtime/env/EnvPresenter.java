package com.hlbd.electric.feature.launcher.data.realtime.env;

import com.hlbd.electric.api.Callback;
import com.hlbd.electric.api.ITask;
import com.hlbd.electric.feature.launcher.data.realtime.common.CommonRequest;
import com.hlbd.electric.model.Information;
import com.hlbd.electric.feature.launcher.data.realtime.env.data.EnvDataIHumidity;
import com.hlbd.electric.feature.launcher.data.realtime.env.data.EnvDataITemp;
import com.hlbd.electric.task.RequestDataTask;
import com.hlbd.electric.util.LogUtil;

public class EnvPresenter implements EnvContract.Presenter {
  private static final String TAG = "EnvPresenter";
  private EnvContract.View<Information> view;
  private CommonRequest mEnvironmentRequest;
  private ITask<Information> mEnvironmentTask;

  EnvPresenter(EnvContract.View<Information> view,
               CommonRequest environmentRequest) {
    this.view = view;
    this.mEnvironmentRequest = environmentRequest;
    this.view.setPersonal(this);
  }

  @Override
  public void start() {
    loadEnvironmentData();
  }



  @Override
  public void loadEnvironmentData() {
    if (view == null || mEnvironmentRequest == null) {
      LogUtil.e(TAG, "loadEnvironmentData() --- view is null");
      return;
    }

    if (mEnvironmentTask == null) {
      mEnvironmentTask = new RequestDataTask<>(Information.class);
    }
    LogUtil.d(TAG, "loadEnvironmentData() --- environmentRequest = " + mEnvironmentRequest.toString());
    mEnvironmentTask.startTask(mEnvironmentRequest, new Callback<Information>() {

      @Override
      public void result(Information information) {
        view.updateEnvironmentData(information);
      }
    });
  }

  @Override
  public void loadEnvironmentDetail(String url) {
    String tempUrl = url + "/Properties/TempAmbient";
    new RequestDataTask<>(EnvDataITemp.class).startTask(tempUrl, new Callback<EnvDataITemp>() {
      @Override
      public void result(EnvDataITemp info) {
        view.updateEnvironmentTemp(info);
      }
    });

    String humidityUrl = url + "/Properties/HumidityAmbient";
    new RequestDataTask<>(EnvDataIHumidity.class).startTask(humidityUrl, new Callback<EnvDataIHumidity>() {
      @Override
      public void result(EnvDataIHumidity info) {
        view.updateEnvironmentHumidity(info);
      }
    });
  }
}
