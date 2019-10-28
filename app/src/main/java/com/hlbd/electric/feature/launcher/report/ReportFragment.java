package com.hlbd.electric.feature.launcher.report;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.hlbd.electric.R;
import com.hlbd.electric.api.HttpApi;
import com.hlbd.electric.base.BaseFragment;
import com.hlbd.electric.feature.launcher.data.realtime.elec.list.ElecListActivity;
import com.hlbd.electric.feature.launcher.report.data.ReportDetailData;
import com.hlbd.electric.feature.launcher.report.data.ReportTypeData;
import com.hlbd.electric.util.LogUtil;
import com.hlbd.electric.util.TimeUtil;

public class ReportFragment extends BaseFragment implements ReportContract.View<ReportTypeData>, View.OnClickListener {

  private static final String TAG = "ReportFragment";
  private ListView mReportLv;
  private ReportAdapter mAdapter;
  private ReportTypeRequest request;
  private ReportContract.Presenter presenter;
  private TextView mTimeTv;
  private long chooseTime;
  private String chooseTimeStr;
  private View mTypeDayV;
  private View mTypeMonV;
  private ReportDetailRequest detailRequest;
  private static final String TYPE_DAY = "日报";
  private static final String TYPE_MON = "月报";
  private String mCurrentType = TYPE_DAY;

  private Intent mIntent;
  private String mDev = "";

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    if (mBaseView == null) {
      mBaseView = inflater.inflate(R.layout.fragment_report, container, false);
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
    chooseTime = System.currentTimeMillis();
    chooseTimeStr = TimeUtil.getDateTime(chooseTime);
    mTimeTv.setText(chooseTimeStr);
    request = new ReportTypeRequest();
    request.url = "Baoding_Overview_DataSupport/Services/GetReportType";

    if (detailRequest == null) {
      detailRequest = new ReportDetailRequest();
      detailRequest.url = "Baoding_Overview_DataSupport/Services/GetUserReportByDateAndTypeAndUser?"
              + "date=" + chooseTime + "&type=" + TYPE_DAY + "&user=" + HttpApi.getUserName();
    }
    presenter = new ReportPresenter(this, request, detailRequest);
  }

  private void initView() {
    mBaseView.findViewById(R.id.ll_type_day_report).setOnClickListener(this);
    mBaseView.findViewById(R.id.ll_type_mon_report).setOnClickListener(this);
    mTypeDayV = mBaseView.findViewById(R.id.v_type_day_report);
    mTypeMonV = mBaseView.findViewById(R.id.v_type_mon_report);
    mTimeTv = mBaseView.findViewById(R.id.tv_time_report);
    mBaseView.findViewById(R.id.iv_left_time_report).setOnClickListener(this);
    mBaseView.findViewById(R.id.iv_right_time_report).setOnClickListener(this);
    mTitleTv.setText("报告查看");
    mBackIv.setOnClickListener(this);
    mReportLv = mBaseView.findViewById(R.id.lv_report);
    mAdapter = new ReportAdapter();
    mReportLv.setAdapter(mAdapter);
    mReportLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

      }
    });
  }

  @Override
  public void updateReportType(ReportTypeData typeData) {

  }

  @Override
  public void updateDetailInfo(ReportDetailData data) {
    if (data == null || data.rows == null || data.rows.size() == 0) {
      mAdapter.clear();
      return;
    }
    mAdapter.addDataList(data.rows);
  }

  @Override
  public void setPersonal(ReportContract.Presenter p) {
    presenter = p;
  }


  @Override
  public void startTask() {
    //presenter.start();
    Activity activity = getActivity();
    if (mIntent == null && activity != null) {
      mIntent = new Intent(activity, ElecListActivity.class);
      mIntent.putExtra(ElecListActivity.KEY_EXTRA_IN_TITLE, "选择报告项");
    }
    if (mIntent != null) {
      startActivityForResult(mIntent, ElecListActivity.REQUEST_REPOTT_CODE);
    }
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.iv_left_time_report:
        updateTime(-1);
        break;
      case R.id.iv_right_time_report:
        updateTime(1);
        break;
      case R.id.ll_type_day_report:
        switchType(TYPE_DAY);
        break;
      case R.id.ll_type_mon_report:
        switchType(TYPE_MON);
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
      default:
        break;
    }
    chooseTime += diff;
    LogUtil.d(TAG, "updateTime() chooseTime=" + chooseTime);
    chooseTimeStr = TimeUtil.getDateTime(chooseTime);
    mTimeTv.setText(chooseTimeStr);
    loadDetailInfo();
  }

  private void switchType(String type) {
    mCurrentType = type;
    LogUtil.d(TAG, "switchType() type=" + type);
    switch (type) {
      case TYPE_DAY:
        mTypeDayV.setVisibility(View.VISIBLE);
        mTypeMonV.setVisibility(View.INVISIBLE);
        break;
      case TYPE_MON:
        mTypeDayV.setVisibility(View.INVISIBLE);
        mTypeMonV.setVisibility(View.VISIBLE);
        break;
      default:
        break;
    }
    loadDetailInfo();
  }

  private void loadDetailInfo() {
    detailRequest.url = "Baoding_Overview_DataSupport/Services/GetUserReportByDateAndTypeAndUser?"
            + "date=" + chooseTime
            + "&type=" + mCurrentType
            + "&user=" + HttpApi.getUserName()
            + "&dev=" + mDev;
    presenter.loadDetailInfo();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == ElecListActivity.REQUEST_REPOTT_CODE && resultCode == ElecListActivity.RESULT_CODE) {
      String value = data.getStringExtra(ElecListActivity.KEY_EXTRA_OUT2);
      if (value == null || TextUtils.isEmpty(value)) {
        return;
      }
      mDev = value;
      loadDetailInfo();
    }

  }
}
