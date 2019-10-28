package com.hlbd.electric.feature.launcher.data.realtime.elec;


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

class ElecContract {

  interface Presenter extends BasePresenter {

    void loadElectricData();

    void loadElectricDetail(String url);

  }

  interface View<T> extends BaseView<Presenter> {

    void updateElectricData(T t);

    void updateElectricIA(ElectDataIA data);

    void updateElectricIB(ElectDataIB data);

    void updateElectricIC(ElectDataIC data);

    void updateElectricVA(ElectDataVA data);

    void updateElectricVB(ElectDataVB data);

    void updateElectricVC(ElectDataVC data);

    void updateElectricP(ElectDataP data);

    void updateElectricQ(ElectDataQ data);

    void updateElectricPF(ElectDataPF data);

  }

}
