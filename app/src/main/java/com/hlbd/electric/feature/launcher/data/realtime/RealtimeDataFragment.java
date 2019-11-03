package com.hlbd.electric.feature.launcher.data.realtime;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hlbd.electric.R;
import com.hlbd.electric.api.HttpApi;
import com.hlbd.electric.base.BaseFragment;
import com.hlbd.electric.feature.BmpRequest;
import com.hlbd.electric.feature.launcher.data.DataFragment;
import com.hlbd.electric.feature.launcher.data.realtime.busbar.BusBarFragment;
import com.hlbd.electric.feature.launcher.data.realtime.common.CommonRequest;
import com.hlbd.electric.feature.launcher.data.realtime.elec.ElecFragment;
import com.hlbd.electric.feature.launcher.data.realtime.env.EnvFragment;
import com.hlbd.electric.feature.launcher.data.realtime.flooding.FloFragment;
import com.hlbd.electric.feature.launcher.data.realtime.video.VideoFragment;
import com.hlbd.electric.util.LogUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class RealtimeDataFragment extends BaseFragment implements RealtimeDataContract.View, View.OnClickListener {

  private static final String TAG = "RealtimeDataFragment";
  public static final int INDEX_ELE = 0;
  public static final int INDEX_ENV = 1;
  public static final int INDEX_BUS = 2;
  public static final int INDEX_FLO = 3;
  public static final int INDEX_VIDEO = 4;

  private Bitmap mBmp;
  private BmpRequest mBmpRequest;
  private RealtimeDataContract.Presenter mPresenter;
  private ImageView mIv;
  private EventHandler mHandler;
  private ArrayList<Fragment> mFragments;
  private BaseFragment mCurrentFragment;
  private FragmentManager mManager;
  private BaseFragment mElecFragment;
  private BaseFragment mEnvFragment;
  private BaseFragment mBusBarFragment;
  private BaseFragment mFloFragment;
  private BaseFragment mVideoFragment;
  private ArrayList<TabInfo> mTabLlList;
  private ArrayList<Integer> mSelectBackGroundIdList;
  private ArrayList<Integer> mDefaultBackGroundIdList;
  private int mIndex;

  private View mOtherV;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    if (mBaseView == null) {
      mBaseView = inflater.inflate(R.layout.fragment_realtime_data, container, false);
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
    mOtherV = mBaseView.findViewById(R.id.ll_other_data);
    mFragments = new ArrayList<>();

    mElecFragment = new ElecFragment();
    mFragments.add(mElecFragment);

    mEnvFragment = new EnvFragment();
    mFragments.add(mEnvFragment);

    mBusBarFragment = new BusBarFragment();
    mFragments.add(mBusBarFragment);

    mFloFragment = new FloFragment();
    mFragments.add(mFloFragment);

    mVideoFragment = new VideoFragment();
    mFragments.add(mVideoFragment);

    mCurrentFragment = mElecFragment;
    mManager = getChildFragmentManager();
    FragmentTransaction transaction = mManager.beginTransaction();
    transaction.add(R.id.ll_fgt_detail_data, mVideoFragment)
            .hide(mVideoFragment)
            .add(R.id.ll_fgt_detail_data, mFloFragment)
            .hide(mFloFragment)
            .add(R.id.ll_fgt_detail_data, mBusBarFragment)
            .hide(mBusBarFragment)
            .add(R.id.ll_fgt_detail_data, mEnvFragment)
            .hide(mEnvFragment)
            .add(R.id.ll_fgt_detail_data, mCurrentFragment)
            .commit();

    mHandler = new EventHandler(this);
    mBmpRequest = new BmpRequest();
    mBmpRequest.username = HttpApi.getUserName();
    mBmpRequest.url = "Baoding_UserAvatar.DT/Services/SelectBackgroundImage?";
    mPresenter = new RealtimeDataPresenter(this, mBmpRequest);
    mPresenter.start();

    mIndex = 0;
    notifyDataAndState(mIndex, mCurrentFragment);
  }

  private void initView() {
    mTitleTv.setText("实时数据");
    mIv = mBaseView.findViewById(R.id.iv_data_detail_data);

    mBaseView.findViewById(R.id.btn_analysus_detail_data).setOnClickListener(this);

    mTabLlList = new ArrayList<>();

    mSelectBackGroundIdList = new ArrayList<>();
    mDefaultBackGroundIdList = new ArrayList<>();

    LinearLayout eleLl = mBaseView.findViewById(R.id.ll_electric_detail_data);
    eleLl.setOnClickListener(this);
    ImageView eleIv = mBaseView.findViewById(R.id.iv_electric_detail_data);
    TextView eleTv = mBaseView.findViewById(R.id.btn_electric_detail_data);
    View eleV = mBaseView.findViewById(R.id.v_electric_detail_data);
    mTabLlList.add(new TabInfo(eleLl, eleIv, eleTv, eleV));

    mDefaultBackGroundIdList.add(R.drawable.ic_data_electric);
    mSelectBackGroundIdList.add(R.drawable.ic_data_electric_select);

    LinearLayout envLl = mBaseView.findViewById(R.id.ll_environment_detail_data);
    envLl.setOnClickListener(this);
    ImageView envIv = mBaseView.findViewById(R.id.iv_environment_detail_data);
    TextView envTv = mBaseView.findViewById(R.id.btn_environment_detail_data);
    View envV = mBaseView.findViewById(R.id.v_environment_detail_data);
    mTabLlList.add(new TabInfo(envLl, envIv, envTv, envV));

    mDefaultBackGroundIdList.add(R.drawable.ic_data_environment);
    mSelectBackGroundIdList.add(R.drawable.ic_data_environment_select);

    LinearLayout busbarLl = mBaseView.findViewById(R.id.ll_busbar_detail_data);
    busbarLl.setOnClickListener(this);
    ImageView busbarIv = mBaseView.findViewById(R.id.iv_busbar_detail_data);
    TextView busbarTv = mBaseView.findViewById(R.id.btn_busbar_detail_data);
    View busbarV = mBaseView.findViewById(R.id.v_busbar_detail_data);
    mTabLlList.add(new TabInfo(busbarLl, busbarIv, busbarTv, busbarV));

    mDefaultBackGroundIdList.add(R.drawable.ic_data_busbar);
    mSelectBackGroundIdList.add(R.drawable.ic_data_busbar_select);

    LinearLayout floLl = mBaseView.findViewById(R.id.ll_flooding_detail_data);
    floLl.setOnClickListener(this);
    ImageView floIv = mBaseView.findViewById(R.id.iv_flooding_detail_data);
    TextView floTv = mBaseView.findViewById(R.id.btn_flooding_detail_data);
    View floV = mBaseView.findViewById(R.id.v_flooding_detail_data);
    mTabLlList.add(new TabInfo(floLl, floIv, floTv, floV));

    mDefaultBackGroundIdList.add(R.drawable.ic_data_flooding);
    mSelectBackGroundIdList.add(R.drawable.ic_data_flooding_select);


    LinearLayout videoLl = mBaseView.findViewById(R.id.ll_video_detail_data);
    videoLl.setOnClickListener(this);
    ImageView videoIv = mBaseView.findViewById(R.id.iv_video_detail_data);
    TextView videoTv = mBaseView.findViewById(R.id.btn_video_detail_data);
    View videoV = mBaseView.findViewById(R.id.v_video_detail_data);
    mTabLlList.add(new TabInfo(videoLl, videoIv, videoTv, videoV));

    mDefaultBackGroundIdList.add(R.drawable.ic_data_video);
    mSelectBackGroundIdList.add(R.drawable.ic_data_video_select);
  }

  @Override
  public void showLoading() {

  }

  @Override
  public void dismissLoading() {

  }

  @Override
  public void showBitmap(Bitmap bitmap) {
    LogUtil.d(TAG, "showBitmap() bmp=" + bitmap);
    if (bitmap == null) {
      mHandler.sendEmptyMessageDelayed(EventHandler.BITMAP_REQUEST, CommonRequest.DEFAULT_RESULT_TIME);
      return;
    }
    mIv.setImageBitmap(bitmap);
    mBmp = bitmap;
  }

  @Override
  public void setPersonal(RealtimeDataContract.Presenter p) {
    mPresenter = p;
  }

  @Override
  public void onClick(View v) {
    BaseFragment fragment = mCurrentFragment;
    boolean needChange = false;
    switch (v.getId()) {
      case R.id.btn_analysus_detail_data:
        if (mPage != null) {
          mPage.turnPage(DataFragment.PAGE_ANALYSIS_ROOT);
          return;
        }
        break;

      case R.id.ll_electric_detail_data:
        if (mIndex != INDEX_ELE) {
          needChange = true;
          mIndex = INDEX_ELE;
          fragment = mElecFragment;
        }
        break;

      case R.id.ll_environment_detail_data:
        if (mIndex != INDEX_ENV) {
          needChange = true;
          mIndex = INDEX_ENV;
          fragment = mEnvFragment;
        }
        break;

      case R.id.ll_busbar_detail_data:
        if (mIndex != INDEX_BUS) {
          needChange = true;
          mIndex = INDEX_BUS;
          fragment = mBusBarFragment;
        }
        break;

      case R.id.ll_flooding_detail_data:
        if (mIndex != INDEX_FLO) {
          needChange = true;
          mIndex = INDEX_FLO;
          fragment = mFloFragment;
        }
        break;

      case R.id.ll_video_detail_data:
        if (mIndex != INDEX_VIDEO) {
          needChange = true;
          mIndex = INDEX_VIDEO;
          fragment = mVideoFragment;
        }
        break;
      default:
        break;
    }
    notifyDataAndState(mIndex, fragment);
    /*if (needChange) {

    }*/
  }

  private static class EventHandler extends Handler {

    private final WeakReference<RealtimeDataFragment> mRef;
    private static final int BITMAP_REQUEST = 1;

    private EventHandler(RealtimeDataFragment fragment) {
      mRef = new WeakReference<>(fragment);
    }

    @Override
    public void handleMessage(Message msg) {
      RealtimeDataFragment fragment = mRef.get();
      if (fragment == null) {
        return;
      }
      switch (msg.what) {
        case BITMAP_REQUEST:
          if (fragment.mBmp == null) {
            fragment.startTask();
            sendEmptyMessageDelayed(BITMAP_REQUEST, CommonRequest.DEFAULT_RESULT_TIME);
          }
          break;
        default:
          break;
      }
    }
  }

  public void startTask() {
    if (mPresenter != null) {
      mPresenter.start();
    }
  }

  private void notifyDataAndState(int index, BaseFragment fragment) {
    if (index == INDEX_VIDEO) {
      mOtherV.setVisibility(View.GONE);
    } else {
      mOtherV.setVisibility(View.VISIBLE);
    }
    mCurrentFragment.stopTask();
    fragment.startTask();
    FragmentTransaction transaction = mManager.beginTransaction();
    transaction.hide(mCurrentFragment).show(fragment)
            .commit();
    mCurrentFragment = fragment;

    for (int i = 0; i < mTabLlList.size(); i++) {
      TabInfo info = mTabLlList.get(i);
      if (info == null) {
        continue;
      }
      ImageView iv = info.iv;
      TextView tv = info.tv;
      View line = info.line;
      if (tv == null || line == null) {
        continue;
      }
      Drawable drawable = null;
      if (i == index) {
        line.setBackgroundColor(getResources().getColor(R.color.data_icon_select));
        tv.setTextColor(getResources().getColor(R.color.data_icon_select));
        iv.setImageDrawable(getResources().getDrawable(mSelectBackGroundIdList.get(i)));
        continue;
      }
      line.setBackgroundColor(getResources().getColor(R.color.data_icon));
      tv.setTextColor(getResources().getColor(R.color.black));
      iv.setImageDrawable(getResources().getDrawable(mDefaultBackGroundIdList.get(i)));
    }
  }

  @Override
  public void stopTask() {
    super.stopTask();
    if (mCurrentFragment != null) {
      mCurrentFragment.stopTask();
    }

  }
}
