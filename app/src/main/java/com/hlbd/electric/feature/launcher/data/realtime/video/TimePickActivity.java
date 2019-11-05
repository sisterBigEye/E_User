package com.hlbd.electric.feature.launcher.data.realtime.video;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.hlbd.electric.R;
import com.hlbd.electric.util.LogUtil;
import com.hlbd.electric.util.TimeUtil;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.config.DefaultConfig;
import com.jzxiang.pickerview.listener.OnDateSetListener;

public class TimePickActivity extends AppCompatActivity implements View.OnClickListener, OnDateSetListener {

  public static final int REQUEST_CODE = 11;
  public static final int RESULT_CODE = 111;
  private TextView mStartTimeTv;
  private TextView mEndTimeTv;
  private long mStartTime;
  private boolean isStartTime;
  private long mEndTime;

  private TimePickerDialog mStartTimePicker;

  public static final String EXTRA_KEY_START_TIME = "start";
  public static final String EXTRA_KEY_END_TIME = "end";

  private static final String TAG = "TimePickActivity";

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_time_pick);
    initView();
    init();
  }

  private void init() {
    mStartTimePicker = new TimePickerDialog.Builder()
            //设置类型
            .setType(DefaultConfig.TYPE)
            //设置选择时间监听回调
            .setCallBack(this)
            //设置标题
            .setTitleStringId("请选择时间")
            //设置时间
            .setSelectorMillseconds(System.currentTimeMillis())
            //设置颜色
            .setThemeColor(getResources().getColor(R.color.colorPrimary))
            //设置 字体大小
            .setWheelItemTextSize(15)
            //完毕
            .build();
  }

  private void initView() {
    mStartTime = System.currentTimeMillis();
    mEndTime = mStartTime;
    String time = TimeUtil.getDateTime(mEndTime);

    mStartTimeTv = findViewById(R.id.tv_start_time_pick);
    mStartTimeTv.setOnClickListener(this);
    mStartTimeTv.setText(time);

    mEndTimeTv = findViewById(R.id.tv_end_time_pick);
    mEndTimeTv.setOnClickListener(this);
    mEndTimeTv.setText(time);

    findViewById(R.id.btn_sure_time_pick).setOnClickListener(this);
    findViewById(R.id.iv_cencel_time_pick).setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.tv_start_time_pick:
        isStartTime = true;
        mStartTimePicker.show(getSupportFragmentManager(), "起始时间");
        break;

      case R.id.tv_end_time_pick:
        isStartTime = false;
        mStartTimePicker.show(getSupportFragmentManager(), "结束时间");
        break;

      case R.id.btn_sure_time_pick:
        Intent i = new Intent();
        i.putExtra(EXTRA_KEY_START_TIME, mStartTime);
        i.putExtra(EXTRA_KEY_END_TIME, mEndTime);
        setResult(RESULT_CODE, i);
        finish();
        break;

      case R.id.iv_cencel_time_pick:
        finish();
        break;

      default:
        break;
    }
  }

  @Override
  public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
    String time = TimeUtil.getDateTime(millseconds);
    LogUtil.d(TAG, "onDateSet() millseconds=" + millseconds + ", time=" + time);
    if (isStartTime) {
      mStartTime = millseconds;
      mStartTimeTv.setText(time);
    } else {
      mEndTime = millseconds;
      mEndTimeTv.setText(time);
    }
  }
}
