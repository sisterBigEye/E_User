package com.hlbd.electric.feature.launcher.data.analysis.power;

import com.hlbd.electric.api.Callback;
import com.hlbd.electric.api.ITask;
import com.hlbd.electric.feature.launcher.data.analysis.power.data.PoweTodayRealTimePData;
import com.hlbd.electric.feature.launcher.data.analysis.power.data.Power30EleData;
import com.hlbd.electric.feature.launcher.data.analysis.power.data.Power7DayTimeEPData;
import com.hlbd.electric.feature.launcher.data.analysis.power.data.PowerCo2CoalData;
import com.hlbd.electric.feature.launcher.data.analysis.power.data.PowerData;
import com.hlbd.electric.feature.launcher.data.analysis.power.data.PowerLastMonthEPDividerData;
import com.hlbd.electric.feature.launcher.data.analysis.power.data.PowerLastMonthEPTotalData;
import com.hlbd.electric.feature.launcher.data.analysis.power.data.PowerMonthEPPriceData;
import com.hlbd.electric.feature.launcher.data.analysis.power.data.PowerMonthMaxPTrendData;
import com.hlbd.electric.feature.launcher.data.analysis.power.data.PowerPMaxData;
import com.hlbd.electric.feature.launcher.data.analysis.power.request.Power30EleRequest;
import com.hlbd.electric.feature.launcher.data.analysis.power.request.Power7DayTimeEPRequest;
import com.hlbd.electric.feature.launcher.data.analysis.power.request.PowerCo2CoalRequest;
import com.hlbd.electric.feature.launcher.data.analysis.power.request.PowerLastMonthEPDivideRequest;
import com.hlbd.electric.feature.launcher.data.analysis.power.request.PowerLastMonthEPTotalRequest;
import com.hlbd.electric.feature.launcher.data.analysis.power.request.PowerMonthEPPriceRequest;
import com.hlbd.electric.feature.launcher.data.analysis.power.request.PowerMonthMaxPTrendRequest;
import com.hlbd.electric.feature.launcher.data.analysis.power.request.PowerPMaxRequest;
import com.hlbd.electric.feature.launcher.data.analysis.power.request.PowerRequest;
import com.hlbd.electric.feature.launcher.data.analysis.power.request.PowerTodayRealTimePRequest;
import com.hlbd.electric.task.RequestDataTask;
import com.hlbd.electric.util.LogUtil;

public class PowerPresenter implements PowerContract.Presenter {
  private static final String TAG = "PowerPresenter";
  private PowerContract.View view;

  private ITask<PowerData> task;
  private ITask<Power30EleData> m30EleTask;
  private ITask<PowerCo2CoalData> mCo2CoalTask;
  private ITask<PowerLastMonthEPDividerData> mLastMonthEPDividerTask;
  private ITask<PowerLastMonthEPTotalData> mLastMonthEPTotalTask;
  private ITask<PowerMonthEPPriceData> mMonthEPPriceTask;
  private ITask<PowerMonthMaxPTrendData> mMonthMaxPTrendTask;
  private ITask<PoweTodayRealTimePData> mTodayRealTimePTask;
  private ITask<Power7DayTimeEPData> m7DayTimeEPDataPTask;
  private ITask<PowerPMaxData> mPMaxTask;

  private PowerRequest request;
  private Power30EleRequest m30EleRequest;
  private PowerCo2CoalRequest mCo2CoalRequest;
  private PowerLastMonthEPDivideRequest mLastMonthEPDivideRequest;
  private PowerLastMonthEPTotalRequest mLastMonthEPTotalRequest;
  private PowerMonthEPPriceRequest mMonthEPPriceRequest;
  private PowerMonthMaxPTrendRequest mMonthMaxPTrendRequest;
  private PowerTodayRealTimePRequest mTodayRealTimePRequest;
  private Power7DayTimeEPRequest m7DayTimeEPRequest;
  private PowerPMaxRequest mPMaxRequest;

