package com.hlbd.electric.feature.login;

import com.hlbd.electric.api.Callback;
import com.hlbd.electric.api.ITask;
import com.hlbd.electric.task.RequestDataTask;
import com.hlbd.electric.util.LogUtil;

public class LoginPresenter implements LoginContract.Presenter {
    private static final String TAG = "LoginPresenter";
    private LoginContract.View<UserInfo> view;
    private LoginRequest request;
    private ITask<UserInfo> loginTask;

    LoginPresenter(LoginContract.View<UserInfo> view, LoginRequest request) {
        this.view = view;
        this.request = request;
        this.view.setPersonal(this);
    }

    @Override
    public void login() {

        if (view == null) {
            LogUtil.e(TAG, "login() --- view is null");
            return;
        }
        if (request == null) {
            LogUtil.e(TAG, "login() --- startTask is null");
            view.loginSuccess(null);
            return;
        }
        view.showLoading();

        if (loginTask == null) {
            loginTask = new RequestDataTask<>(UserInfo.class);
        }
        LogUtil.d(TAG, "login() --- map = " + request.toString());
        loginTask.startTask(request, new Callback<UserInfo>() {

            @Override
            public void result(UserInfo userInfo) {
                view.dismissLoading();
                view.loginSuccess(userInfo);
            }
        });
    }

    @Override
    public void start() {
        login();
    }
}
