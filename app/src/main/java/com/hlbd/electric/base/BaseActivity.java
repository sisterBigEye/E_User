package com.hlbd.electric.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.hlbd.electric.R;
import com.hlbd.electric.util.LogUtil;
import com.umeng.message.PushAgent;

/**
 * Created by YySleep on 2018/7/22.
 *
 * @author YySleep
 */

public abstract class BaseActivity extends AppCompatActivity {
  private final static String TAG = "BaseActivity";
  protected Toolbar mToolbar;
  private String GATENAME;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    handleStateBar();
    setContentView(layoutId());
    Intent intent = getIntent();
    GATENAME = intent.getStringExtra("GATENAME");
    initToolbar();

    PushAgent.getInstance(getApplicationContext()).onAppStart();
  }

  @LayoutRes
  protected abstract int layoutId();

  private void initToolbar() {
    mToolbar = findViewById(R.id.toolbar);
    if (mToolbar == null) {
      LogUtil.e(TAG, "[initToolbar] you should include toolbar in your activity xml" + this);
      return;
    }
    setSupportActionBar(mToolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      // 展示 back 箭头
      actionBar.setDisplayHomeAsUpEnabled(backIconState());
      // 不展示 原本的 title
      actionBar.setDisplayShowTitleEnabled(false);
    }
    String title = null;
    if (GATENAME != null) {
      title = toolBarTitle() + "  " + GATENAME;
    } else {
      title = toolBarTitle();
    }
    mToolbar.setTitle(title);
    mToolbar.setTitleTextColor(Color.WHITE);

  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  protected abstract String toolBarTitle();

  protected boolean backIconState() {
    return true;
  }

  protected void hideStateBar() {
    // 隐藏状态栏 begin
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
  }

  private void handleStateBar() {
    if (isHideStateBar()) {
      hideStateBar();
    } else {
      int statusColor = getStatusBarColor();
      if (statusColor >= 0) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(getResources().getColor(statusColor));
      }
    }
  }

  protected boolean isHideStateBar() {
    return false;
  }

  protected int getStatusBarColor() {
    return -1;
  }

}
