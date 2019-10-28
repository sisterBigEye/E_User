package com.hlbd.electric.feature.login;


import com.hlbd.electric.base.BasePresenter;
import com.hlbd.electric.base.BaseView;

class LoginContract {

    interface Presenter extends BasePresenter {

        void login();

        void start();


    }

    interface View<T> extends BaseView<Presenter> {

        void showLoading();

        void dismissLoading();

        void loginSuccess(T t);

    }

}
