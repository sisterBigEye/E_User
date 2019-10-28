package com.hlbd.electric.feature.launcher.mime.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hlbd.electric.R;
import com.hlbd.electric.api.HttpApi;
import com.hlbd.electric.base.BaseFragment;
import com.hlbd.electric.feature.BmpRequest;
import com.hlbd.electric.feature.launcher.mime.MimeFragment;
import com.hlbd.electric.feature.launcher.mime.MimeInfoMgr;
import com.hlbd.electric.feature.launcher.mime.main.data.MimeInfoData;
import com.hlbd.electric.feature.launcher.mime.main.data.MimeOrderData;
import com.hlbd.electric.feature.launcher.mime.main.data.MimeAlertQuantityData;
import com.hlbd.electric.feature.launcher.mime.main.request.MimeAlertQuantityRequest;
import com.hlbd.electric.feature.launcher.mime.main.request.MimeOrderRequest;
import com.hlbd.electric.feature.launcher.mime.main.request.MimeInfoRequest;
import com.hlbd.electric.feature.login.LoginActivity;
import com.hlbd.electric.util.LogUtil;
import com.hlbd.electric.widget.CircularProgressView;

public class MimeMainFragment extends BaseFragment implements MimeMainContract.View, View.OnClickListener {

  private static final String TAG = "MimeMainFragment";
  private MimeMainContract.Presenter mPresenter;
  public static final String EVENT_TYPE_DAY = "本日";
  public static final String EVENT_TYPE_WEEK = "本周";
  public static final String EVENT_TYPE_MON = "本月";
  private String mCurrentEventType = EVENT_TYPE_DAY;
  private MimeOrderRequest mOrderRequest;
  private MimeAlertQuantityRequest mAlertQuantityRequest;
  private BmpRequest mBmpRequest;
  private MimeInfoRequest mMimeInfoRequest;
  private CircularProgressView mAlertQuantityCP;
  private CircularProgressView mOrderCP;

  private ImageView mIv;
  private TextView mNameTv;
  private TextView mNumTv;

  private TextView mDayTv;
  private TextView mWeekTv;
  private TextView mMonTv;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    if (mBaseView == null) {
      mBaseView = inflater.inflate(R.layout.fragment_mime_main, container, false);
    }

