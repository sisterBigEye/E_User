package com.hlbd.electric.feature.launcher.mime.modify.info;

import com.hlbd.electric.api.Callback;
import com.hlbd.electric.api.ITask;
import com.hlbd.electric.feature.launcher.mime.modify.info.data.ModifyAvatarResult;
import com.hlbd.electric.feature.launcher.mime.modify.info.data.ModifyInfoData;
import com.hlbd.electric.task.RequestDataTask;
import com.hlbd.electric.util.LogUtil;

public class ModifyInfoPresenter implements ModifyInfoContract.Presenter {
  private static final String TAG = "ModifyInfoPresenter";
  private ModifyInfoContract.View view;
  private ModifyInfoRequest infoRequest;
  private ITask<ModifyInfoData> infoTask;

  private ModifyAvatarRequest avatarRequest;
  private ITask<ModifyAvatarResult> avatarTask;

  ModifyInfoPresenter(ModifyInfoContract.View view, ModifyInfoRequest infoRequest, ModifyAvatarRequest avatarRequest) {
    this.view = view;
    this.infoRequest = infoRequest;
    this.avatarRequest = avatarRequest;
    this.view.setPersonal(this);
  }

  @Override
  public void start() {
    modifyDetailInfo();
    modifyAvatar();
  }


  @Override
  public void modifyDetailInfo() {
    if (view == null || infoRequest == null) {
      LogUtil.e(TAG, "modifyDetailInfo() --- view is null");
      return;
    }
    if (infoTask == null) {
      infoTask = new RequestDataTask<>(ModifyInfoData.class);
    }
    LogUtil.d(TAG, "modifyDetailInfo() --- map = " + infoRequest.toString());
    infoTask.startTask(infoRequest, new Callback<ModifyInfoData>() {

      @Override
      public void result(ModifyInfoData data) {
        view.updateModifyInfo(data);
      }
    });
  }

  @Override
  public void modifyAvatar() {
    if (avatarRequest == null || avatarRequest.userAvatar == null) {
      LogUtil.e(TAG, "modifyAvatar() --- param is null");
      if (view != null) {
        view.updateAvatarResult(null);
      }
      return;
    }
    if (avatarTask == null) {
      avatarTask = new RequestDataTask<>(ModifyAvatarResult.class);
    }
    LogUtil.d(TAG, "modifyAvatar() --- map = " + avatarRequest.toString());
    avatarTask.startTask(avatarRequest, new Callback<ModifyAvatarResult>() {

      @Override
      public void result(ModifyAvatarResult result) {
        if (view == null) {
          return;
        }
        view.updateAvatarResult(result);
      }
    });
  }
}
