package com.hlbd.electric.feature.launcher.data.analysis.power;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.hlbd.electric.R;
import com.hlbd.electric.base.BaseFragment;
import com.hlbd.electric.feature.launcher.data.DataFragment;
import com.hlbd.electric.feature.launcher.data.analysis.analysis.data.ElectricPInfo;
import com.hlbd.electric.feature.launcher.data.analysis.count.data.PowerCostInfo;
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
import com.hlbd.electric.feature.launcher.data.analysis.power.request.PowerTodayRealTimePRequest;
import com.hlbd.electric.util.LogUtil;
import com.hlbd.electric.util.TimeUtil;
import com.hlbd.electric.widget.CrossView;
import com.hlbd.electric.widget.DashboardView;
import com.hlbd.electric.widget.HistogramView;
import com.hlbd.electric.widget.chart.ChartData;
import com.hlbd.electric.widget.chart.ChartView;

import java.util.ArrayList;
import java.util.List;

public class PowerFragment extends BaseFragment implements PowerContract.View, View.OnClickListener {

  private static final String TAG = "PowerFragment";

  private PowerContract.Presenter mPresenter;

  private Power30EleRequest m30EleRequest;
  private PowerCo2CoalRequest mCo2CoalRequest;
  private PowerLastMonthEPDivideRequest mLastMonthEPDivideRequest;
  private PowerLastMonthEPTotalRequest mLastMonthEPTotalRequest;
  private PowerMonthEPPriceRequest mMonthEPPriceRequest;
  private PowerMonthMaxPTrendRequest mMonthMaxPTrendRequest;
  private PowerTodayRealTimePRequest mTodayRealTimePRequest;
  private Power7DayTimeEPRequest m7DayTimeEPRequest;
  private PowerPMaxRequest mPMaxRequest;

  private ArrayList<ChartData> m30EleList;
  private List<PieEntry> mPieStrings = new ArrayList<>();
  private ArrayList<ChartData> mTodayRealtimePList;
  private List<PowerCostInfo> m7DayTimeEPList;

  private PieChart mPicChart;
  private ChartView m30EleCv;
  private TextView mCo2Tv;
  private TextView mCoalTv;
  //private TextView mLastMonEleCostTv;
  //private TextView mThisMonEleCostTv;
  //private CrossView mPMaxCrv;
  private ChartView mTodayRealtimePCv;
  private HistogramView m7DayTimeEPHv;
  private TextView mPMaxTv;
  private TextView mRecordTimeTv;
  private DashboardView mPMaxDv;

