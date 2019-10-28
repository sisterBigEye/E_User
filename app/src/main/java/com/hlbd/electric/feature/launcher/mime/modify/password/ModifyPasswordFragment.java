package com.hlbd.electric.feature.launcher.mime.modify.password;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.hlbd.electric.IConstant;
import com.hlbd.electric.R;
import com.hlbd.electric.api.HttpApi;
import com.hlbd.electric.base.BaseFragment;
import com.hlbd.electric.feature.launcher.mime.MimeFragment;
import com.hlbd.electric.feature.launcher.mime.MimeInfoMgr;
import com.hlbd.electric.feature.launcher.mime.modify.password.data.ModifyPasswordData;
import com.hlbd.electric.util.LogUtil;
import com.hlbd.electric.util.SharedPreferencesUtil;
import com.hlbd.electric.util.ToastUtil;

import java.lang.ref.WeakReference;

public class ModifyPasswordFragment extends BaseFragment implements ModifyPasswordContract.View, View.OnClickListener {

  private static final String TAG = "ModifyPasswordFragment";
  private ModifyPasswordContract.Presenter mPresenter;
  private EventHandler mHandler;
  private ModifyPasswordRequest request;

  private ImageView mIv;
  private EditText mPasswordEt;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    if (mBaseView == null) {
      mBaseView = inflater.inflate(R.layout.fragment_mime_modiify_password, container, false);
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
  public void setPersonal(ModifyPasswordContract.Presenter p) {
    mPresenter = p;
  }

  public void startTask() {
    Bitmap bmp = MimeInfoMgr.getsInstance().userAvatarBmp;
    if (bmp != null) {
      mIv.setImageBitmap(bmp);
    }

    String name = MimeInfoMgr.getsInstance().passwordStr;
    mPasswordEt.setText(name);

  }

  private void init() {
    mHandler = new EventHandler(this);

    request = new ModifyPasswordRequest();
    request.username = HttpApi.getUserName();
    request.url = "Baoding_UserAccountManage/Services/UpdateUserPasswordByUser?";

    mPresenter = new ModifyPasswordPresenter(this, request);
  }

  private void initView() {
    mTitleTv.setText("修改密码");
    mBackIv.setVisibility(View.VISIBLE);
    mBackIv.setOnClickListener(this);

    mBaseView.findViewById(R.id.btn_sure_mime_password_modify).setOnClickListener(this);

    mIv = mBaseView.findViewById(R.id.iv_image_mime_password_modify);
    mPasswordEt = mBaseView.findViewById(R.id.et_name_mime_password_modify);
  }

  @Override
  public void updatePassword(ModifyPasswordData data) {
    try {
      for (ModifyPasswordData.Row row : data.rows) {
        if (row.result != null || TextUtils.isEmpty(row.result)) {
          LogUtil.d(TAG, "updatePassword() row.result=" + row.result);
          break;
        }
      }
    } catch (Exception e) {
      LogUtil.e(TAG, "updatePassword() error", e);
      ToastUtil.toast("修改失败，请重试");
      return;
    }
    LogUtil.d(TAG, "updatePassword() 修改成功");
    ToastUtil.toast("修改成功");
    MimeInfoMgr.getsInstance().passwordStr = request.password;
    SharedPreferencesUtil.putData(IConstant.PASS_WORD, request.password);
    if (mPage != null) {
      mPage.turnPage(MimeFragment.PAGE_DETAIL);
    }
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.iv_back:
        if (mPage != null) {
          mPage.turnPage(MimeFragment.PAGE_DETAIL);
        }
        break;
      case R.id.btn_sure_mime_password_modify:
        String password = mPasswordEt.getText().toString().trim();
        if (password.equals(MimeInfoMgr.getsInstance().passwordStr)) {
          ToastUtil.toast("请输入不同的密码");
          return;
        }
        if (TextUtils.isEmpty(password)) {
          ToastUtil.toast("密码不可以为空");
          return;
        }
        request.password = password;
        mPresenter.start();
        break;
    }
  }

  private static class EventHandler extends Handler {

    private final WeakReference<ModifyPasswordFragment> mRef;
    private static final int REQUEST = 1;

    private EventHandler(ModifyPasswordFragment fragment) {
      mRef = new WeakReference<>(fragment);
    }

    @Override
    public void handleMessage(Message msg) {
      ModifyPasswordFragment fragment = mRef.get();
      if (fragment == null) {
        return;
      }
      switch (msg.what) {
        case REQUEST:
          break;
        default:
          break;
      }
    }
  }
}
