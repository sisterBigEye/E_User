package com.hlbd.electric.feature.launcher.data.analysis.count;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hlbd.electric.R;
import com.hlbd.electric.base.BaseFragment;
import com.hlbd.electric.feature.launcher.data.DataFragment;
import com.hlbd.electric.feature.launcher.data.analysis.count.data.CountCostData;
import com.hlbd.electric.feature.launcher.data.analysis.count.data.CountPowerData;
import com.hlbd.electric.feature.launcher.data.analysis.count.data.PowerCostInfo;
import com.hlbd.electric.util.LogUtil;
import com.hlbd.electric.util.TimeUtil;
import com.hlbd.electric.widget.HistogramView;

import java.util.ArrayList;
import java.util.List;

public class CountFragment extends BaseFragment implements CountContract.View, View.OnClickListener {

  private static final String TAG = "CountFragment";

  private static final int TYPE_POWER_DAY = 1;
  private static final int TYPE_POWER_MON = 2;

  private int mPowerType = TYPE_POWER_DAY;
  private long powerTime;
  private String powerTimeStr;

  private static final int TYPE_COST_DAY = 100;
  private static final int TYPE_COST_MON = 200;

  private int mCostType = TYPE_COST_DAY;
  private long costTime;
  private String costTimeStr;

  private View mPowerDayV;
  private View mPowerMonV;
  private TextView mPowerTimeTv;

  private View mCostDayV;
  private View mCostMonV;
  private TextView mCostTimeTv;

  private CountContract.Presenter mPresenter;

  private CountPowerRequest powerRequest;
  private CountCostRequest costRequest;

  private List<PowerCostInfo> powerList;
  private HistogramView mPowerHv;

  private List<PowerCostInfo> costList;
  private HistogramView mCostHv;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    if (mBaseView == null) {
      mBaseView = inflater.inflate(R.layout.fragment_count, container, false);
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
    mTitleTv.setText("电量分时统计");
    mBackIv.setVisibility(View.VISIBLE);
    mBackIv.setOnClickListener(this);

    mPowerDayV = mBaseView.findViewById(R.id.v_power_day_count);
    mPowerMonV = mBaseView.findViewById(R.id.v_power_mon_count);
    mPowerTimeTv = mBaseView.findViewById(R.id.tv_power_time_count);
    mBaseView.findViewById(R.id.iv_power_left_time_count).setOnClickListener(this);
    mBaseView.findViewById(R.id.iv_power_right_time_count).setOnClickListener(this);
    mBaseView.findViewById(R.id.ll_power_day_count).setOnClickListener(this);
    mBaseView.findViewById(R.id.ll_power_mon_count).setOnClickListener(this);

    mCostDayV = mBaseView.findViewById(R.id.v_cost_day_count);
    mCostMonV = mBaseView.findViewById(R.id.v_cost_mon_count);
    mCostTimeTv = mBaseView.findViewById(R.id.tv_cost_time_count);
    mBaseView.findViewById(R.id.iv_cost_left_time_count).setOnClickListener(this);
    mBaseView.findViewById(R.id.iv_cost_right_time_count).setOnClickListener(this);
    mBaseView.findViewById(R.id.ll_cost_day_count).setOnClickListener(this);
    mBaseView.findViewById(R.id.ll_cost_mon_count).setOnClickListener(this);

    powerTime = costTime = System.currentTimeMillis();
    powerTimeStr = costTimeStr = TimeUtil.getDateTime(powerTime);
    mPowerTimeTv.setText(powerTimeStr);
    mCostTimeTv.setText(costTimeStr);

    mPowerHv = mBaseView.findViewById(R.id.hv_power_count);
    mCostHv = mBaseView.findViewById(R.id.hv_cost_count);
  }

