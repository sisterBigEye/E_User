package com.hlbd.electric.feature.launcher.data.analysis.analysis;


import com.hlbd.electric.base.BasePresenter;
import com.hlbd.electric.base.BaseView;
import com.hlbd.electric.feature.launcher.data.analysis.analysis.data.SystemData;

class SystemContract {

  interface Presenter extends BasePresenter {

    void loadSystemData();

  }

  interface View extends BaseView<Presenter> {

    void updateSystemData(SystemData data);


  }

}
