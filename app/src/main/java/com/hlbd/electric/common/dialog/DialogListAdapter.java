package com.hlbd.electric.common.dialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hlbd.electric.R;
import com.hlbd.electric.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class DialogListAdapter extends BaseAdapter {

  private static final String TAG = "DialogListAdapter";
  public List<IContent> mInfoList;

  public DialogListAdapter() {
    mInfoList = new ArrayList<>();
  }

  public void addDataList(List<IContent> list) {
    mInfoList.clear();
    ;
    if (list == null || list.size() == 0) {
      return;
    }
    mInfoList.addAll(list);
    notifyDataSetChanged();
  }

  public void addData(IContent content) {
    mInfoList.add(content);
  }

  public void clear() {
    mInfoList.clear();
    notifyDataSetChanged();
  }

  @Override
  public int getCount() {
    return mInfoList.size();
  }

  @Override
  public IContent getItem(int position) {
    return mInfoList.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    DialogListAdapter.Holder holder;
    if (convertView == null) {
      convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_dialog_content, parent, false);
      holder = new DialogListAdapter.Holder(convertView);
      convertView.setTag(holder);
    } else {
      holder = (DialogListAdapter.Holder) convertView.getTag();
    }
    IContent content = mInfoList.get(position);
    LogUtil.d(TAG, "getView() content=" + content);
    if (content != null) {
      holder.mTitleTv.setText(content.getDescription());
    }
    return convertView;
  }

  private static class Holder {
    private TextView mTitleTv;

    public Holder(View v) {
      mTitleTv = v.findViewById(R.id.tv_content_dialog_list);

    }
  }

}
