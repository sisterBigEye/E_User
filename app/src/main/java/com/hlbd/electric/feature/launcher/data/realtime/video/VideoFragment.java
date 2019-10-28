package com.hlbd.electric.feature.launcher.data.realtime.video;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.hlbd.electric.R;
import com.hlbd.electric.base.BaseFragment;
import com.hlbd.electric.feature.launcher.data.realtime.video.list.VideoListActivity;
import com.hlbd.electric.util.LogUtil;
import com.hlbd.electric.util.ToastUtil;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class VideoFragment extends BaseFragment {

  private static final String TAG = "VideoFragment";

  private View mParentView;
  private EventHandler mHandler;
  private SurfaceView mVideoView;
  private MediaPlayer mPlayer;
  private SurfaceHolder mHolder;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    if (mParentView == null) {
      mParentView = inflater.inflate(R.layout.fragment_video, container, false);
    }
    initView();
    init();
    return mParentView;
  }

  private void init() {

  }

  private void initView() {
    mVideoView = mParentView.findViewById(R.id.sv_video);
    SurfaceHolder holder = mVideoView.getHolder();
    holder.addCallback(mCallback);
  }

  public void play(String url) {
    LogUtil.d(TAG, "play() url=" + url);
    if (url == null || TextUtils.isEmpty(url)) {
      return;
    }
    if (mPlayer != null) {
      mPlayer.release();
    }
    mPlayer = new MediaPlayer();
    try {
      mPlayer.setDataSource(getContext(), Uri.parse(url));
      mPlayer.prepare();
      mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
          mPlayer.setDisplay(mHolder);
          mPlayer.start();
          //mPlayer.setLooping(true);
        }
      });
    } catch (IOException e) {
      e.printStackTrace();
      LogUtil.e(TAG, "notifyVideoInfo() play error", e);
      ToastUtil.toast("播放失败");
    }
  }


  private SurfaceHolder.Callback mCallback = new SurfaceHolder.Callback() {

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
      mHolder = holder;
      LogUtil.d(TAG, "surfaceCreated()");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
      LogUtil.d(TAG, "surfaceChanged()");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
      LogUtil.d(TAG, "surfaceDestroyed()");
    }
  };

  @Override
  public void startTask() {
    super.startTask();
    startActivityForResult(new Intent(getActivity(), VideoListActivity.class), VideoListActivity.REQUEST_CODE);
  }

  private static class EventHandler extends Handler {

    private final WeakReference<VideoFragment> mRef;
    private static final int REQUEST = 1;

    private EventHandler(VideoFragment fragment) {
      mRef = new WeakReference<>(fragment);
    }

    @Override
    public void handleMessage(Message msg) {
      VideoFragment fragment = mRef.get();
      if (fragment == null) {
        return;
      }
      switch (msg.what) {
        case REQUEST:
          break;
        default:
          break;
      }
    }
  }


  public void stopTask() {
    stop();
  }

  public void stop() {
    if (mPlayer != null) {
      mPlayer.stop();
      mPlayer.release();
      mPlayer = null;
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == VideoListActivity.REQUEST_CODE && resultCode == VideoListActivity.RESULT_CODE) {
      String url = data.getStringExtra("value");
      if (url == null || TextUtils.isEmpty(url)) {
        return;
      }
      play(url);
    }

  }

  @Override
  public void onPause() {
    super.onPause();
    if (mVideoView != null) {
      mVideoView.setVisibility(View.INVISIBLE);
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    if (mVideoView != null) {
      mVideoView.setVisibility(View.VISIBLE);
    }
  }
}
