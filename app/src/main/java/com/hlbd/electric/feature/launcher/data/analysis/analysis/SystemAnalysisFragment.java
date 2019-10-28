package com.hlbd.electric.feature.launcher.data.analysis.analysis;

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
import com.hlbd.electric.feature.launcher.data.analysis.analysis.data.ElectricPInfo;
import com.hlbd.electric.feature.launcher.data.analysis.analysis.data.SystemData;
import com.hlbd.electric.feature.launcher.data.analysis.count.data.PowerCostInfo;
import com.hlbd.electric.util.LogUtil;
import com.hlbd.electric.util.TimeUtil;
import com.hlbd.electric.widget.CrossView;
import com.hlbd.electric.widget.HistogramView;

import java.util.ArrayList;
import java.util.List;

public class SystemAnalysisFragment extends BaseFragment implements SystemContract.View, View.OnClickListener {

  private static final String TAG = "SystemAnalysisFragment";

  private SystemDataRequest systemDataRequest;
  private SystemContract.Presenter presenter;
  private TextView mTimeTv;
  private long chooseTime;
  private String chooseTimeStr;
  private View mTypeDayV;
  private View mTypeMonV;
  private View mTypeYearV;
  private static final String TYPE_DAY = "日";
  private static final String TYPE_MON = "月";
  private static final String TYPE_YEAR = "年";
  private String mCurrentType = TYPE_DAY;
  private List<PowerCostInfo> mEPList;
  private List<ElectricPInfo> mElePList;
  private HistogramView mEPHv;
  private CrossView mPCroV;
  private TextView mEpSystemTv;
  private TextView mPSystemTv;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    if (mBaseView == null) {
      mBaseView = inflater.inflate(R.layout.fragment_system_analysis, container, false);
    }
    return mBaseView;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    initView();
    init();
  }

  private void init() {
    mEPList = new ArrayList<>();
    mElePList = new ArrayList<>();
    chooseTime = System.currentTimeMillis();
    chooseTimeStr = TimeUtil.getDateTime(chooseTime);
    mTimeTv.setText(chooseTimeStr);

    systemDataRequest = new SystemDataRequest();
    systemDataRequest.fistUrl = "Baoding_UserData_DataSupport.";
    systemDataRequest.secondUrl = "/Services/GetEPAndExtremePByDateAndDateType?";
    systemDataRequest.datetime = chooseTime;
    systemDataRequest.dateType = mCurrentType;

    presenter = new SystemPresenter(this, systemDataRequest);
  }

  private void initView() {
    mTitleTv.setText("系统分析");
    mBackIv.setVisibility(View.VISIBLE);
    mBackIv.setOnClickListener(this);
    mBaseView.findViewById(R.id.ll_type_day_system).setOnClickListener(this);
    mBaseView.findViewById(R.id.ll_type_mon_system).setOnClickListener(this);
    mBaseView.findViewById(R.id.ll_type_year_system).setOnClickListener(this);

    mBaseView.findViewById(R.id.iv_left_time_system).setOnClickListener(this);
    mBaseView.findViewById(R.id.iv_right_time_system).setOnClickListener(this);

    mTypeDayV = mBaseView.findViewById(R.id.v_type_day_system);
    mTypeMonV = mBaseView.findViewById(R.id.v_type_mon_system);
    mTypeYearV = mBaseView.findViewById(R.id.v_type_year_system);

    mTimeTv = mBaseView.findViewById(R.id.tv_time_system);

    mEPHv = mBaseView.findViewById(R.id.hv_ep_system);
    mPCroV = mBaseView.findViewById(R.id.cro_v_p_system);

    mEpSystemTv = mBaseView.findViewById(R.id.tv_ep_system);
    mPSystemTv = mBaseView.findViewById(R.id.tv_p_system);
  }


  @Override
  public void startTask() {
    presenter.start();
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.iv_back:
        if (mPage != null) {
          mPage.turnPage(DataFragment.PAGE_ANALYSIS_ROOT);
        }
        break;
      case R.id.iv_left_time_system:
        updateTime(-1);
        break;
      case R.id.iv_right_time_system:
        updateTime(1);
        break;
      case R.id.ll_type_day_system:
        switchType(TYPE_DAY);
        break;
      case R.id.ll_type_mon_system:
        switchType(TYPE_MON);
        break;
      case R.id.ll_type_year_system:
        switchType(TYPE_YEAR);
        break;
      default:
        break;
    }
  }

  private void updateTime(int multiple) {
    long diff = multiple * TimeUtil.ONE_DAY_MILLISECOND;
    LogUtil.d(TAG, "updateTime() reportType=" + mCurrentType + ", multiple=" + multiple + ", diff=" + diff);
    switch (mCurrentType) {
      case TYPE_MON:
        diff = diff * 30;
        break;
      case TYPE_YEAR:
        diff = diff * 365;
        break;
      default:
        break;
    }
    chooseTime += diff;
    LogUtil.d(TAG, "updateTime() chooseTime=" + chooseTime);
    chooseTimeStr = TimeUtil.getDateTime(chooseTime);
    mTimeTv.setText(chooseTimeStr);
    loadSystemInfo();
  }

  private void switchType(String type) {
    String p;
    String ep;
    mCurrentType = type;
    LogUtil.d(TAG, "switchType() type=" + type);
    mTypeDayV.setVisibility(View.INVISIBLE);
    mTypeMonV.setVisibility(View.INVISIBLE);
    mTypeYearV.setVisibility(View.INVISIBLE);
    switch (type) {
      case TYPE_YEAR:
        p = "负荷（月）";
        ep = "用电量（月）";
        mTypeYearV.setVisibility(View.VISIBLE);
        break;
      case TYPE_MON:
        p = "负荷（日）";
        ep = "用电量（日）";
        mTypeMonV.setVisibility(View.VISIBLE);
        break;
      default:
        p = "负荷（小时）";
        ep = "用电量（小时）";
        mTypeDayV.setVisibility(View.VISIBLE);
        break;
    }
    mPSystemTv.setText(p);
    mEpSystemTv.setText(ep);
    loadSystemInfo();
  }

  private void loadSystemInfo() {
    systemDataRequest.datetime = chooseTime;
    systemDataRequest.dateType = mCurrentType;
    presenter.start();
  }

  @Override
  public void updateSystemData(SystemData data) {
    LogUtil.d(TAG, "updateSystemData() data=" + data);
    mEPList.clear();
    mElePList.clear();
    if (data == null) {
      mEPHv.setPowerCoatList(null);
      return;
    }
    float pMax = 0;
    try {
      for (SystemData.Row row : data.rows) {
        PowerCostInfo info = new PowerCostInfo();
        info.lowest = row.ep;
        info.date = row.recordtimeslot;
        mEPList.add(info);
        if (row.pmax > pMax) {
          pMax = row.pmax;
        }
        if (row.pmin > pMax) {
          pMax = row.pmin;
        }
        mElePList.add(new ElectricPInfo(row.pmin, row.pmax, row.recordtimeslot));
      }

    } catch (Exception e) {
      LogUtil.e(TAG, "updateSystemData()", e);
    }
    mEPHv.setPowerCoatList(mEPList);
    mPCroV.setMax(pMax);
    mPCroV.setInfoList(mElePList);
  }

  @Override
  public void setPersonal(SystemContract.Presenter p) {

  }
}