  private ArrayList<ElectricPInfo> mPMaxList;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    if (mBaseView == null) {
      mBaseView = inflater.inflate(R.layout.fragment_power, container, false);
    }
    return mBaseView;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    initView();
    init();
  }

  private void initView() {
    mTitleTv.setText("系统用能");
    mBackIv.setVisibility(View.VISIBLE);
    mBackIv.setOnClickListener(this);

    mPicChart = mBaseView.findViewById(R.id.pc_power_power);
    m30EleCv = mBaseView.findViewById(R.id.cv_30day_elec_power);

    mCo2Tv = mBaseView.findViewById(R.id.tv_co2_power);
    mCoalTv = mBaseView.findViewById(R.id.tv_coal_power);

    //mLastMonEleCostTv = mBaseView.findViewById(R.id.tv_last_mon_elec_cost_power);
    //mThisMonEleCostTv = mBaseView.findViewById(R.id.tv_this_mon_elec_cost_power);

    //mPMaxCrv = mBaseView.findViewById(R.id.crv_p_max_power);
    mTodayRealtimePCv = mBaseView.findViewById(R.id.cv_today_real_time_p_power);
    m7DayTimeEPHv = mBaseView.findViewById(R.id.hv_7day_time_ep_power);

    mPMaxTv = mBaseView.findViewById(R.id.tv_pmax_power);
    mRecordTimeTv = mBaseView.findViewById(R.id.tv_record_time_power);
    mPMaxDv = mBaseView.findViewById(R.id.dv_pmax_power);
  }

  private void init() {
    mPMaxList = new ArrayList<>();
    m30EleList = new ArrayList<>();
    mTodayRealtimePList = new ArrayList<>();
    m7DayTimeEPList = new ArrayList<>();

    m30EleRequest = new Power30EleRequest();
    m30EleRequest.fistUrl = "Baoding_UserData_DataSupport.";
    m30EleRequest.secondUrl = "/Services/GetRecent30DaysEPByUser";

    mCo2CoalRequest = new PowerCo2CoalRequest();
    mCo2CoalRequest.fistUrl = "Baoding_UserData_DataSupport.";
    mCo2CoalRequest.secondUrl = "/Services/getCO2AndCoalUsageByUser";

    mLastMonthEPDivideRequest = new PowerLastMonthEPDivideRequest();
    mLastMonthEPDivideRequest.fistUrl = "Baoding_UserData_DataSupport.";
    mLastMonthEPDivideRequest.secondUrl = "/Services/GetLastMonthEPDivide";

    mLastMonthEPTotalRequest = new PowerLastMonthEPTotalRequest();
    mLastMonthEPTotalRequest.fistUrl = "Baoding_UserData_DataSupport.";
    mLastMonthEPTotalRequest.secondUrl = "/Services/GetLastMonthEPTotal";

    mMonthEPPriceRequest = new PowerMonthEPPriceRequest();
    mMonthEPPriceRequest.fistUrl = "Baoding_UserData_DataSupport.";
    mMonthEPPriceRequest.secondUrl = "/Services/GetMonthEPPrice";

    mMonthMaxPTrendRequest = new PowerMonthMaxPTrendRequest();
    mMonthMaxPTrendRequest.fistUrl = "Baoding_UserData_DataSupport.";
    mMonthMaxPTrendRequest.secondUrl = "/Services/GetMonthMaxPTrend";

    mTodayRealTimePRequest = new PowerTodayRealTimePRequest();
    mTodayRealTimePRequest.fistUrl = "Baoding_UserData_DataSupport.";
    mTodayRealTimePRequest.secondUrl = "/Services/GetTodayRealTimeP";

    m7DayTimeEPRequest = new Power7DayTimeEPRequest();
    m7DayTimeEPRequest.fistUrl = "Baoding_UserData_DataSupport.";
    m7DayTimeEPRequest.secondUrl = "/Services/get7DayTimeEP";

    mPMaxRequest = new PowerPMaxRequest();
    mPMaxRequest.fistUrl = "Baoding_UserData_DataSupport.";
    mPMaxRequest.secondUrl = "/Services/GetRecent30DaysPMaxTimeAndPmaxByUser";

    mPresenter = new PowerPresenter(this, null,
            m30EleRequest,
            mCo2CoalRequest,
            mLastMonthEPDivideRequest,
            mLastMonthEPTotalRequest,
            mMonthEPPriceRequest,
            mMonthMaxPTrendRequest,
            mTodayRealTimePRequest,
            m7DayTimeEPRequest,
            mPMaxRequest);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.iv_back:
        if (mPage != null) {
          mPage.turnPage(DataFragment.PAGE_ANALYSIS_ROOT);
        }
        break;

      default:
        break;
    }
  }

  @Override
  public void setPersonal(PowerContract.Presenter p) {
    mPresenter = p;
  }

  @Override
  public void startTask() {
    if (mPresenter != null) {
      mPresenter.start();
    }
  }

  @Override
  public void updatePowerData(PowerData data) {

  }

  @Override
  public void update30DaysEleData(Power30EleData data) {
    LogUtil.d(TAG, "update30DaysEleData() --- data = " + data);
    if (m30EleCv == null) {
      return;
    }
    m30EleList.clear();
    if (data == null) {
      m30EleCv.setDataList(m30EleList);
      return;
    }
    float max = 0;
    try {
      for (Power30EleData.Row row : data.rows) {
        if (row == null) {
          return;
        }
        if (row.ep > max) {
          max = row.ep;
        }
        m30EleList.add(new ChartData(TimeUtil.getData(row.recordtimeslot), row.ep));
      }
    } catch (Exception e) {
      LogUtil.e(TAG, "update30DaysEleData() error", e);
    }
    if (max <= 0) {
      max = 100;
    }
    max = max / 4 + max;
    m30EleCv.setMax(max);
    LogUtil.d(TAG, "update30DaysEleData() --- m30EleList = " + m30EleList.toString());
    m30EleCv.setDataList(m30EleList);
  }

  @Override
  public void co2CoalResult(PowerCo2CoalData data) {
    if (data == null) {
      return;
    }
    try {
      for (PowerCo2CoalData.Row row : data.rows) {
        mCo2Tv.setText(row.co2 + "吨");
        mCoalTv.setText(row.coal + "吨");

      }
    } catch (Exception e) {
      LogUtil.e(TAG, "co2CoalResult() error", e);
    }
  }

  @Override
  public void lastMonthEPDivideResult(PowerLastMonthEPDividerData data) {
    mPieStrings.clear();
    if (data == null) {
      return;
    }
    try {
      for (PowerLastMonthEPDividerData.Row row : data.rows) {
        mPieStrings.add(new PieEntry(row.ep, row.type));
      }
    } catch (Exception e) {
      LogUtil.e(TAG, "lastMonthEPDivideResult() error", e);
    }

    PieDataSet dataSet = new PieDataSet(mPieStrings, "");

    ArrayList<Integer> colors = new ArrayList<Integer>();
    colors.add(Color.parseColor("#8dc913"));
    colors.add(Color.parseColor("#70deff"));
    colors.add(Color.parseColor("#15a7e8"));
    colors.add(Color.parseColor("#ffaf15"));
    dataSet.setColors(colors);

    PieData pieData = new PieData(dataSet);
    pieData.setDrawValues(true);

    mPicChart.setData(pieData);
    mPicChart.invalidate();
  }

  @Override
  public void lastMonthEPTotalResult(PowerLastMonthEPTotalData data) {
    if (data == null) {
      return;
    }
    String desc = null;
    try {
      for (PowerLastMonthEPTotalData.Row row : data.rows) {
        desc = row.result;
        if (desc != null) {
          break;
        }
      }
    } catch (Exception e) {
      LogUtil.e(TAG, "lastMonthEPTotalResult() error", e);
    }
    if (desc == null) {
      return;
    }
    Description description = new Description();
    description.setText(desc);
    description.setTextSize(18);
    mPicChart.setDescription(description);
    mPicChart.invalidate();
  }

  @Override
  public void monthEPPriceResult(PowerMonthEPPriceData data) {
    if (data == null) {
      return;
    }
    try {
      for (PowerMonthEPPriceData.Row row : data.rows) {
        //mLastMonEleCostTv.setText(row.lastMonth + "元");
        //mThisMonEleCostTv.setText(row.thisMonth + "元");

      }
    } catch (Exception e) {
      LogUtil.e(TAG, "monthEPPriceResult() error", e);
    }
  }

  @Override
  public void monthMaxPTrendResult(PowerMonthMaxPTrendData data) {
    LogUtil.d(TAG, "monthMaxPTrendResult() data=" + data);
    /*if (data == null) {
      return;
    }
    mPMaxList.clear();
    float max = 0;
    try {
      for (PowerMonthMaxPTrendData.Row row : data.rows) {
        if (row.PMaxThisMonth > max) {
          max = row.PMaxThisMonth;
        }
        if (row.PMaxLastMonth > max) {
          max = row.PMaxLastMonth;
        }
        mPMaxList.add(new ElectricPInfo(row.PMaxThisMonth, row.PMaxLastMonth));
      }

    } catch (Exception e) {
      LogUtil.e(TAG, "monthMaxPTrendResult()", e);
    }
    if (max <= 0) {
      max = 100;
    }
    max = max / 4 + max;
    mPMaxCrv.setMax(max);
    mPMaxCrv.setInfoList(mPMaxList);*/
  }

  @Override
  public void todayRealTimePResult(PoweTodayRealTimePData data) {
    LogUtil.d(TAG, "todayRealTimePResult() --- data = " + data);
    if (mTodayRealtimePCv == null) {
      return;
    }
    mTodayRealtimePList.clear();
    if (data == null) {
      mTodayRealtimePCv.setDataList(mTodayRealtimePList);
      return;
    }
    float max = 0;
    try {
      for (PoweTodayRealTimePData.Row row : data.rows) {
        if (row == null) {
          return;
        }
        if (row.ep > max) {
          max = row.ep;
        }
        mTodayRealtimePList.add(new ChartData(TimeUtil.getTime(row.recordtimeslot), row.ep));
      }
    } catch (Exception e) {
      LogUtil.e(TAG, "todayRealTimePResult() error", e);
    }
    if (max <= 0) {
      max = 100;
    }
    max = max / 4 + max;
    mTodayRealtimePCv.setMax(max);
    LogUtil.d(TAG, "todayRealTimePResult() --- TodayRealtimePList = " + mTodayRealtimePList.toString());
    mTodayRealtimePCv.setDataList(mTodayRealtimePList);
  }

  @Override
  public void d7DayTimeEPResult(Power7DayTimeEPData data) {
    m7DayTimeEPList.clear();
    LogUtil.d(TAG, "d7DayTimeEPResult() Power7DayTimeEPData=" + data);
    if (data == null) {
      m7DayTimeEPHv.setPowerCoatList(null);
      return;
    }
    if (m7DayTimeEPHv != null) {
      try {
        m7DayTimeEPHv.setLowestColor(data.color.lowest);
        m7DayTimeEPHv.setSecondLowColor(data.color.secondLow);
        m7DayTimeEPHv.setSecondHighestColor(data.color.secondHighest);
        m7DayTimeEPHv.setHighestColor(data.color.highest);
        for (int i = 0; i < data.unitdata.highest.size(); i++) {
          Power7DayTimeEPData.UnitData unitData = data.unitdata;
          PowerCostInfo info = new PowerCostInfo();
          info.lowest = unitData.lowest.get(i);
          info.secondLow = unitData.secondLow.get(i);
          info.secondHighest = unitData.secondHighest.get(i);
          info.highest = unitData.lowest.get(i);
          info.date = data.x.get(i);
          m7DayTimeEPList.add(info);
        }
      } catch (Exception e) {
        LogUtil.e(TAG, "d7DayTimeEPResult()", e);
      }
      m7DayTimeEPHv.setPowerCoatList(m7DayTimeEPList);
    }
  }

  @Override
  public void pMaxResult(PowerPMaxData data) {
    if (data == null) {
      return;
    }
    float pMax = 0;
    try {
      for (PowerPMaxData.Row row : data.rows) {
        pMax = row.pmax;
        mPMaxTv.setText(pMax + "KW");
        mRecordTimeTv.setText(TimeUtil.getDateTime(row.recordtimeslot));
        mPMaxDv.setRealTimeValue((int) pMax);

      }
    } catch (Exception e) {
      LogUtil.e(TAG, "pMaxResult() error", e);
    }
  }

}
