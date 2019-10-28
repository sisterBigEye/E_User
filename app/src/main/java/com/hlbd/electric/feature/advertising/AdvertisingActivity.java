package com.hlbd.electric.feature.advertising;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hlbd.electric.R;
import com.hlbd.electric.util.BitmapUtil;

public class AdvertisingActivity extends AppCompatActivity implements View.OnClickListener {

  private boolean mHavePermission;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_ad);
    initView();
  }

  private void initView() {

    findViewById(R.id.btn_save_ad).setOnClickListener(this);
    findViewById(R.id.iv_cencel_ad).setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btn_save_ad:
        if (haveWritePermission()) {
          BitmapUtil.saveImage(this, R.drawable.ic_qr_code);
          finish();
        }
        break;

      case R.id.iv_cencel_ad:
        finish();
        break;

      default:
        break;
    }
  }

  private boolean haveWritePermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      int state = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
      if (state != PackageManager.PERMISSION_GRANTED) {
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        return false;
      }
    }
    return true;
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    for (int result : grantResults) {
      if (result == PackageManager.PERMISSION_DENIED) {
        return;
      }
    }
    BitmapUtil.saveImage(this, R.drawable.ic_qr_code);
    finish();
  }
}
