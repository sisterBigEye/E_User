package com.hlbd.electric.feature.launcher.order;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hlbd.electric.R;
import com.hlbd.electric.feature.launcher.order.data.OrderData;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderAdapter extends BaseAdapter {

  public List<OrderData.Row> mInfoList;
  private DateFormat mDateFormat= DateFormat.getDateTimeInstance();

  public OrderAdapter() {
    mInfoList = new ArrayList<>();
  }

  public void updateData(List<OrderData.Row> list) {
    mInfoList.clear();
    addDataList(list);
  }

  public void addDataList(List<OrderData.Row> list) {
    mInfoList.clear();;
    if (list == null || list.size() == 0) {
      return;
    }
    mInfoList.addAll(list);
    notifyDataSetChanged();
  }

  public void addData(OrderData.Row info) {
    if (info != null) {
      mInfoList.add(info);
    }
  }

  @Override
  public int getCount() {
    return mInfoList.size();
  }

  @Override
  public OrderData.Row getItem(int position) {
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
      convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_order, parent, false);
      holder = new Holder(convertView);
      convertView.setTag(holder);
    } else {
      holder = (Holder) convertView.getTag();
    }
    OrderData.Row info = mInfoList.get(position);
    if (info != null) {
      if (info.isSelect) {
        holder.mItem.setBackgroundColor(parent.getContext().getResources().getColor(R.color.gray));
      } else {
        holder.mItem.setBackground(null);
      }
      holder.mTitleTv.setText(info.usersource);
      holder.mContentTv.setText("处理状态: " + info.repairstatus);
      String time = "";
      if (info.createtime > 0) {
        time = mDateFormat.format(new Date(info.createtime));
      }
      holder.mTimeTv.setText("创单时间: " + time);
    }

    return convertView;
  }

  private static class Holder {
    private TextView mTitleTv;
    private TextView mTimeTv;
    private TextView mContentTv;
    private View mItem;

    public Holder(View v) {
      mItem = v.findViewById(R.id.v_item_order_item);
      mTitleTv = v.findViewById(R.id.tv_title_order_item);
      mTimeTv = v.findViewById(R.id.tv_time_order_item);
      mContentTv = v.findViewById(R.id.tv_content_order_item);

    }
  }

  public void clear() {
    mInfoList.clear();
    notifyDataSetChanged();
  }
}
