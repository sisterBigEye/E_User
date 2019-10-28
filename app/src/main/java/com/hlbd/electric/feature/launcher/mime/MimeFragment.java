package com.hlbd.electric.feature.launcher.mime;

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
import com.hlbd.electric.feature.launcher.mime.detail.MimeDetailFragment;
import com.hlbd.electric.feature.launcher.mime.main.MimeMainFragment;
import com.hlbd.electric.feature.launcher.mime.modify.info.ModifyInfoFragment;
import com.hlbd.electric.feature.launcher.mime.modify.password.ModifyPasswordFragment;
import com.hlbd.electric.util.LogUtil;

import java.util.ArrayList;

public class MimeFragment extends BaseFragment implements IPage{

  private static final String TAG = "MimeFragment";
  public static final int PAGE_MAIN = 0;
  public static final int PAGE_DETAIL = 1;
  public static final int PAGE_MODIFY_INFO = 2;
  public static final int PAGE_MODIFY_PASSWORD = 3;

  private ArrayList<BaseFragment> mFragments;
  private BaseFragment mCurrentFragment;
  private FragmentManager mManager;
  private BaseFragment mMainFragment;
  private BaseFragment mDetailFragment;
  private BaseFragment mModifyInfoFragment;
  private BaseFragment mModifyPasswordFragment;
  private int mIndex;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    if (mBaseView == null) {
      mBaseView = inflater.inflate(R.layout.fragment_mime, container, false);
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

    mMainFragment = new MimeMainFragment();
    mMainFragment.setPage(this);
    mFragments.add(mMainFragment);

    mDetailFragment = new MimeDetailFragment();
    mDetailFragment.setPage(this);
    mFragments.add(mDetailFragment);

    mModifyInfoFragment = new ModifyInfoFragment();
    mModifyInfoFragment.setPage(this);
    mFragments.add(mModifyInfoFragment);

    mModifyPasswordFragment = new ModifyPasswordFragment();
    mModifyPasswordFragment.setPage(this);
    mFragments.add(mModifyPasswordFragment);

    mCurrentFragment = mMainFragment;

    mManager = getChildFragmentManager();
    FragmentTransaction transaction = mManager.beginTransaction();
    transaction.add(R.id.ll_fragment_mime, mModifyPasswordFragment)
            .hide(mModifyPasswordFragment)
            .add(R.id.ll_fragment_mime, mModifyInfoFragment)
            .hide(mModifyInfoFragment)
            .add(R.id.ll_fragment_mime, mDetailFragment)
            .hide(mDetailFragment)
            .add(R.id.ll_fragment_mime, mCurrentFragment)
            .commit();

    mIndex = 0;
    notifyDataAndState(mIndex);
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
    mCurrentFragment.stopTask();
    fragment.startTask();
    FragmentTransaction transaction = mManager.beginTransaction();
    transaction.hide(mCurrentFragment).show(fragment)
            .commit();
    mCurrentFragment = fragment;
  }

  @Override
  public void startTask() {
    if (mCurrentFragment != null) {
      mCurrentFragment.startTask();
    }
  }
}
