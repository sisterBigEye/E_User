package com.hlbd.electric.feature.launcher.event.main;


import com.hlbd.electric.base.BasePresenter;
import com.hlbd.electric.base.BaseView;
import com.hlbd.electric.feature.launcher.event.main.data.EventMainCommitResponse;

class EventMainContract {

  interface Presenter extends BasePresenter {

    void loadEventData();

    void commitEvent(EventMainCommitRequest request);

  }

  interface View<T> extends BaseView<Presenter> {

    void updateEventData(T t);

    void commitResult(EventMainCommitResponse response);

  }

}
