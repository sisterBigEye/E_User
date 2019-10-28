package com.hlbd.electric.feature.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hlbd.electric.IConstant;
import com.hlbd.electric.R;
import com.hlbd.electric.api.HttpApi;
import com.hlbd.electric.base.BaseActivity;
import com.hlbd.electric.feature.advertising.AdvertisingActivity;
import com.hlbd.electric.feature.launcher.LauncherActivity;
import com.hlbd.electric.feature.start.StartActivity;
import com.hlbd.electric.util.LogUtil;
import com.hlbd.electric.util.SharedPreferencesUtil;
import com.hlbd.electric.util.ToastUtil;
import com.hlbd.electric.widget.WaitBar;

import java.util.Arrays;

;

public class LoginActivity extends BaseActivity implements LoginContract.View<UserInfo>, View.OnClickListener {

  private static final String TAG = "LoginActivity";
  private LoginContract.Presenter presenter;
  private EditText userEt;
  private EditText pwdEt;
  private boolean isLogging = false;
  private LoginRequest request;
  private WaitBar waitBar;
  public static final String INTENT_KEY_BACK = "back";
  public static final int INTENT_VALUE_BACK = 5;
  private int mCount = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    init();
  }

  @Override
  protected boolean isHideStateBar() {
    return true;
  }

  private void init() {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
      int internetPermission = checkSelfPermission(Manifest.permission.INTERNET);
      LogUtil.d(TAG, "init() --- internetPermission = " + internetPermission);
      if (internetPermission == PackageManager.PERMISSION_DENIED) {
        requestPermissions(new String[]{Manifest.permission.INTERNET}, 100);
      }
    }
    String httpUrl = HttpApi.getDefaultUrl();
    //HttpApi.setDefaultUrl(httpUrl);

    userEt = findViewById(R.id.login_name_et);
    pwdEt = findViewById(R.id.login_pwd_et);
    String userName = (String) SharedPreferencesUtil.getData(IConstant.USER_NAME, "");
    HttpApi.setUserName(userName);
    userEt.setText(userName);
    String password = (String) SharedPreferencesUtil.getData(IConstant.PASS_WORD, "");
    pwdEt.setText(password);
    Button loginBtn = findViewById(R.id.login_login_btn);
    loginBtn.setOnClickListener(this);

    request = new LoginRequest();
    request.username = userName;
    request.password = password;
    request.url = "Baoding_UserAccountManage/Services/VerifyUser?";
    presenter = new LoginPresenter(this, request);
    Intent intent = getIntent();
    if (intent != null) {
      int value = intent.getIntExtra(StartActivity.KEY_FLAG_AD, 0);
      if (value == StartActivity.FLAG_AD) {
        startActivity(new Intent(LoginActivity.this, AdvertisingActivity.class));
        return;
      }
      int valueBack = intent.getIntExtra(INTENT_KEY_BACK, 0);
      if (valueBack == INTENT_VALUE_BACK) {
        return;
      }
    }
    if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)) {
      presenter.start();
    }
  }

  @Override
  protected int layoutId() {
    return R.layout.activity_login;
  }

  @Override
  protected String toolBarTitle() {
    return "";
  }

  /*@Override
  protected String toolBarTitle() {
    return "登录";
  }*/

  @Override
  public void showLoading() {
    if (waitBar == null) {
      waitBar = new WaitBar(this);
    }
    waitBar.show();
  }

  @Override
  public void dismissLoading() {
    if (waitBar == null) {
      return;
    }
    waitBar.dismiss();
  }

  @Override
  public void loginSuccess(UserInfo info) {
    isLogging = false;
    if (info == null) {
      LogUtil.e(TAG, "loginFailed() info = info");
      ToastUtil.toast("登录失败，请重试");
      return;
    }
    LogUtil.d(TAG, "loginResult() --- info = " + info.toString());
    try {
      for (UserInfo.Row row : info.rows) {
        if (row.result) {
          Intent intent = new Intent(this, LauncherActivity.class);
          startActivity(intent);
          finish();
          return;
        }
      }
    } catch (Exception e) {
      LogUtil.e(TAG, "loginFailed()", e);
    }
    ToastUtil.toast("登录失败，请重试");

  }

  @Override
  public void setPersonal(LoginContract.Presenter p) {
    this.presenter = p;
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.login_login_btn:
        //ToastUtil.toast("这是第" + mCount++ + "次");
        login();
        break;

      default:
        break;
    }
  }

  private void login() {
    userEt.clearFocus();
    pwdEt.clearFocus();
    if (isLogging) {
      LogUtil.d(TAG, "login() --- 正在登陆");
      ToastUtil.toast("正在登陆");
      return;
    }
    request.username = userEt.getText().toString().trim();
    if (TextUtils.isEmpty(request.username)) {
      ToastUtil.toast("请输入用户名");
      return;
    }
    request.password = pwdEt.getText().toString().trim();
    if (TextUtils.isEmpty(request.password)) {
      ToastUtil.toast("请输入密码");
      return;
    }
    isLogging = true;
    SharedPreferencesUtil.putData(IConstant.USER_NAME, request.username);
    SharedPreferencesUtil.putData(IConstant.PASS_WORD, request.password);
    HttpApi.setUserName(request.username);
    presenter.start();
  }


  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    LogUtil.d(TAG, "onRequestPermissionsResult() --- permissions=" + Arrays.toString(permissions));
  }
}
