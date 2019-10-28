package com.hlbd.electric.feature.launcher.data;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hlbd.electric.R;
import com.hlbd.electric.base.BaseFragment;
import com.hlbd.electric.feature.launcher.data.analysis.AnalysisRootFragment;
import com.hlbd.electric.feature.launcher.data.analysis.analysis.SystemAnalysisFragment;
import com.hlbd.electric.feature.launcher.data.analysis.count.CountFragment;
import com.hlbd.electric.feature.launcher.data.analysis.power.PowerFragment;
import com.hlbd.electric.feature.launcher.data.realtime.RealtimeDataFragment;
import com.hlbd.electric.feature.launcher.mime.IPage;
import com.hlbd.electric.util.LogUtil;

import java.util.ArrayList;

public class DataFragment extends BaseFragment implements IPage {

  private static final String TAG = "DataFragment";
  public static final int PAGE_REALTIME = 0;
  public static final int PAGE_ANALYSIS_ROOT = 1;
  public static final int PAGE_POWER = 2;
  public static final int PAGE_SYSTEM = 3;
  public static final int PAGE_COUNT = 4;

  private ArrayList<BaseFragment> mFragments;
  private BaseFragment mCurrentFragment;
  private FragmentManager mManager;
  private BaseFragment mRealtimeFragment;
  private BaseFragment mAnalysisRootFragment;
  private BaseFragment mPowerFragment;
  private BaseFragment mSystemFragment;
  private BaseFragment mCountFragment;
  private int mCurrentPage;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    if (mBaseView == null) {
      mBaseView = inflater.inflate(R.layout.fragment_data, container, false);
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
    mFragments = new ArrayList<>();

    mRealtimeFragment = new RealtimeDataFragment();
    mRealtimeFragment.setPage(this);
    mFragments.add(mRealtimeFragment);

    mAnalysisRootFragment = new AnalysisRootFragment();
    mAnalysisRootFragment.setPage(this);
    mFragments.add(mAnalysisRootFragment);

    mPowerFragment = new PowerFragment();
    mPowerFragment.setPage(this);
    mFragments.add(mPowerFragment);

    mSystemFragment = new SystemAnalysisFragment();
    mSystemFragment.setPage(this);
    mFragments.add(mSystemFragment);

    mCountFragment = new CountFragment();
    mCountFragment.setPage(this);
    mFragments.add(mCountFragment);

    mCurrentFragment = mRealtimeFragment;

    mManager = getChildFragmentManager();
    FragmentTransaction transaction = mManager.beginTransaction();
    transaction.add(R.id.ll_fragment_data, mCountFragment)
            .hide(mCountFragment)
            .add(R.id.ll_fragment_data, mSystemFragment)
            .hide(mSystemFragment)
            .add(R.id.ll_fragment_data, mPowerFragment)
            .hide(mPowerFragment)
            .add(R.id.ll_fragment_data, mAnalysisRootFragment)
            .hide(mAnalysisRootFragment)
            .add(R.id.ll_fragment_data, mCurrentFragment)
            .commit();

    mCurrentPage = 0;
    notifyDataAndState(mCurrentPage);
  }

  private void initView() {

  }

  @Override
  public void turnPage(int page) {
    if (page == mCurrentPage) {
      return;
    }
    notifyDataAndState(page);
    mCurrentPage = page;
  }

  private void notifyDataAndState(int index) {
    BaseFragment fragment = mFragments.get(index);
    LogUtil.d(TAG, "notifyDataAndState() page=" + index + ", fragment=" + fragment + ", mCurrentFragment=" + mCurrentFragment);
    mCurrentFragment.stopTask();
    fragment.startTask();
    FragmentTransaction transaction = mManager.beginTransaction();
    transaction.hide(mCurrentFragment).show(fragment)
            .commit();
    mCurrentFragment = fragment;
  }

  @Override
  public void startTask() {
    LogUtil.d(TAG, "startTask() mCurrentFragment=" + mCurrentFragment);
    if (mCurrentFragment != null) {
      mCurrentFragment.startTask();
    }
  }

  @Override
  public void stopTask() {
    if (mCurrentFragment != null) {
      mCurrentFragment.stopTask();
    }
  }
}
