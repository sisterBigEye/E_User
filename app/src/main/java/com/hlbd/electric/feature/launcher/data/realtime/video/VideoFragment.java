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
import android.webkit.WebView;

import com.hlbd.electric.R;
import com.hlbd.electric.base.BaseFragment;
import com.hlbd.electric.feature.launcher.data.realtime.video.list.VideoListActivity;
import com.hlbd.electric.util.LogUtil;
import com.hlbd.electric.util.ToastUtil;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class VideoFragment extends BaseFragment implements View.OnClickListener, VideoContract.View {

  private static final String TAG = "VideoFragment";

  private View mParentView;
  private EventHandler mHandler;
  private SurfaceView mVideoView;
  private WebView mVideoWv;
  private MediaPlayer mPlayer;
  private SurfaceHolder mHolder;
  private View mControlV;
  private String mRawUrl;
  private String mCurrentCameraType;
  private String lastUrl = "";

  private VideoPresenter mPresenter;
  private VideoControlRequest mControlRequest;
  private VideoChannelRequest mChannelRequest;

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
    mControlRequest = new VideoControlRequest();
    mControlRequest.speed = 0;
    mControlRequest.url = "Baoding_VideoThing/Services/CameraControll?";

    mChannelRequest = new VideoChannelRequest();
    mChannelRequest.url = "Baoding_VideoThing/Services/SelectVideoDserialAndChannelNoByspecificname?";
    mPresenter = new VideoPresenter(this, mControlRequest, mChannelRequest);
  }

  private void initView() {
    //mVideoView = mParentView.findViewById(R.id.sv_video);
    mControlV = mParentView.findViewById(R.id.ll_control_video);
    mVideoWv = mParentView.findViewById(R.id.wv_video);

    mParentView.findViewById(R.id.btn_live_video).setOnClickListener(this);
    mParentView.findViewById(R.id.btn_playback_video).setOnClickListener(this);

    mParentView.findViewById(R.id.iv_up_video).setOnClickListener(this);
    mParentView.findViewById(R.id.iv_down_video).setOnClickListener(this);
    mParentView.findViewById(R.id.iv_left_video).setOnClickListener(this);
    mParentView.findViewById(R.id.iv_right_video).setOnClickListener(this);

    mParentView.findViewById(R.id.btn_enlarge_video).setOnClickListener(this);
    mParentView.findViewById(R.id.btn_narrow_video).setOnClickListener(this);

    /*SurfaceHolder holder = mVideoView.getHolder();
    holder.addCallback(mCallback);*/
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

  public void playWebView(String url) {
    LogUtil.d(TAG, "playWebView() url=" + url);
    if (url == null || TextUtils.isEmpty(url)) {
      return;
    }

    mVideoWv.reload();

    mVideoWv.getSettings().setJavaScriptEnabled(true);

    //mVideoWv.getSettings().setPluginsEnabled(true);

    //mVideoWv.getSettings().setPluginState(WebSettings.PluginState.ON);

    mVideoWv.setVisibility(View.VISIBLE);

    mVideoWv.getSettings().setUseWideViewPort(true);

    mVideoWv.loadUrl(url);

    lastUrl = url;

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

  @Override
  public void onClick(View v) {
    boolean isControl = false;
    switch (v.getId()) {
      case R.id.btn_live_video:
        ToastUtil.toast("直播");
        if (mCurrentCameraType != null && mCurrentCameraType.equals("球机")) {
          mControlV.setVisibility(View.VISIBLE);
        }
        playWebView(mRawUrl);
        break;
      case R.id.btn_playback_video:
        ToastUtil.toast("回放");
        mControlV.setVisibility(View.INVISIBLE);
        break;

      case R.id.iv_up_video:
        ToastUtil.toast("上");
        isControl = true;
        mControlRequest.direction = 0;
        break;

      case R.id.iv_down_video:
        ToastUtil.toast("下");
        isControl = true;
        mControlRequest.direction = 1;
        break;

      case R.id.iv_left_video:
        ToastUtil.toast("左");
        isControl = true;
        mControlRequest.direction = 2;
        break;

      case R.id.iv_right_video:
        ToastUtil.toast("右");
        isControl = true;
        mControlRequest.direction = 3;
        break;

      case R.id.btn_enlarge_video:
        ToastUtil.toast("放大");
        isControl = true;
        mControlRequest.direction = 8;
        break;

      case R.id.btn_narrow_video:
        ToastUtil.toast("缩小");
        isControl = true;
        mControlRequest.direction = 9;
        break;

      default:
        break;
    }

    if (isControl) {
      LogUtil.d(TAG, "onClick() mControlRequest=" + mControlRequest);
      mPresenter.controlVideo();
    }

  }

  @Override
  public void channelResult(VideoChannel channel) {
    LogUtil.d(TAG, "channelResult() channel=" + channel);
    if (channel == null) {
      return;
    }
    for (VideoChannel.Row row : channel.rows) {
      if (row == null) {
        continue;
      }
      mControlRequest.channelNo = row.channelNo;
      mControlRequest.deviceSerial = row.deviceSerial;
    }
  }

  @Override
  public void controlVideoResult(VideoControlResult result) {

  }

  @Override
  public void setPersonal(VideoContract.Presenter p) {

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
    LogUtil.d(TAG, "stop()");
    if (mPlayer != null) {
      mPlayer.stop();
      mPlayer.release();
      mPlayer = null;
    }
    if (mVideoWv != null) {
      mVideoWv.stopLoading();
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == VideoListActivity.REQUEST_CODE && resultCode == VideoListActivity.RESULT_CODE) {
      String url = data.getStringExtra(VideoListActivity.EXTRA_KEY_URL);
      mRawUrl = url;
      String cameraType = data.getStringExtra(VideoListActivity.EXTRA_KEY_CAMERA_TYPE);
      mCurrentCameraType = cameraType;
      if (cameraType == null || !cameraType.equals("球机")) {
        mControlV.setVisibility(View.INVISIBLE);
      } else {
        mControlV.setVisibility(View.VISIBLE);
      }
      if (url == null || TextUtils.isEmpty(url)) {
        return;
      }
      mChannelRequest.specificname = data.getStringExtra(VideoListActivity.EXTRA_KEY_CAMERA_SPECIFIC);
      mPresenter.start();
      playWebView(url);
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
