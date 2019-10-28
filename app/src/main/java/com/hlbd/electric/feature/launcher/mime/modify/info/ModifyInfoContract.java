package com.hlbd.electric.feature.launcher.mime.modify.info;


import com.hlbd.electric.base.BasePresenter;
import com.hlbd.electric.base.BaseView;
import com.hlbd.electric.feature.launcher.mime.modify.info.data.ModifyAvatarResult;
import com.hlbd.electric.feature.launcher.mime.modify.info.data.ModifyInfoData;

class ModifyInfoContract {

  interface Presenter extends BasePresenter {

    void modifyDetailInfo();

    void modifyAvatar();

  }

  interface View extends BaseView<Presenter> {

    void updateModifyInfo(ModifyInfoData data);

    void updateAvatarResult(ModifyAvatarResult result);

  }

}
