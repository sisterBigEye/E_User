package com.hlbd.electric.feature.launcher.report;

import com.hlbd.electric.api.Callback;
import com.hlbd.electric.api.ITask;
import com.hlbd.electric.feature.launcher.data.realtime.common.CommonRequest;
import com.hlbd.electric.feature.launcher.report.data.ReportDetailData;
import com.hlbd.electric.feature.launcher.report.data.ReportTypeData;
import com.hlbd.electric.model.Information;
import com.hlbd.electric.task.RequestDataTask;
import com.hlbd.electric.util.LogUtil;

public class ReportPresenter implements ReportContract.Presenter {
  private static final String TAG = "ReportPresenter";
  private ReportContract.View<ReportTypeData> view;
  private ReportTypeRequest typeRequest;
  private ITask<ReportTypeData> task;
  private ReportDetailRequest detailRequest;

  private CommonRequest mElectricRequest;
  private ITask<Information> mElectricTask;

  ReportPresenter(ReportContract.View<ReportTypeData> view,
                  ReportTypeRequest request,
                  ReportDetailRequest detailRequest,
                  CommonRequest electricRequest) {
    this.view = view;
    this.typeRequest = request;
    this.detailRequest = detailRequest;
    mElectricRequest = electricRequest;
    this.view.setPersonal(this);
  }

  @Override
  public void start() {
    loadReportType();
    loadDetailInfo();
  }

  @Override
  public void loadReportType() {
    if (view == null || typeRequest == null) {
      LogUtil.e(TAG, "loadReportType() --- view is null");
      return;
    }
    if (task == null) {
      task = new RequestDataTask<>(ReportTypeData.class);
    }
    LogUtil.d(TAG, "loadReportType() --- map = " + typeRequest.toString());
    task.startTask(typeRequest, new Callback<ReportTypeData>() {

      @Override
      public void result(ReportTypeData data) {
        view.updateReportType(data);
      }
    });
  }

  @Override
  public void loadDetailInfo() {
    LogUtil.d(TAG, "loadDetailInfo() --- map = " + detailRequest.toString());
    new RequestDataTask<>
            (ReportDetailData.class).startTask(detailRequest, new Callback<ReportDetailData>() {
      @Override
      public void result(ReportDetailData data) {
        view.updateDetailInfo(data);
      }
    });
  }

  @Override
  public void loadElectricData() {
    if (view == null || mElectricRequest == null) {
      LogUtil.e(TAG, "loadElectricData() --- view is null");
      return;
    }

    if (mElectricTask == null) {
      mElectricTask = new RequestDataTask<>(Information.class);
    }
    LogUtil.d(TAG, "loadElectricData() --- ElectricRequest = " + mElectricRequest.toString());
    mElectricTask.startTask(mElectricRequest, new Callback<Information>() {

      @Override
      public void result(Information information) {
        view.updateElectricData(information);
      }
    });
  }
}
