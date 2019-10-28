package com.hlbd.electric.feature.launcher.data.realtime;


import android.graphics.Bitmap;

import com.hlbd.electric.base.BasePresenter;
import com.hlbd.electric.base.BaseView;

public class RealtimeDataContract {

    public interface Presenter extends BasePresenter {

        void loadBitmap();

    }

    public interface View extends BaseView<Presenter> {

        void showLoading();

        void dismissLoading();

        void showBitmap(Bitmap bitmap);

    }

}
