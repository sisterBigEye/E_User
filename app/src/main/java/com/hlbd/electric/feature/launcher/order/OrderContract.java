package com.hlbd.electric.feature.launcher.order;


import com.hlbd.electric.base.BasePresenter;
import com.hlbd.electric.base.BaseView;
import com.hlbd.electric.feature.launcher.order.data.OrderDetailData;

class OrderContract {

  interface Presenter extends BasePresenter {

    void loadOrderData();

    void loadDetailInfo(OrderDetailRequest request);

  }

  interface View<T> extends BaseView<Presenter> {

    void updateOrderData(T t);

    void updateDetailInfo(OrderDetailData data);

  }

}