  private void init() {
    powerList = new ArrayList<>();
    powerRequest = new CountPowerRequest();
    powerRequest.fistUrl = "Baoding_TimeOfUserSupport.";
    powerRequest.secondUrl = "/Services/GetDailyTimeEP?";
    powerRequest.date = String.valueOf(powerTime);

    costList = new ArrayList<>();
    costRequest = new CountCostRequest();
    costRequest.fistUrl = "Baoding_TimeOfUserSupport.";
    costRequest.secondUrl = "/Services/GetDailyTimePrice";
    costRequest.date = String.valueOf(powerTime);
    mPresenter = new CountPresenter(this, powerRequest, costRequest);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.iv_back:
        if (mPage != null) {
          mPage.turnPage(DataFragment.PAGE_ANALYSIS_ROOT);
        }
        break;

      case R.id.iv_power_left_time_count:
        updatePowerTime(-1);
        break;
      case R.id.iv_power_right_time_count:
        updatePowerTime(1);
        break;
      case R.id.ll_power_day_count:
        switchPowerType(TYPE_POWER_DAY);
        break;
      case R.id.ll_power_mon_count:
        switchPowerType(TYPE_POWER_MON);
        break;

      case R.id.iv_cost_left_time_count:
        updateCostTime(-1);
        break;
      case R.id.iv_cost_right_time_count:
        updateCostTime(1);
        break;
      case R.id.ll_cost_day_count:
        switchCostType(TYPE_COST_DAY);
        break;
      case R.id.ll_cost_mon_count:
        switchCostType(TYPE_COST_MON);
        break;

      default:
        break;
    }
  }

  @Override
  public void startTask() {
    if (mPresenter != null) {
      mPresenter.start();
    }
  }


  @Override
  public void setPersonal(CountContract.Presenter p) {
    this.mPresenter = p;
  }

  private void updatePowerTime(int multiple) {
    long diff = multiple * TimeUtil.ONE_DAY_MILLISECOND;
    LogUtil.d(TAG, "updatePowerTime() PowerType=" + mPowerType + ", multiple=" + multiple + ", diff=" + diff);
    switch (mPowerType) {
      case TYPE_POWER_MON:
        diff = diff * 30;
        break;
      default:
        break;
    }
    powerTime += diff;
    LogUtil.d(TAG, "updatePowerTime() powerTime=" + powerTime);
    powerTimeStr = TimeUtil.getDateTime(powerTime);
    mPowerTimeTv.setText(powerTimeStr);
    loadPowerInfo();
  }

  private void switchPowerType(int type) {
    if (type == mPowerType) {
      return;
    }
    mPowerType = type;
    LogUtil.d(TAG, "switchPowerType() type=" + type);
    switch (type) {
      case TYPE_POWER_DAY:
        mPowerDayV.setVisibility(View.VISIBLE);
        mPowerMonV.setVisibility(View.INVISIBLE);
        break;
      case TYPE_POWER_MON:
        mPowerDayV.setVisibility(View.INVISIBLE);
        mPowerMonV.setVisibility(View.VISIBLE);
        break;
      default:
        break;
    }
    loadPowerInfo();
  }

  private void updateCostTime(int multiple) {
    long diff = multiple * TimeUtil.ONE_DAY_MILLISECOND;
    LogUtil.d(TAG, "updateCostTime() CostType=" + mCostType + ", multiple=" + multiple + ", diff=" + diff);
    switch (mCostType) {
      case TYPE_COST_MON:
        diff = diff * 30;
        break;
      default:
        break;
    }
    costTime += diff;
    LogUtil.d(TAG, "updateCostTime() costTime=" + costTime);
    costTimeStr = TimeUtil.getDateTime(costTime);
    mCostTimeTv.setText(costTimeStr);
    loadCostInfo();
  }

  private void switchCostType(int type) {
    if (type == mCostType) {
      return;
    }
    mCostType = type;
    LogUtil.d(TAG, "switchCostType() type=" + type);
    switch (type) {
      case TYPE_COST_DAY:
        mCostDayV.setVisibility(View.VISIBLE);
        mCostMonV.setVisibility(View.INVISIBLE);
        break;
      case TYPE_COST_MON:
        mCostDayV.setVisibility(View.INVISIBLE);
        mCostMonV.setVisibility(View.VISIBLE);
        break;
      default:
        break;
    }
    loadCostInfo();
  }

  private void loadCostInfo() {
    if (mPresenter != null) {
      String firstUrl = "";
      String secondUrl = "";
      switch (mCostType) {
        case TYPE_COST_MON:
          firstUrl = "Baoding_TimeOfUserSupport.";
          secondUrl = "/Services/GetMonthlyTimePrice?";
          break;
        default:
          firstUrl = "Baoding_TimeOfUserSupport.";
          secondUrl = "/Services/GetDailyTimePrice";
          break;
      }
      costRequest.fistUrl = firstUrl;
      costRequest.secondUrl = secondUrl;
      costRequest.date = String.valueOf(costTime);
      mPresenter.loadCostData();
    }
  }

  private void loadPowerInfo() {
    if (mPresenter != null) {
      String firstUrl = "";
      String secondUrl = "";
      switch (mPowerType) {
        case TYPE_POWER_MON:
          firstUrl = "Baoding_TimeOfUserSupport.";
          secondUrl = "/Services/GetMonthlyTimeEP?";
          break;
        default:
          firstUrl = "Baoding_TimeOfUserSupport.";
          secondUrl = "/Services/GetDailyTimeEP?";
          break;
      }
      powerRequest.fistUrl = firstUrl;
      powerRequest.secondUrl = secondUrl;
      powerRequest.date = String.valueOf(powerTime);
      mPresenter.loadPowerData();
    }
  }

  @Override
  public void updateCountPowerData(CountPowerData data) {
    LogUtil.d(TAG, "updateCountPowerData() CountPowerData=" + data);
    powerList.clear();
    if (data == null) {
      mPowerHv.setPowerCoatList(null);
      return;
    }
    if (mPowerHv != null) {
      try {
        mPowerHv.setLowestColor(data.color.lowest);
        mPowerHv.setSecondLowColor(data.color.secondLow);
        mPowerHv.setSecondHighestColor(data.color.secondHighest);
        mPowerHv.setHighestColor(data.color.highest);
        for (int i = 0; i < data.unitdata.highest.size(); i++) {
          CountPowerData.UnitData unitData = data.unitdata;
          PowerCostInfo info = new PowerCostInfo();
          info.lowest = unitData.lowest.get(i);
          info.secondLow = unitData.secondLow.get(i);
          info.secondHighest = unitData.secondHighest.get(i);
          info.highest = unitData.lowest.get(i);
          /*info.lowest = 10;
          info.secondLow = 20;
          info.secondHighest = 45;
          info.highest = 65;*/
          info.date = data.x.get(i);
          powerList.add(info);
        }
      } catch (Exception e) {
        LogUtil.e(TAG, "updateCountPowerData()", e);
      }
      mPowerHv.setPowerCoatList(powerList);
    }

  }

  @Override
  public void updateCountCostData(CountCostData data) {
    costList.clear();
    LogUtil.d(TAG, "updateCountCostData() CountCostData=" + data);
    if (data == null) {
      mCostHv.setPowerCoatList(null);
      return;
    }
    if (mCostHv != null) {
      try {
        mCostHv.setLowestColor(data.color.lowest);
        mCostHv.setSecondLowColor(data.color.secondLow);
        mCostHv.setSecondHighestColor(data.color.secondHighest);
        mCostHv.setHighestColor(data.color.highest);
        for (int i = 0; i < data.unitdata.highest.size(); i++) {
          CountCostData.UnitData unitData = data.unitdata;
          PowerCostInfo info = new PowerCostInfo();
          info.lowest = unitData.lowest.get(i);
          info.secondLow = unitData.secondLow.get(i);
          info.secondHighest = unitData.secondHighest.get(i);
          info.highest = unitData.lowest.get(i);
          /*info.lowest = 15;
          info.secondLow = 30;
          info.secondHighest = 70;
          info.highest = 75;*/
          info.date = data.x.get(i);
          costList.add(info);
        }
      } catch (Exception e) {
        LogUtil.e(TAG, "updateCountCostData()", e);
      }
      mCostHv.setPowerCoatList(costList);
    }
  }
}