package com.hlbd.electric.feature.launcher.event.order;


import com.hlbd.electric.base.BasePresenter;
import com.hlbd.electric.base.BaseView;
import com.hlbd.electric.feature.launcher.event.order.data.EventOrderData;

class EventOrderContract {

  interface Presenter extends BasePresenter {

    void getAckAlert();

  }

  interface View extends BaseView<Presenter> {

    void ackAlertResult(EventOrderData data);

  }

}
