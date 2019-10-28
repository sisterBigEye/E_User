package com.hlbd.electric.feature.start;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.hlbd.electric.R;
import com.hlbd.electric.base.BaseActivity;
import com.hlbd.electric.feature.login.LoginActivity;

import java.lang.ref.WeakReference;

public class StartActivity extends BaseActivity implements View.OnClickListener {

  private static final int MSG_START = 1;
  private int mCount = 3;
  public static final int FLAG_AD = -10;
  public static final String KEY_FLAG_AD = "FLAG_AD";
  private StartHandler mHandler;
  private TextView mCountTv;
  private View mV;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mHandler = new StartHandler(this);
    mCountTv = findViewById(R.id.tv_count_start);
    mV = findViewById(R.id.ll_start);
    mV.setOnClickListener(this);
  }

  @Override
  protected void onResume() {
    super.onResume();
    mHandler.sendEmptyMessageDelayed(MSG_START, 0);
  }

  @Override
  protected int layoutId() {
    return R.layout.activity_start;
  }

  @Override
  protected String toolBarTitle() {
    return null;
  }

  @Override
  protected boolean isHideStateBar() {
    return true;
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.ll_start:
        mCount = FLAG_AD;
        Intent intent = new Intent(StartActivity.this, LoginActivity.class);
        intent.putExtra(KEY_FLAG_AD, FLAG_AD);
        startActivity(intent);
        mHandler.removeCallbacksAndMessages(null);
        finish();
        break;
    }

  }

  private static class StartHandler extends Handler {
    private final WeakReference<StartActivity> mWeak;

    public StartHandler(StartActivity activity) {
      mWeak = new WeakReference<>(activity);
    }

    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      StartActivity activity = mWeak.get();
      if (activity == null) {
        return;
      }
      switch (msg.what) {
        case MSG_START:
          if (activity.mCount <= FLAG_AD) {
            return;
          }
          if (activity.mCount < 0) {
            activity.startActivity(new Intent(activity, LoginActivity.class));
            activity.finish();
            return;
          }
          sendEmptyMessageDelayed(MSG_START, 1000);
          activity.show(activity.mCount);
          activity.mCount--;
          break;
      }
    }
  }

  private void show(int count) {
    if (mCountTv != null) {
      mCountTv.setText(String.valueOf(count));
    }
  }
}
