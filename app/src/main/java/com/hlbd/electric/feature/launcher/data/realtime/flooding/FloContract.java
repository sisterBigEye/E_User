package com.hlbd.electric.feature.launcher.data.realtime.flooding;


import com.hlbd.electric.base.BasePresenter;
import com.hlbd.electric.base.BaseView;
import com.hlbd.electric.feature.launcher.data.realtime.flooding.data.FloodingData;

class FloContract {

  interface Presenter extends BasePresenter {

    void loadFloodingData();

    void loadFloodingDetail(String url);

  }

  interface View<T> extends BaseView<FloContract.Presenter> {

    void updateFloodingData(T t);

    void updateFloodingDetail(FloodingData data);

  }
}
