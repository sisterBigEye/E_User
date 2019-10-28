package com.hlbd.electric.feature.launcher.report;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hlbd.electric.R;
import com.hlbd.electric.feature.launcher.report.data.ReportDetailData;
import com.hlbd.electric.util.TimeUtil;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class ReportAdapter extends BaseAdapter {

  public List<ReportDetailData.Row> mInfoList;
  private DateFormat mDateFormat = DateFormat.getDateTimeInstance();

  public ReportAdapter() {
    mInfoList = new ArrayList<>();
  }

  public void updateData(List<ReportDetailData.Row> list) {
    mInfoList.clear();
    addDataList(list);
  }

  public void addDataList(List<ReportDetailData.Row> list) {
    mInfoList.clear();
    ;
    if (list == null || list.size() == 0) {
      return;
    }
    mInfoList.addAll(list);
    notifyDataSetChanged();
  }

  public void addData(ReportDetailData.Row info) {
    if (info != null) {
      mInfoList.add(info);
    }
  }

  @Override
  public int getCount() {
    return mInfoList.size();
  }

  @Override
  public ReportDetailData.Row getItem(int position) {
    return mInfoList.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    Holder holder;
    if (convertView == null) {
      convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_report, parent, false);
      holder = new Holder(convertView);
      convertView.setTag(holder);
    } else {
      holder = (Holder) convertView.getTag();
    }
    ReportDetailData.Row info = mInfoList.get(position);
    if (info != null) {
      holder.mTitleTv.setText(info.devicesource);
      holder.mDeviceNameTv.setText(info.devicesource);
      holder.mRecordTimeTv.setText(TimeUtil.getDateTime(info.recordtimeslot));

      holder.mMaxITv.setText(String.valueOf(info.imax));
      holder.mMinITv.setText(String.valueOf(info.imin));

      holder.mMaxVTv.setText(String.valueOf(info.umax));
      holder.mMinVTv.setText(String.valueOf(info.umin));

      holder.mMaxPTv.setText(String.valueOf(info.pmax));
      holder.mMinPTv.setText(String.valueOf(info.pmin));

      holder.mMaxQTv.setText(String.valueOf(info.qmax));
      holder.mMinQTv.setText(String.valueOf(info.qmin));

      holder.mMaxPFTv.setText(String.valueOf(info.pfmax));
      holder.mMinPFTv.setText(String.valueOf(info.pfmin));
    }

    return convertView;
  }

  private static class Holder {
    private TextView mTitleTv;
    private TextView mDeviceNameTv;
    private TextView mRecordTimeTv;

    private TextView mMaxITv;
    private TextView mMinITv;

    private TextView mMaxVTv;
    private TextView mMinVTv;

    private TextView mMaxPTv;
    private TextView mMinPTv;

    private TextView mMaxQTv;
    private TextView mMinQTv;

    private TextView mMaxPFTv;
    private TextView mMinPFTv;

    public Holder(View v) {
      mTitleTv = v.findViewById(R.id.tv_title_report_item);
      mDeviceNameTv = v.findViewById(R.id.tv_device_name_report_item);
      mRecordTimeTv = v.findViewById(R.id.tv_record_time_report_item);

      mMaxITv = v.findViewById(R.id.tv_max_i_report_item);
      mMinITv = v.findViewById(R.id.tv_min_i_report_item);

      mMaxVTv = v.findViewById(R.id.tv_max_v_report_item);
      mMinVTv = v.findViewById(R.id.tv_min_v_report_item);

      mMaxPTv = v.findViewById(R.id.tv_max_p_report_item);
      mMinPTv = v.findViewById(R.id.tv_min_p_report_item);

      mMaxQTv = v.findViewById(R.id.tv_max_q_report_item);
      mMinQTv = v.findViewById(R.id.tv_min_q_report_item);

      mMaxPFTv = v.findViewById(R.id.tv_max_pf_report_item);
      mMinPFTv = v.findViewById(R.id.tv_min_pf_report_item);

    }
  }

  public void clear() {
    mInfoList.clear();
    notifyDataSetChanged();
  }
}
