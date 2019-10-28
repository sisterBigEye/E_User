package com.hlbd.electric.feature.launcher.order;

import com.hlbd.electric.api.Callback;
import com.hlbd.electric.api.ITask;
import com.hlbd.electric.feature.launcher.order.data.OrderData;
import com.hlbd.electric.feature.launcher.order.data.OrderDetailData;
import com.hlbd.electric.task.RequestDataTask;
import com.hlbd.electric.util.LogUtil;

public class OrderPresenter implements OrderContract.Presenter {
  private static final String TAG = "EventMainPresenter";
  private OrderContract.View<OrderData> view;
  private OrderRequest orderRequest;
  private ITask<OrderData> task;

  OrderPresenter(OrderContract.View<OrderData> view, OrderRequest orderRequest) {
    this.view = view;
    this.orderRequest = orderRequest;
    this.view.setPersonal(this);
  }

  @Override
  public void start() {
    loadOrderData();
  }

  @Override
  public void loadOrderData() {
    if (view == null || orderRequest == null) {
      LogUtil.e(TAG, "loadOrderData() --- view is null");
      return;
    }
    if (task == null) {
      task = new RequestDataTask<>(OrderData.class);
    }
    LogUtil.d(TAG, "loadOrderData() --- map = " + orderRequest.toString());
    task.startTask(orderRequest, new Callback<OrderData>() {

      @Override
      public void result(OrderData data) {
        view.updateOrderData(data);
      }
    });
  }

  @Override
  public void loadDetailInfo(OrderDetailRequest request) {
    if (task == null) {
      task = new RequestDataTask<>(OrderData.class);
    }
    LogUtil.d(TAG, "loadDetailInfo() --- map = " + request.toString());
    new RequestDataTask<>
            (OrderDetailData.class).startTask(request, new Callback<OrderDetailData>() {
      @Override
      public void result(OrderDetailData orderDetailData) {
        view.updateDetailInfo(orderDetailData);
      }
    });
  }
}
