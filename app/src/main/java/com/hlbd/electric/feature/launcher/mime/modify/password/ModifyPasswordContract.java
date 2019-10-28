package com.hlbd.electric.feature.launcher.mime.modify.password;


import com.hlbd.electric.base.BasePresenter;
import com.hlbd.electric.base.BaseView;
import com.hlbd.electric.feature.launcher.mime.modify.password.data.ModifyPasswordData;

class ModifyPasswordContract {

  interface Presenter extends BasePresenter {

    void modifyPassword();

  }

  interface View extends BaseView<Presenter> {

    void updatePassword(ModifyPasswordData data);

  }

}
