package com.hlbd.electric.feature.launcher.mime.detail;

import android.graphics.Bitmap;
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
import com.hlbd.electric.feature.launcher.mime.MimeFragment;
import com.hlbd.electric.feature.launcher.mime.MimeInfoMgr;
import com.hlbd.electric.feature.launcher.mime.main.MimeMainContract;
import com.hlbd.electric.feature.launcher.mime.main.MimeMainPresenter;
import com.hlbd.electric.feature.launcher.mime.main.data.MimeAlertQuantityData;
import com.hlbd.electric.feature.launcher.mime.main.data.MimeInfoData;
import com.hlbd.electric.feature.launcher.mime.main.data.MimeOrderData;
import com.hlbd.electric.feature.launcher.mime.main.request.MimeInfoRequest;
import com.hlbd.electric.util.LogUtil;

public class MimeDetailFragment extends BaseFragment implements View.OnClickListener, MimeMainContract.View {

  private static final String TAG = "MimeDetailFragment";
  private ImageView mIv;
  private TextView mNameTv;
  private TextView mPhoneNumberTv;
  private TextView mCityTv;
  private TextView mAddressTv;
  private MimeInfoRequest mMimeInfoRequest;
  private MimeMainContract.Presenter mPresenter;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    if (mBaseView == null) {
      mBaseView = inflater.inflate(R.layout.fragment_mime_detail, container, false);
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
    mTitleTv.setText("用户资料");
    mBackIv.setVisibility(View.VISIBLE);
    mBackIv.setOnClickListener(this);

    mIv = mBaseView.findViewById(R.id.iv_image_mime_detail);
    mNameTv = mBaseView.findViewById(R.id.tv_name_mime_detail);
    mPhoneNumberTv = mBaseView.findViewById(R.id.tv_number_mime_detail);
    mCityTv = mBaseView.findViewById(R.id.tv_city_mime_detail);
    mAddressTv = mBaseView.findViewById(R.id.tv_adress_mime_detail);

    mBaseView.findViewById(R.id.btn_modify_info_mime_detail).setOnClickListener(this);
    mBaseView.findViewById(R.id.btn_modify_password_mime_detail).setOnClickListener(this);

  }

  private void init() {
    mMimeInfoRequest = new MimeInfoRequest();
    mMimeInfoRequest.username = HttpApi.getUserName();
    mMimeInfoRequest.url = "Baoding_UserAccountManage/Services/SelectDataSQLByUsername?";

    mPresenter = new MimeMainPresenter(this, null, null,
            mMimeInfoRequest, null);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btn_detail_main_mine:
        if (mPage != null) {
          mPage.turnPage(MimeFragment.PAGE_DETAIL);
        }
        break;
      case R.id.iv_back:
        if (mPage != null) {
          mPage.turnPage(MimeFragment.PAGE_MAIN);
        }
        break;

      case R.id.btn_modify_info_mime_detail:
        if (mPage != null) {
          mPage.turnPage(MimeFragment.PAGE_MODIFY_INFO);
        }
        break;

      case R.id.btn_modify_password_mime_detail:
        if (mPage != null) {
          mPage.turnPage(MimeFragment.PAGE_MODIFY_PASSWORD);
        }
        break;

      default:
        break;
    }
  }

  @Override
  public void startTask() {
    super.startTask();
  }

  @Override
  public void updateMimeOrderData(MimeOrderData data) {
    // DO NOTHING
  }

  @Override
  public void updateAlertQuantityData(MimeAlertQuantityData data) {
    // DO NOTHING
  }

  @Override
  public void updateMimeInfo(MimeInfoData data) {
    LogUtil.d(TAG, "updateMimeInfo() data=" + data);
    try {
      for (MimeInfoData.Row row : data.rows) {
        if (row.username != null) {
          MimeInfoMgr.getsInstance().userName = row.username;
        }
        if (row.phonenumber != null) {
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

    Bitmap bmp = MimeInfoMgr.getsInstance().userAvatarBmp;
    if (bmp != null) {
      mIv.setImageBitmap(bmp);
    }

    String name = "用户姓名:    " + MimeInfoMgr.getsInstance().fullName;
    mNameTv.setText(name);

    String number = "联系方式:    " + MimeInfoMgr.getsInstance().phoneNumber;
    mPhoneNumberTv.setText(number);


    String city = "所在城市:    " + MimeInfoMgr.getsInstance().city;
    mCityTv.setText(city);


    String address = "用户地址:    " + MimeInfoMgr.getsInstance().address;
    mAddressTv.setText(address);
  }

  @Override
  public void showBitmap(Bitmap bitmap) {
    // DO NOTHING
  }

  @Override
  public void setPersonal(MimeMainContract.Presenter p) {
    this.mPresenter = p;
  }

  @Override
  public void updateTask() {
    mPresenter.loadMimeInfo();
  }
}
