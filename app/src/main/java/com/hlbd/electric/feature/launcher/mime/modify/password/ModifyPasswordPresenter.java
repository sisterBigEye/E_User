package com.hlbd.electric.feature.launcher.mime.modify.password;

import com.hlbd.electric.api.Callback;
import com.hlbd.electric.api.ITask;
import com.hlbd.electric.feature.launcher.mime.modify.password.data.ModifyPasswordData;
import com.hlbd.electric.task.RequestDataTask;
import com.hlbd.electric.util.LogUtil;

public class ModifyPasswordPresenter implements ModifyPasswordContract.Presenter {
  private static final String TAG = "ModifyPasswordPresenter";
  private ModifyPasswordContract.View view;
  private ModifyPasswordRequest request;
  private ITask<ModifyPasswordData> task;

  ModifyPasswordPresenter(ModifyPasswordContract.View view, ModifyPasswordRequest request) {
    this.view = view;
    this.request = request;
    this.view.setPersonal(this);
  }

  @Override
  public void start() {
    modifyPassword();
  }

  @Override
  public void modifyPassword() {
    if (view == null || request == null) {
      LogUtil.e(TAG, "modifyPassword() --- view is null");
      return;
    }
    if (task == null) {
      task = new RequestDataTask<>(ModifyPasswordData.class);
    }
    LogUtil.d(TAG, "modifyPassword() --- map = " + request.toString());
    task.startTask(request, new Callback<ModifyPasswordData>() {

      @Override
      public void result(ModifyPasswordData data) {
        view.updatePassword(data);
      }
    });
  }
}
