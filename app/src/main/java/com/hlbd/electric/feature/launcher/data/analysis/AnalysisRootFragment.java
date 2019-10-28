package com.hlbd.electric.feature.launcher.data.analysis;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hlbd.electric.R;
import com.hlbd.electric.base.BaseFragment;
import com.hlbd.electric.feature.launcher.data.DataFragment;

public class AnalysisRootFragment extends BaseFragment implements View.OnClickListener {

  private static final String TAG = "AnalysisRootFragment";

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    if (mBaseView == null) {
      mBaseView = inflater.inflate(R.layout.fragment_data_analysis, container, false);
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
    mTitleTv.setText("数据统计分析");
    mBackIv.setVisibility(View.VISIBLE);
    mBackIv.setOnClickListener(this);

    mBaseView.findViewById(R.id.ll_power_analysis_data).setOnClickListener(this);
    mBaseView.findViewById(R.id.ll_system_analysis_data).setOnClickListener(this);
    mBaseView.findViewById(R.id.ll_count_analysis_data).setOnClickListener(this);
  }

  private void init() {
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.iv_back:
        if (mPage != null) {
          mPage.turnPage(DataFragment.PAGE_REALTIME);
        }
        break;

      case R.id.ll_power_analysis_data:
        if (mPage != null) {
          mPage.turnPage(DataFragment.PAGE_POWER);
        }
        break;

      case R.id.ll_system_analysis_data:
        if (mPage != null) {
          mPage.turnPage(DataFragment.PAGE_SYSTEM);
        }
        break;

      case R.id.ll_count_analysis_data:
        if (mPage != null) {
          mPage.turnPage(DataFragment.PAGE_COUNT);
        }
        break;

      default:
        break;
    }
  }

  @Override
  public void startTask() {
  }
}
