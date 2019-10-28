package com.hlbd.electric.feature.launcher.event.history;


import com.hlbd.electric.base.BasePresenter;
import com.hlbd.electric.base.BaseView;
import com.hlbd.electric.feature.launcher.event.history.data.EventHistoryData;

class EventHistoryContract {

  interface Presenter extends BasePresenter {

    void getUserAllAlertHistory();

  }

  interface View extends BaseView<Presenter> {

    void userAllAlertHistoryResult(EventHistoryData data);

  }

}
