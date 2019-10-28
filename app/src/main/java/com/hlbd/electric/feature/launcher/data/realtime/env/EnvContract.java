package com.hlbd.electric.feature.launcher.data.realtime.env;


import com.hlbd.electric.base.BasePresenter;
import com.hlbd.electric.base.BaseView;
import com.hlbd.electric.feature.launcher.data.realtime.env.data.EnvDataIHumidity;
import com.hlbd.electric.feature.launcher.data.realtime.env.data.EnvDataITemp;

class EnvContract {

  interface Presenter extends BasePresenter {

    void loadEnvironmentData();

    void loadEnvironmentDetail(String url);

  }

  interface View<T> extends BaseView<Presenter> {

    void updateEnvironmentData(T t);

    void updateEnvironmentTemp(EnvDataITemp data);

    void updateEnvironmentHumidity(EnvDataIHumidity data);

  }

}
