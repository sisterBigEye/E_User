package com.hlbd.electric.feature.launcher.event;

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
import com.hlbd.electric.feature.launcher.event.history.EvenHistoryFragment;
import com.hlbd.electric.feature.launcher.event.main.EventMainFragment;
import com.hlbd.electric.feature.launcher.event.order.EventOrderFragment;
import com.hlbd.electric.feature.launcher.mime.IPage;
import com.hlbd.electric.util.LogUtil;

import java.util.ArrayList;

public class EventFragment extends BaseFragment implements IPage {

  private static final String TAG = "EventFragment";
  public static final int PAGE_EVENT_MAIN = 0;
  public static final int PAGE_EVENT_ORDER = 1;
  public static final int PAGE_EVENT_HISTORY = 2;

  private ArrayList<BaseFragment> mFragments;
  private BaseFragment mCurrentFragment;
  private FragmentManager mManager;
  private BaseFragment mEventMainFragment;
  private BaseFragment mEventOrderFragment;
  private BaseFragment mEventHistoryFragment;
  private int mIndex;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    if (mBaseView == null) {
      mBaseView = inflater.inflate(R.layout.fragment_event, container, false);
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

    mEventMainFragment = new EventMainFragment();
    mEventMainFragment.setPage(this);
    mFragments.add(mEventMainFragment);

    mEventOrderFragment = new EventOrderFragment();
    mEventOrderFragment.setPage(this);
    mFragments.add(mEventOrderFragment);

    mEventHistoryFragment = new EvenHistoryFragment();
    mEventHistoryFragment.setPage(this);
    mFragments.add(mEventHistoryFragment);

    mCurrentFragment = mEventMainFragment;

    mManager = getChildFragmentManager();
    FragmentTransaction transaction = mManager.beginTransaction();
    transaction.add(R.id.ll_fragment_event, mEventHistoryFragment)
            .hide(mEventHistoryFragment)
            .add(R.id.ll_fragment_event, mEventOrderFragment)
            .hide(mEventOrderFragment)
            .add(R.id.ll_fragment_event, mCurrentFragment)
            .commit();

    mIndex = 0;
  }

  private void initView() {

  }


  @Override
  public void turnPage(int page) {
    notifyDataAndState(page);
  }

  private void notifyDataAndState(int index) {
    BaseFragment fragment = mFragments.get(index);
    LogUtil.d(TAG, "notifyDataAndState() page=" + index + ", fragment=" + fragment + ", mCurrentFragment=" + mCurrentFragment);
    FragmentTransaction transaction = mManager.beginTransaction();
    transaction.hide(mCurrentFragment).show(fragment)
            .commit();
    mCurrentFragment.stopTask();
    fragment.startTask();
    mCurrentFragment = fragment;
  }

  @Override
  public void startTask() {
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
