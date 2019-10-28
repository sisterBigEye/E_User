package com.hlbd.electric.feature.launcher.data.realtime.video.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hlbd.electric.R;
import com.hlbd.electric.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class VideoListAdapter extends BaseAdapter {
  private static final String TAG = "VideoListAdapter";
  public List<VideoInfo.Row> mInfoList;

  public VideoListAdapter() {
    mInfoList = new ArrayList<>();
  }

  public void addDataList(List<VideoInfo.Row> list) {
    mInfoList.clear();
    ;
    if (list == null || list.size() == 0) {
      notifyDataSetChanged();
      return;
    }
    mInfoList.addAll(list);
    notifyDataSetChanged();
  }

  @Override
  public int getCount() {
    return mInfoList.size();
  }

  @Override
  public VideoInfo.Row getItem(int position) {
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
      convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_video, parent, false);
      holder = new Holder(convertView);
      convertView.setTag(holder);
    } else {
      holder = (Holder) convertView.getTag();
    }
    VideoInfo.Row info = mInfoList.get(position);
    LogUtil.d(TAG, "getView() indo=" + info);
    if (info != null) {
      holder.mTitleTv.setText(info.specificname);
    }
    return convertView;
  }

  private static class Holder {
    private TextView mTitleTv;

    public Holder(View v) {
      mTitleTv = v.findViewById(R.id.tv_video_list);

    }
  }

}
