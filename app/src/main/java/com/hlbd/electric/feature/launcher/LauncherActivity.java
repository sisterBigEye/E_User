package com.hlbd.electric.feature.launcher;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.hlbd.electric.R;
import com.hlbd.electric.base.BaseActivity;
import com.hlbd.electric.base.BaseFragment;
import com.hlbd.electric.feature.launcher.data.DataFragment;
import com.hlbd.electric.feature.launcher.event.EventFragment;
import com.hlbd.electric.feature.launcher.mime.MimeFragment;
import com.hlbd.electric.feature.launcher.order.WorkOrderFragment;
import com.hlbd.electric.feature.launcher.report.ReportFragment;
import com.hlbd.electric.util.LogUtil;

import java.util.ArrayList;

public class LauncherActivity extends BaseActivity implements View.OnClickListener {

  private static final String TAG = "LauncherActivity";

  private BaseFragment mDataFragment;
  private BaseFragment mEventFragment;
  private BaseFragment mWorkOrderFragment;
  private BaseFragment mReportFragment;
  private BaseFragment mMimeFragment;

  private int mFragmentPage = 0;
  public static final int PAGE_DATA = 0;
  public static final int PAGE_EVENT = 1;
  public static final int PAGE_ORDER = 2;
  public static final int PAGE_REPORT = 3;
  public static final int PAGE_MIME = 4;

  FragmentManager mManager;

  private View mDataV;
  private View mEventV;
  private View mWorkOrderV;
  private View mReportV;
  private View mMimeV;

  private ArrayList<View> mVList;

  private BaseFragment mCurrentFragment;
  private ArrayList<BaseFragment> mFragments;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // 设置 NavigationBar 的颜色
    getWindow().setNavigationBarColor(Color.BLACK);
    init();
    initView();
    LogUtil.d(TAG, "onCreate()" + this);
  }

  private void init() {
    mFragments = new ArrayList<>();
    mDataFragment = new DataFragment();
    mFragments.add(mDataFragment);
    mEventFragment = new EventFragment();
    mFragments.add(mEventFragment);
    mWorkOrderFragment = new WorkOrderFragment();
    mFragments.add(mWorkOrderFragment);
    mReportFragment = new ReportFragment();
    mFragments.add(mReportFragment);
    mMimeFragment = new MimeFragment();
    mFragments.add(mMimeFragment);
    mCurrentFragment = mDataFragment;
  }

  private void initView() {
    mVList = new ArrayList<>();

    mDataV = findViewById(R.id.btn_data_launcher);
    mDataV.setOnClickListener(this);
    mVList.add(mDataV);

    mEventV = findViewById(R.id.btn_event_launcher);
    mEventV.setOnClickListener(this);
    mVList.add(mEventV);

    mWorkOrderV = findViewById(R.id.btn_work_order_launcher);
    mWorkOrderV.setOnClickListener(this);
    mVList.add(mWorkOrderV);

    mReportV = findViewById(R.id.btn_report_launcher);
    mReportV.setOnClickListener(this);
    mVList.add(mReportV);

    mMimeV = findViewById(R.id.btn_mime_launcher);
    mMimeV.setOnClickListener(this);
    mVList.add(mMimeV);
    notifyIconState(0);

    mManager = getSupportFragmentManager();
    FragmentTransaction transaction = mManager.beginTransaction();
    for (Fragment fragment : mFragments) {
      transaction.add(R.id.fgt_launcher, fragment).hide(fragment);
    }
    transaction.show(mCurrentFragment);
    transaction.commit();
  }

  @Override
  protected int layoutId() {
    return R.layout.activity_launcher;
  }

  @Override
  protected String toolBarTitle() {
    return "";
  }

  @Override
  public void onClick(View v) {
    BaseFragment fragment = null;
    int selectPage;
    switch (v.getId()) {
      case R.id.btn_data_launcher:
        selectPage = PAGE_DATA;
        break;

      case R.id.btn_event_launcher:
        selectPage = PAGE_EVENT;
        break;

      case R.id.btn_work_order_launcher:
        selectPage = PAGE_ORDER;
        break;

      case R.id.btn_report_launcher:
        selectPage = PAGE_REPORT;
        break;

      case R.id.btn_mime_launcher:
        selectPage = PAGE_MIME;
        break;

      default:
        selectPage = mFragmentPage;
        break;
    }

    /*if (selectPage == mFragmentPage) {
      return;
    }*/
    fragment = mFragments.get(selectPage);
    if (fragment == null) {
      return;
    }
    mFragmentPage = selectPage;

    LogUtil.d(TAG, "onClick() --- mFragmentPage=" + mFragmentPage
            + ", mCurrentFragment=" + mCurrentFragment
            + ", fragment=" + fragment);
    FragmentTransaction transaction = mManager.beginTransaction();
    transaction.hide(mCurrentFragment).show(fragment).commit();
    notifyIconState(mFragmentPage);
    mCurrentFragment.stopTask();
    fragment.startTask();
    mCurrentFragment = fragment;

  }

  private void notifyIconState(int index) {
    for (int i = 0; i < mVList.size(); i++) {
      View v = mVList.get(i);
      if (v == null) {
        continue;
      }
      if (i == index) {
        v.setBackgroundColor(getResources().getColor(R.color.launcher_btn_select));
        continue;
      }
      v.setBackgroundColor(getResources().getColor(R.color.launcher_btn));
    }
  }

  @Override
  protected int getStatusBarColor() {
    return R.color.colorPrimary;
  }
}
