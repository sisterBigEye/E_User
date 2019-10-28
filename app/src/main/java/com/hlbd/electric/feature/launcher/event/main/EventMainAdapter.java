package com.hlbd.electric.feature.launcher.event.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hlbd.electric.R;
import com.hlbd.electric.feature.launcher.event.main.data.EventMainData;
import com.hlbd.electric.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

public class EventMainAdapter extends BaseAdapter {

  public List<EventMainData.Row> mInfoList;

  public EventMainAdapter() {
    mInfoList = new ArrayList<>();
  }

  public void updateData(List<EventMainData.Row> list) {
    mInfoList.clear();
    addDataList(list);
  }

  public void addDataList(List<EventMainData.Row> list) {
    mInfoList.clear();
    ;
    if (list == null || list.size() == 0) {
      return;
    }
    mInfoList.addAll(list);
    notifyDataSetChanged();
  }

  public void addData(EventMainData.Row info) {
    if (info != null) {
      mInfoList.add(info);
    }
  }

  @Override
  public int getCount() {
    return mInfoList.size();
  }

  @Override
  public EventMainData.Row getItem(int position) {
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
      convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_event, parent, false);
      holder = new Holder(convertView);
      convertView.setTag(holder);
    } else {
      holder = (Holder) convertView.getTag();
    }
    EventMainData.Row info = mInfoList.get(position);
    if (info != null) {
      if (info.isSelect) {
        holder.mItem.setBackgroundColor(parent.getContext().getResources().getColor(R.color.gray));
      } else {
        holder.mItem.setBackground(null);
      }
      holder.mTitleTv.setText(info.source);
      String time = "";
      if (info.timestamp > 0) {
        time = TimeUtil.getDateTime(info.timestamp);
      }
      holder.mTimeTv.setText("报警时间: " + time);
      holder.mContentTv.setText("报警内容: " + info.name);
    }
    return convertView;
  }

  private static class Holder {
    private TextView mTitleTv;
    private TextView mTimeTv;
    private TextView mContentTv;
    private View mItem;

    public Holder(View v) {
      mItem = v.findViewById(R.id.v_item_event_item);
      mTitleTv = v.findViewById(R.id.tv_title_event_item);
      mTimeTv = v.findViewById(R.id.tv_time_event_item);
      mContentTv = v.findViewById(R.id.tv_content_event_item);

    }
  }

  public void clear() {
    mInfoList.clear();
    notifyDataSetChanged();
  }
}
