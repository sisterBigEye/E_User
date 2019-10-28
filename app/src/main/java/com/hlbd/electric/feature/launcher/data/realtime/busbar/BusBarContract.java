package com.hlbd.electric.feature.launcher.data.realtime.busbar;


import com.hlbd.electric.base.BasePresenter;
import com.hlbd.electric.base.BaseView;
import com.hlbd.electric.feature.launcher.data.realtime.busbar.data.BusBarData;

class BusBarContract {

  interface Presenter extends BasePresenter {

    void loadBusBarData();

    void loadBusBarDetail(String url);

  }

  interface View<T> extends BaseView<BusBarContract.Presenter> {

    void updateBusBarData(T t);

    void updateBusBarTemp(BusBarData data);

  }

}
