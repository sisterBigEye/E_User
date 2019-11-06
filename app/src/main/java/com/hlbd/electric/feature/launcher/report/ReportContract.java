package com.hlbd.electric.feature.launcher.report;


import com.hlbd.electric.base.BasePresenter;
import com.hlbd.electric.base.BaseView;
import com.hlbd.electric.feature.launcher.report.data.ReportDetailData;
import com.hlbd.electric.model.Information;

class ReportContract {

  interface Presenter extends BasePresenter {

    void loadReportType();

    void loadDetailInfo();

    void loadElectricData();

  }

  interface View<T> extends BaseView<Presenter> {

    void updateReportType(T t);

    void updateDetailInfo(ReportDetailData data);

    void updateElectricData(Information info);

  }

}
