package com.hlbd.electric.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hlbd.electric.R;
import com.hlbd.electric.feature.launcher.mime.IPage;
import com.hlbd.electric.model.Information;
import com.hlbd.electric.util.LogUtil;
import com.hlbd.electric.util.ToastUtil;

import java.lang.ref.WeakReference;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class BaseFragment extends Fragment {

  protected View mBaseView;
  protected TextView mTitleTv;
  protected ImageView mBackIv;
  protected IPage mPage;
  protected EventHandler mHandler;
  private final String tag = "BaseFragment_" + getClass().getSimpleName();
  private boolean isReadyStart = false;

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    if (mBaseView != null) {
      mTitleTv = mBaseView.findViewById(R.id.tv_title);
      mBackIv = mBaseView.findViewById(R.id.iv_back);
    }
  }

  public void startTask() {
    startTask(0);
  }

  public void startTask(long updateTime) {
    if (isReadyStart) {
      LogUtil.d(tag, "startTask() already start");
      return;
    }
    isReadyStart = true;
    LogUtil.d(tag, "startTask() updateTime=" + updateTime);
    if (mHandler == null) {
      mHandler = new EventHandler(this);
    }
    mHandler.setUpdateTime(updateTime);
    mHandler.sendEmptyMessage(EventHandler.MSG_START);
  }

  public void updateTask() {
    LogUtil.d(tag, "updateTask()");
    ToastUtil.toast("开始刷新页面");
  }

  public void stopTask() {
    isReadyStart = false;
    LogUtil.d(tag, "stopTask()");
    if (mHandler != null) {
      mHandler.sendEmptyMessage(EventHandler.MSG_STOP);
    }
  }

  protected String getName(List<Information.Row> rows) {
    String name = null;
    try {
      name = rows.get(0).name;
    } catch (Exception e) {
      LogUtil.e(TAG, "getName() error", e);
    }
    return name;
  }

  public void setPage(IPage page) {
    this.mPage = page;
  }

  public static class EventHandler extends Handler {

    private final WeakReference<BaseFragment> mRef;
    public static final int MSG_START = 1;
    public static final int MSG_UPDATE = 2;
    public static final int MSG_STOP = 3;
    private boolean isConnute = false;
    private long mUpdateTime = 0;

    public EventHandler(BaseFragment fragment) {
      mRef = new WeakReference<>(fragment);
    }

    public void setUpdateTime(long updateTime) {
      mUpdateTime = updateTime;
    }

    @Override
    public void handleMessage(Message msg) {
      BaseFragment fragment = mRef.get();
      if (fragment == null) {
        return;
      }
      switch (msg.what) {
        case MSG_START:
          fragment.updateTask();
          isConnute = true;
          if (mUpdateTime > 0) {
            sendEmptyMessageDelayed(MSG_UPDATE, mUpdateTime);
          }
          LogUtil.d(fragment.tag, "handleMessage() MSG_START, mUpdateTime=" + mUpdateTime);
          break;
        case MSG_UPDATE:
          LogUtil.d(fragment.tag, "handleMessage() MSG_UPDATE, mUpdateTime="
                  + mUpdateTime + ", isConnute=" + isConnute);
          if (isConnute && mUpdateTime > 0) {
            fragment.updateTask();
            sendEmptyMessageDelayed(MSG_UPDATE, mUpdateTime);
          }
          break;
        case MSG_STOP:
          LogUtil.d(fragment.tag, "handleMessage() MSG_STOP, mUpdateTime=" + mUpdateTime);
          isConnute = false;
          removeCallbacksAndMessages(null);
          break;
        default:
          break;
      }
    }
  }
}
