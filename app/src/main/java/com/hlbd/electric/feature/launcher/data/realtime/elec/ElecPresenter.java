package com.hlbd.electric.feature.launcher.data.realtime.elec;

import com.hlbd.electric.api.Callback;
import com.hlbd.electric.api.ITask;
import com.hlbd.electric.feature.launcher.data.realtime.common.CommonRequest;
import com.hlbd.electric.model.Information;
import com.hlbd.electric.feature.launcher.data.realtime.elec.data.ElectDataIA;
import com.hlbd.electric.feature.launcher.data.realtime.elec.data.ElectDataIB;
import com.hlbd.electric.feature.launcher.data.realtime.elec.data.ElectDataIC;
import com.hlbd.electric.feature.launcher.data.realtime.elec.data.ElectDataP;
import com.hlbd.electric.feature.launcher.data.realtime.elec.data.ElectDataPF;
import com.hlbd.electric.feature.launcher.data.realtime.elec.data.ElectDataQ;
import com.hlbd.electric.feature.launcher.data.realtime.elec.data.ElectDataVA;
import com.hlbd.electric.feature.launcher.data.realtime.elec.data.ElectDataVB;
import com.hlbd.electric.feature.launcher.data.realtime.elec.data.ElectDataVC;
import com.hlbd.electric.task.RequestDataTask;
import com.hlbd.electric.util.LogUtil;

public class ElecPresenter implements ElecContract.Presenter {
  private static final String TAG = "ElecPresenter";
  private ElecContract.View<Information> view;
  private CommonRequest mElectricRequest;

  private ITask<Information> mElectricTask;
  private ITask<Information> mEnvironmentTask;
  private ITask<Information> mBusBarTask;
  private ITask<Information> mFloodingTask;

  ElecPresenter(ElecContract.View<Information> view,
                CommonRequest electricRequest) {
    this.view = view;
    this.mElectricRequest = electricRequest;
    this.view.setPersonal(this);
  }

  @Override
  public void start() {
    loadElectricData();
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

  @Override
  public void loadElectricDetail(String url) {
    String iA = url + "/Properties/CurrentA";
    new RequestDataTask<>(ElectDataIA.class).startTask(iA, new Callback<ElectDataIA>() {
      @Override
      public void result(ElectDataIA info) {
        view.updateElectricIA(info);
      }
    });

    String iB = url + "/Properties/CurrentB";
    new RequestDataTask<>(ElectDataIB.class).startTask(iB, new Callback<ElectDataIB>() {
      @Override
      public void result(ElectDataIB info) {
        view.updateElectricIB(info);
      }
    });

    String iC = url + "/Properties/CurrentC";
    new RequestDataTask<>(ElectDataIC.class).startTask(iC, new Callback<ElectDataIC>() {
      @Override
      public void result(ElectDataIC info) {
        view.updateElectricIC(info);
      }
    });

    String vA = url + "/Properties/VoltageA";
    new RequestDataTask<>(ElectDataVA.class).startTask(vA, new Callback<ElectDataVA>() {
      @Override
      public void result(ElectDataVA info) {
        view.updateElectricVA(info);
      }
    });

    String vB = url + "/Properties/VoltageB";
    new RequestDataTask<>(ElectDataVB.class).startTask(vB, new Callback<ElectDataVB>() {
      @Override
      public void result(ElectDataVB info) {
        view.updateElectricVB(info);
      }
    });

    String vC = url + "/Properties/VoltageC";
    new RequestDataTask<>(ElectDataVC.class).startTask(vC, new Callback<ElectDataVC>() {
      @Override
      public void result(ElectDataVC info) {
        view.updateElectricVC(info);
      }
    });

    String p = url + "/Properties/P";
    new RequestDataTask<>(ElectDataP.class).startTask(p, new Callback<ElectDataP>() {
      @Override
      public void result(ElectDataP info) {
        view.updateElectricP(info);
      }
    });

    String q = url + "/Properties/Q";
    new RequestDataTask<>(ElectDataQ.class).startTask(q, new Callback<ElectDataQ>() {
      @Override
      public void result(ElectDataQ info) {
        view.updateElectricQ(info);
      }
    });

    String pf = url + "/Properties/PF";
    new RequestDataTask<>(ElectDataPF.class).startTask(pf, new Callback<ElectDataPF>() {
      @Override
      public void result(ElectDataPF info) {
        view.updateElectricPF(info);
      }
    });
  }
}
