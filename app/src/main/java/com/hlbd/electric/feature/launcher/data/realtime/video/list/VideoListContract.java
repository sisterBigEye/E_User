package com.hlbd.electric.feature.launcher.data.realtime.video.list;


import com.hlbd.electric.base.BasePresenter;
import com.hlbd.electric.base.BaseView;

class VideoListContract {

    interface Presenter extends BasePresenter {

        void loadVideoInfo();

    }

    interface View<T> extends BaseView<Presenter> {

        void showLoading();

        void dismissLoading();

        void notifyVideoInfo(T t);

    }

}
