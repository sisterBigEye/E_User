package com.hlbd.electric.feature.launcher.mime.main;


import android.graphics.Bitmap;

import com.hlbd.electric.base.BasePresenter;
import com.hlbd.electric.base.BaseView;
import com.hlbd.electric.feature.launcher.mime.main.data.MimeInfoData;
import com.hlbd.electric.feature.launcher.mime.main.data.MimeOrderData;
import com.hlbd.electric.feature.launcher.mime.main.data.MimeAlertQuantityData;

public class MimeMainContract {

  public interface Presenter extends BasePresenter {

    void loadOrderTimes();

    void loadWarrantyTimes();

    void loadMimeInfo();

    void loadBmp();

  }

  public interface View extends BaseView<Presenter> {

    void updateMimeOrderData(MimeOrderData data);

    void updateAlertQuantityData(MimeAlertQuantityData data);

    void updateMimeInfo(MimeInfoData data);

    void showBitmap(Bitmap bitmap);

  }

}