    return mBaseView;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    initView();
    init();
  }

  @Override
  public void setPersonal(MimeMainContract.Presenter p) {
    mPresenter = p;
  }

  public void startTask() {
    LogUtil.d(TAG, "startTask() Presenter=" + mPresenter);
    if (mPresenter == null) {
      return;
    }
    mPresenter.start();
  }


  private void init() {

    mOrderRequest = new MimeOrderRequest();
    mOrderRequest.user = HttpApi.getUserName();
    mOrderRequest.type = mCurrentEventType;
    mOrderRequest.url = "Baoding_Overview_DataSupport/Services/GetUserRepairOrderQuantityByDateType?";

    mAlertQuantityRequest = new MimeAlertQuantityRequest();
    mAlertQuantityRequest.user = HttpApi.getUserName();
    mAlertQuantityRequest.type = mCurrentEventType;
    mAlertQuantityRequest.url = "Baoding_Overview_DataSupport/Services/GetUserAlertQuantity?";

    mMimeInfoRequest = new MimeInfoRequest();
    mMimeInfoRequest.username = HttpApi.getUserName();
    mMimeInfoRequest.url = "Baoding_UserAccountManage/Services/SelectDataSQLByUsername?";

    mBmpRequest = new BmpRequest();
    mBmpRequest.username = HttpApi.getUserName();
    mBmpRequest.url = "Baoding_UserAvatar.DT/Services/SelectRow?";

    mPresenter = new MimeMainPresenter(this, mOrderRequest, mAlertQuantityRequest,
            mMimeInfoRequest, mBmpRequest);

    MimeInfoMgr.getsInstance().eventType = EVENT_TYPE_DAY;
  }

  private void initView() {
    mTitleTv.setText("我的信息");
    TextView tvBack = mBaseView.findViewById(R.id.tv_back);
    tvBack.setVisibility(View.VISIBLE);
    tvBack.setOnClickListener(this);

    mIv = mBaseView.findViewById(R.id.iv_image_main_mine);
    mNameTv = mBaseView.findViewById(R.id.tv_name_main_mine);
    mNumTv = mBaseView.findViewById(R.id.tv_number_main_mine);

    mBaseView.findViewById(R.id.btn_detail_main_mine).setOnClickListener(this);

    mDayTv = mBaseView.findViewById(R.id.tv_day_main_mine);
    mDayTv.setOnClickListener(this);

    mWeekTv = mBaseView.findViewById(R.id.tv_week_main_mine);
    mWeekTv.setOnClickListener(this);

    mMonTv = mBaseView.findViewById(R.id.tv_mon_main_mine);
    mMonTv.setOnClickListener(this);

    mAlertQuantityCP = mBaseView.findViewById(R.id.cp_alert_quantity_main_mine);
    mOrderCP = mBaseView.findViewById(R.id.cp_order_main_mine);
  }

  @Override
  public void updateMimeOrderData(MimeOrderData data) {
    try {
      for (MimeOrderData.Row row : data.rows) {
        if (row.result >= 0) {
          LogUtil.d(TAG, "updateMimeOrderData() row.result=" + row.result);
          mOrderCP.setData(row.result);
          break;
        }
      }
    } catch (Exception e) {
      LogUtil.e(TAG, "updateMimeOrderData() error", e);
    }
  }

  @Override
  public void updateAlertQuantityData(MimeAlertQuantityData data) {
    try {
      for (MimeAlertQuantityData.Row row : data.rows) {
        if (row.result >= 0) {
          LogUtil.e(TAG, "updateAlertQuantityData() row.result=" + row.result);
          mAlertQuantityCP.setData(row.result);
          break;
        }
      }
    } catch (Exception e) {
      LogUtil.e(TAG, "updateMimeOrderData() error", e);
    }
  }

  @Override
  public void updateMimeInfo(MimeInfoData data) {
    try {
      for (MimeInfoData.Row row : data.rows) {
        if (row.username != null) {
          mNameTv.setText(row.username);
          MimeInfoMgr.getsInstance().userName = row.username;
        }
        if (row.phonenumber != null) {
          mNumTv.setText(row.phonenumber);
          MimeInfoMgr.getsInstance().phoneNumber = row.phonenumber;
        }

        if (row.city != null) {
          MimeInfoMgr.getsInstance().city = row.city;
        }

        if (row.address != null) {
          MimeInfoMgr.getsInstance().address = row.address;
        }

        MimeInfoMgr.getsInstance().id = row.id;

        if (row.passwordstr != null) {
          MimeInfoMgr.getsInstance().passwordStr = row.passwordstr;
        }

        if (row.fullname != null) {
          MimeInfoMgr.getsInstance().fullName = row.fullname;
        }

        if (row.department != null) {
          MimeInfoMgr.getsInstance().department = row.department;
        }

        if (row.authority != null) {
          MimeInfoMgr.getsInstance().authority = row.authority;
        }
      }
    } catch (Exception e) {
      LogUtil.e(TAG, "updateMimeInfo() error", e);
    }

  }

  @Override
  public void showBitmap(Bitmap bitmap) {
    if (bitmap != null) {
      mIv.setImageBitmap(bitmap);
      MimeInfoMgr.getsInstance().userAvatarBmp = bitmap;
    }
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btn_detail_main_mine:
        if (mPage != null) {
          mPage.turnPage(MimeFragment.PAGE_DETAIL);
        }
        break;

      case R.id.tv_day_main_mine:
        notifyEvent(EVENT_TYPE_DAY);
        break;

      case R.id.tv_week_main_mine:
        notifyEvent(EVENT_TYPE_WEEK);
        break;

      case R.id.tv_mon_main_mine:
        notifyEvent(EVENT_TYPE_MON);
        break;

      case R.id.tv_back:
        Activity activity = getActivity();
        if (activity == null) {
          return;
        }
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.putExtra(LoginActivity.INTENT_KEY_BACK, LoginActivity.INTENT_VALUE_BACK);
        startActivity(intent);
        activity.finish();
        break;

      default:
        break;

    }
  }

  private void notifyEvent(String eventType) {
    if (mCurrentEventType.equals(eventType)) {
      return;
    }
    mCurrentEventType = eventType;
    MimeInfoMgr.getsInstance().eventType = eventType;

    mAlertQuantityRequest.type = mCurrentEventType;
    mOrderRequest.type = mCurrentEventType;
    if (mPresenter != null) {
      mPresenter.loadOrderTimes();
      mPresenter.loadWarrantyTimes();
    }

    mDayTv.setTextColor(Color.BLACK);
    mDayTv.setBackgroundColor(Color.WHITE);

    mWeekTv.setTextColor(Color.BLACK);
    mWeekTv.setBackgroundColor(Color.WHITE);

    mMonTv.setTextColor(Color.BLACK);
    mMonTv.setBackgroundColor(Color.WHITE);

    TextView tv = null;
    switch (eventType) {
      case EVENT_TYPE_WEEK:
        tv = mWeekTv;
        break;

      case EVENT_TYPE_MON:
        tv = mMonTv;
        break;

      default:
        tv = mDayTv;
        break;
    }
    tv.setTextColor(Color.WHITE);
    tv.setBackgroundColor(Color.BLACK);
  }

}
