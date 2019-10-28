package com.hlbd.electric.feature.launcher.data.analysis.count;


import com.hlbd.electric.base.BasePresenter;
import com.hlbd.electric.base.BaseView;
import com.hlbd.electric.feature.launcher.data.analysis.count.data.CountCostData;
import com.hlbd.electric.feature.launcher.data.analysis.count.data.CountPowerData;

class CountContract {

  interface Presenter extends BasePresenter {

    void loadCostData();

    void loadPowerData();

  }

  interface View extends BaseView<Presenter> {

    void updateCountPowerData(CountPowerData data);

    void updateCountCostData(CountCostData data);

  }

}
