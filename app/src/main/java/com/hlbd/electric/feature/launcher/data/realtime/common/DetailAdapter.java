package com.hlbd.electric.feature.launcher.data.realtime.common;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hlbd.electric.R;
import com.hlbd.electric.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YySleep on 2018/3/14.
 *
 * @author YySleep
 */

public class DetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  private final static String TAG = "DynamicAdapter";
  private final static int VIEW_TYPE_01 = 1;

  private List<DetailInfo> list;
  private OnItemClickListener mItemClickListener;

  public DetailAdapter() {
    list = new ArrayList<>();
  }

  public void updateList(List<DetailInfo> dataList) {
    list.clear();
    if (dataList != null && dataList.size() > 0) {
      this.list.addAll(dataList);
    }
    notifyDataSetChanged();
  }

  public void clear() {
    list.clear();
    notifyDataSetChanged();
  }

  public void add(DetailInfo info) {
    LogUtil.d(TAG, "add() +++ info=" + info);
    if (info == null) {
      return;
    }

    if (info.desc == null) {
      LogUtil.w(TAG, "add() info has no desc");
      return;
    }

    for (int i = 0; i < list.size(); i++) {
      DetailInfo internal = list.get(i);
      if (internal.desc.equals(info.desc)) {
        if (!internal.value.equals(info.value)) {
          list.set(i, info);
          notifyItemChanged(i);
        } else {
          LogUtil.d(TAG, "add() already has this value! internal.value=" + internal.value);
        }
        return;
      }
    }
    int position = list.size();
    list.add(info);
    notifyItemInserted(position);
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    RecyclerView.ViewHolder holder;
    switch (viewType) {
      default:
        holder = new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_detail, parent, false));
        break;
    }
    return holder;
  }

  @Override
  public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
    if (list == null || list.size() == 0) {
      return;
    }
    DetailInfo info = list.get(position);
    if (info == null) {
      return;
    }
    switch (holder.getItemViewType()) {
      default:
        Holder viewHolder = (Holder) holder;
        viewHolder.mDescTv.setText(info.desc);
        viewHolder.mValue.setText(info.value);
        if (info.resId > 0) {
          viewHolder.mIcIv.setImageDrawable(viewHolder.mIcIv.getContext().getDrawable(info.resId));
        }
        if (mItemClickListener != null) {
          viewHolder.mItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              mItemClickListener.onItemClick(holder.getAdapterPosition());
            }
          });
        }

        break;
    }
  }

  @Override
  public int getItemViewType(int position) {
    return VIEW_TYPE_01;
  }

  @Override
  public int getItemCount() {
    return list == null ? 0 : list.size();
  }

  public void setOnItemClickListener(OnItemClickListener listener) {
    mItemClickListener = listener;
  }

  private static class Holder extends RecyclerView.ViewHolder {

    private TextView mValue;
    private TextView mDescTv;
    private ImageView mIcIv;
    private View mItem;

    private Holder(View itemView) {
      super(itemView);
      mItem = itemView.findViewById(R.id.v_item_detail_item);
      mValue = itemView.findViewById(R.id.tv_value_detail_item);
      mDescTv = itemView.findViewById(R.id.tv_desc_detail_item);
      mIcIv = itemView.findViewById(R.id.iv_icon_detail_item);
    }

  }

  public interface OnItemClickListener {

    void onItemClick(int position);
  }
}
