package com.hlbd.electric.feature.launcher.report;

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

import com.airsaid.pickerviewlibrary.OptionsPickerView;
import com.hlbd.electric.R;
import com.hlbd.electric.api.HttpApi;
import com.hlbd.electric.base.BaseFragment;
import com.hlbd.electric.feature.launcher.data.realtime.common.CommonRequest;
import com.hlbd.electric.feature.launcher.data.realtime.elec.list.ElecListActivity;
import com.hlbd.electric.feature.launcher.report.data.ReportDetailData;
import com.hlbd.electric.feature.launcher.report.data.ReportTypeData;
import com.hlbd.electric.model.Information;
import com.hlbd.electric.util.LogUtil;
import com.hlbd.electric.util.TimeUtil;
import com.hlbd.electric.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

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
  private TextView mItemTv;
  private ReportDetailRequest detailRequest;
  private static final String TYPE_DAY = "日报";
  private static final String TYPE_MON = "月报";
  private String mCurrentType = TYPE_DAY;

  private Intent mIntent;
  private String mDev = "";

  private boolean needShowPickerLater;

  private List<Information.Row> mCurrentRows;

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

    CommonRequest electricRequest = new CommonRequest();
    electricRequest.userTag = HttpApi.getUserName();
    electricRequest.url = "Baoding_Overview_DataSupport/Services/GetUsersElecDevice?";
    presenter = new ReportPresenter(this, request, detailRequest, electricRequest);
    presenter.loadElectricData();
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

    mItemTv = mBaseView.findViewById(R.id.tv_item_report);
    mItemTv.setOnClickListener(this);
  }

  @Override
  public void updateReportType(ReportTypeData typeData) {
    LogUtil.d(TAG, "updateReportType() ReportTypeData=" + typeData);
  }

  @Override
  public void updateDetailInfo(ReportDetailData data) {
    LogUtil.d(TAG, "updateDetailInfo() ReportDetailData=" + data);
    if (data == null || data.rows == null || data.rows.size() == 0) {
      mAdapter.clear();
      return;
    }
    LogUtil.d(TAG, "updateDetailInfo() size=" + data.rows.size());
    mAdapter.addDataList(data.rows);
  }

  @Override
  public void setPersonal(ReportContract.Presenter p) {
    presenter = p;
  }


  @Override
  public void startTask() {
    //presenter.start();
    /*Activity activity = getActivity();
    if (mIntent == null && activity != null) {
      mIntent = new Intent(activity, ElecListActivity.class);
      mIntent.putExtra(ElecListActivity.KEY_EXTRA_IN_TITLE, "选择报告项");
    }
    if (mIntent != null) {
      startActivityForResult(mIntent, ElecListActivity.REQUEST_REPOTT_CODE);
    }*/
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

      case R.id.tv_item_report:
        if (mCurrentRows == null || mCurrentRows.size() == 0) {
          needShowPickerLater = true;
          presenter.loadElectricData();
          return;
        }
        showPicker();
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

  @Override
  public void updateElectricData(Information information) {
    LogUtil.d(TAG, "updateElectricData() information=" + information);
    if (information == null || information.rows == null || information.rows.size() == 0) {
      ToastUtil.toast("获取数据失败，请稍后尝试");
      needShowPickerLater = false;
      return;
    }
    mCurrentRows = information.rows;
    if (needShowPickerLater) {
      needShowPickerLater = false;
      showPicker();
      return;
    }
    mDev = information.rows.get(0).description;
    mItemTv.setText(mDev);
    loadDetailInfo();

  }

  private void showPicker() {
    /*Activity activity = getActivity();
    LogUtil.d(TAG, "showPicker() activity=" + activity);
    if (activity == null) {
      return;
    }
    if (mIntent == null) {
      mIntent = new Intent(activity, ElecListActivity.class);
      mIntent.putExtra(ElecListActivity.KEY_EXTRA_IN_TITLE, "选择电力监测设备");
    }
    startActivityForResult(mIntent, ElecListActivity.REQUEST_ELEC_CODE);*/

    if (mCurrentRows == null || mCurrentRows.size() == 0) {
      return;
    }
    OptionsPickerView<String> mOptionsPickerView = new OptionsPickerView<>(getActivity());
    final ArrayList<String> list = new ArrayList<>();
    // 设置数据
    for (Information.Row row : mCurrentRows) {
      if (row != null) {
        list.add(row.description);
      }
    }
    mOptionsPickerView.setPicker(list);
    // 设置选项单位
    //mOptionsPickerView.setLabels("性");
    mOptionsPickerView.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
      @Override
      public void onOptionsSelect(int option1, int option2, int option3) {
        String value = list.get(option1);
        ToastUtil.toast("选中了 " + value);
        mItemTv.setText(value);
        mDev = value;
        loadDetailInfo();
      }
    });
    mOptionsPickerView.show();
  }
}
