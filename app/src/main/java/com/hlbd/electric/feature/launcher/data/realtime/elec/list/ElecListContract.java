package com.hlbd.electric.feature.launcher.data.realtime.elec.list;


import com.hlbd.electric.base.BasePresenter;
import com.hlbd.electric.base.BaseView;
import com.hlbd.electric.feature.launcher.data.realtime.elec.data.ElectDataIA;
import com.hlbd.electric.feature.launcher.data.realtime.elec.data.ElectDataIB;
import com.hlbd.electric.feature.launcher.data.realtime.elec.data.ElectDataIC;
import com.hlbd.electric.feature.launcher.data.realtime.elec.data.ElectDataP;
import com.hlbd.electric.feature.launcher.data.realtime.elec.data.ElectDataPF;
import com.hlbd.electric.feature.launcher.data.realtime.elec.data.ElectDataQ;
import com.hlbd.electric.feature.launcher.data.realtime.elec.data.ElectDataVA;
import com.hlbd.electric.feature.launcher.data.realtime.elec.data.ElectDataVB;
import com.hlbd.electric.feature.launcher.data.realtime.elec.data.ElectDataVC;

class ElecListContract {

  interface Presenter extends BasePresenter {

    void loadElectricData();

  }

  interface View<T> extends BaseView<Presenter> {

    void updateElectricData(T t);


  }

}
