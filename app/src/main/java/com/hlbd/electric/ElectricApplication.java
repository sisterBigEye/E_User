package com.hlbd.electric;

import android.app.Application;

import com.hlbd.electric.util.LogUtil;
import com.hlbd.electric.util.PxUtil;
import com.hlbd.electric.util.SharedPreferencesUtil;
import com.hlbd.electric.util.ToastUtil;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

public class ElectricApplication extends Application {

  private static final String TAG = "ElectricApplication_User";
  public static TaskHandler sHandler;

  private static final String Umeng_Appkey = "5d947dc1570df3cb8d00088b";
  private static final String Umeng_Message_Secret = "7bed4ec382bb014fc55a822a21d60f4c";
  public static String gDeviceToken = null;

  @Override
  public void onCreate() {
    super.onCreate();
    sHandler = new TaskHandler(getMainLooper());
    ToastUtil.init(this);
    PxUtil.init(this);
    SharedPreferencesUtil.init(this);
    initUMeng();
  }

  private void initUMeng() {
    UMConfigure.init(this, Umeng_Appkey,
            "Umeng",
            UMConfigure.DEVICE_TYPE_PHONE,
            Umeng_Message_Secret);

    //获取消息推送代理示例
    PushAgent mPushAgent = PushAgent.getInstance(this);
    //注册推送服务，每次调用register方法都会回调该接口
    mPushAgent.register(new IUmengRegisterCallback() {

      @Override
      public void onSuccess(String deviceToken) {
        //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
        LogUtil.i(TAG, "initUMeng() onSuccess 注册成功：deviceToken：-------->  " + deviceToken);
        gDeviceToken = deviceToken;
      }

      @Override
      public void onFailure(String s, String s1) {
        LogUtil.e(TAG, "initUMeng() onFailure 注册失败：-------->  " + "s:" + s + ",s1:" + s1);
        gDeviceToken = null;
      }
    });
  }
}