  PowerPresenter(PowerContract.View view, PowerRequest request,
                 Power30EleRequest d30EleRequest,
                 PowerCo2CoalRequest co2CoalRequest,
                 PowerLastMonthEPDivideRequest lastMonthEPDivideRequest,
                 PowerLastMonthEPTotalRequest lastMonthEPTotalRequest,
                 PowerMonthEPPriceRequest monthEPPriceRequest,
                 PowerMonthMaxPTrendRequest monthMaxPTrendRequest,
                 PowerTodayRealTimePRequest todayRealTimePRequest,
                 Power7DayTimeEPRequest d7DayTimeEPRequest,
                 PowerPMaxRequest pMaxRequest) {
    this.view = view;
    this.view.setPersonal(this);
    this.request = request;
    m30EleRequest = d30EleRequest;
    mCo2CoalRequest = co2CoalRequest;
    mLastMonthEPDivideRequest = lastMonthEPDivideRequest;
    mLastMonthEPTotalRequest = lastMonthEPTotalRequest;
    mMonthEPPriceRequest = monthEPPriceRequest;
    mMonthMaxPTrendRequest = monthMaxPTrendRequest;
    mTodayRealTimePRequest = todayRealTimePRequest;
    m7DayTimeEPRequest = d7DayTimeEPRequest;
    mPMaxRequest = pMaxRequest;
  }

  @Override
  public void start() {
    loadPowerData();
    load30DaysEleData();
    getCO2AndCoalUsageByUser();
    getLastMonthEPDivide();
    getLastMonthEPTotal();
    getMonthEPPrice();
    getMonthMaxPTrend();
    getTodayRealTimeP();
    get7DayTimeEP();
    getPMax();
  }


  @Override
  public void loadPowerData() {
    if (view == null || request == null) {
      LogUtil.e(TAG, "modifyPassword() --- view is null");
      return;
    }
    if (task == null) {
      task = new RequestDataTask<>(PowerData.class);
    }
    LogUtil.d(TAG, "modifyPassword() --- map = " + request.toString());
    task.startTask(request, new Callback<PowerData>() {

      @Override
      public void result(PowerData data) {
        view.updatePowerData(data);
      }
    });
  }

  @Override
  public void load30DaysEleData() {
    if (view == null || m30EleRequest == null) {
      LogUtil.e(TAG, "load30DaysEleData() --- view is null");
      return;
    }
    if (m30EleTask == null) {
      m30EleTask = new RequestDataTask<>(Power30EleData.class);
    }
    LogUtil.d(TAG, "load30DaysEleData() --- map = " + m30EleRequest.toString());
    m30EleTask.startTask(m30EleRequest, new Callback<Power30EleData>() {

      @Override
      public void result(Power30EleData data) {
        view.update30DaysEleData(data);
      }
    });
  }

  @Override
  public void getCO2AndCoalUsageByUser() {
    if (view == null || mCo2CoalRequest == null) {
      LogUtil.e(TAG, "getCO2AndCoalUsageByUser() --- view is null");
      return;
    }
    if (mCo2CoalTask == null) {
      mCo2CoalTask = new RequestDataTask<>(PowerCo2CoalData.class);
    }
    LogUtil.d(TAG, "getCO2AndCoalUsageByUser() --- map = " + mCo2CoalRequest.toString());
    mCo2CoalTask.startTask(mCo2CoalRequest, new Callback<PowerCo2CoalData>() {

      @Override
      public void result(PowerCo2CoalData data) {
        view.co2CoalResult(data);
      }
    });
  }

  @Override
  public void getLastMonthEPDivide() {
    if (view == null || mLastMonthEPDivideRequest == null) {
      LogUtil.e(TAG, "getLastMonthEPDivide() --- view is null");
      return;
    }
    if (mLastMonthEPDividerTask == null) {
      mLastMonthEPDividerTask = new RequestDataTask<>(PowerLastMonthEPDividerData.class);
    }
    LogUtil.d(TAG, "getLastMonthEPDivide() --- map = " + mLastMonthEPDivideRequest.toString());
    mLastMonthEPDividerTask.startTask(mLastMonthEPDivideRequest, new Callback<PowerLastMonthEPDividerData>() {

      @Override
      public void result(PowerLastMonthEPDividerData data) {
        view.lastMonthEPDivideResult(data);
      }
    });
  }

  @Override
  public void getLastMonthEPTotal() {
    if (view == null || mLastMonthEPTotalRequest == null) {
      LogUtil.e(TAG, "getLastMonthEPTotal() --- view is null");
      return;
    }
    if (mLastMonthEPTotalTask == null) {
      mLastMonthEPTotalTask = new RequestDataTask<>(PowerLastMonthEPTotalData.class);
    }
    LogUtil.d(TAG, "getLastMonthEPTotal() --- map = " + mLastMonthEPTotalRequest.toString());
    mLastMonthEPTotalTask.startTask(mLastMonthEPTotalRequest, new Callback<PowerLastMonthEPTotalData>() {

      @Override
      public void result(PowerLastMonthEPTotalData data) {
        view.lastMonthEPTotalResult(data);
      }
    });
  }

  @Override
  public void getMonthEPPrice() {
    if (view == null || mMonthEPPriceRequest == null) {
      LogUtil.e(TAG, "getMonthEPPrice() --- view is null");
      return;
    }
    if (mMonthEPPriceTask == null) {
      mMonthEPPriceTask = new RequestDataTask<>(PowerMonthEPPriceData.class);
    }
    LogUtil.d(TAG, "getMonthEPPrice() --- map = " + mMonthEPPriceRequest.toString());
    mMonthEPPriceTask.startTask(mMonthEPPriceRequest, new Callback<PowerMonthEPPriceData>() {

      @Override
      public void result(PowerMonthEPPriceData data) {
        view.monthEPPriceResult(data);
      }
    });
  }

  @Override
  public void getMonthMaxPTrend() {
    if (view == null || mMonthMaxPTrendRequest == null) {
      LogUtil.e(TAG, "getMonthEPPrice() --- view is null");
      return;
    }
    if (mMonthMaxPTrendTask == null) {
      mMonthMaxPTrendTask = new RequestDataTask<>(PowerMonthMaxPTrendData.class);
    }
    LogUtil.d(TAG, "getMonthEPPrice() --- map = " + mMonthMaxPTrendRequest.toString());
    mMonthMaxPTrendTask.startTask(mMonthMaxPTrendRequest, new Callback<PowerMonthMaxPTrendData>() {

      @Override
      public void result(PowerMonthMaxPTrendData data) {
        view.monthMaxPTrendResult(data);
      }
    });
  }

  @Override
  public void getTodayRealTimeP() {
    if (view == null || mTodayRealTimePRequest == null) {
      LogUtil.e(TAG, "getTodayRealTimeP() --- view is null");
      return;
    }
    if (mTodayRealTimePTask == null) {
      mTodayRealTimePTask = new RequestDataTask<>(PoweTodayRealTimePData.class);
    }
    LogUtil.d(TAG, "getTodayRealTimeP() --- map = " + mTodayRealTimePRequest.toString());
    mTodayRealTimePTask.startTask(mTodayRealTimePRequest, new Callback<PoweTodayRealTimePData>() {

      @Override
      public void result(PoweTodayRealTimePData data) {
        view.todayRealTimePResult(data);
      }
    });
  }

  @Override
  public void get7DayTimeEP() {
    if (view == null || m7DayTimeEPRequest == null) {
      LogUtil.e(TAG, "get7DayTimeEP() --- view is null");
      return;
    }
    if (m7DayTimeEPDataPTask == null) {
      m7DayTimeEPDataPTask = new RequestDataTask<>(Power7DayTimeEPData.class);
    }
    LogUtil.d(TAG, "get7DayTimeEP() --- map = " + m7DayTimeEPRequest.toString());
    m7DayTimeEPDataPTask.startTask(m7DayTimeEPRequest, new Callback<Power7DayTimeEPData>() {

      @Override
      public void result(Power7DayTimeEPData data) {
        view.d7DayTimeEPResult(data);
      }
    });
  }

  @Override
  public void getPMax() {
    if (view == null || mPMaxRequest == null) {
      LogUtil.e(TAG, "getPMax() --- view is null");
      return;
    }
    if (mPMaxTask == null) {
      mPMaxTask = new RequestDataTask<>(PowerPMaxData.class);
    }
    LogUtil.d(TAG, "getPMax() --- map = " + mPMaxRequest.toString());
    mPMaxTask.startTask(mPMaxRequest, new Callback<PowerPMaxData>() {

      @Override
      public void result(PowerPMaxData data) {
        view.pMaxResult(data);
      }
    });
  }

}
