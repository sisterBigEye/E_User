package com.hlbd.electric.feature.launcher.data.realtime.video;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.hlbd.electric.R;
import com.hlbd.electric.base.BaseFragment;
import com.hlbd.electric.feature.launcher.data.realtime.video.list.VideoListActivity;
import com.hlbd.electric.util.LogUtil;
import com.hlbd.electric.util.ToastUtil;

import java.lang.ref.WeakReference;

public class VideoFragment extends BaseFragment implements View.OnClickListener, VideoContract.View {

  private static final String TAG = "VideoFragment";

  private View mParentView;
  private WebView mVideoWv;
  private View mControlV;
  private String mRawUrl;
  private String mPlaybackUrl;
  private String mCurrentCameraType;
  private String lastUrl = "";

  private VideoPresenter mPresenter;
  private VideoControlRequest mControlRequest;
  private VideoChannelRequest mChannelRequest;
  private VideoPlaybackRequest mVideoPlaybackRequest;

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

    mVideoPlaybackRequest = new VideoPlaybackRequest();
    mVideoPlaybackRequest.url = "Baoding_VideoThing/Services/VideoPlayBack?";
    mPresenter = new VideoPresenter(this, mControlRequest, mChannelRequest, mVideoPlaybackRequest);
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
        if (TextUtils.isEmpty(mRawUrl)) {
          ToastUtil.toast("请先选择一个播放地址");
          return;
        }
        startActivityForResult(new Intent(getActivity(), TimePickActivity.class), TimePickActivity.REQUEST_CODE);
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
  public void playbackResult(VideoPlaybackResult result) {
    mPlaybackUrl = null;
    LogUtil.d(TAG, "playbackResult() result=" + result);
    if (result == null || result.rows == null) {
      ToastUtil.toast("回放失败，没有获取到回放地址");
      return;
    }
    for (VideoPlaybackResult.Row row : result.rows) {
      if (row == null || TextUtils.isEmpty(row.result)) {
        continue;
      }
      mPlaybackUrl = row.result;
      break;
    }
    if (TextUtils.isEmpty(mPlaybackUrl)) {
      ToastUtil.toast("回放失败，没有获取到回放地址");
      return;
    }
    mControlV.setVisibility(View.INVISIBLE);
    ToastUtil.toast("开始回放");
    playWebView(mPlaybackUrl);
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
    } else if (requestCode == TimePickActivity.REQUEST_CODE && resultCode == TimePickActivity.RESULT_CODE) {
      long startTime = data.getLongExtra(TimePickActivity.EXTRA_KEY_START_TIME, 0);
      long endTime = data.getLongExtra(TimePickActivity.EXTRA_KEY_END_TIME, 0);
      mVideoPlaybackRequest.startTime = startTime - 1000 * 100;
      mVideoPlaybackRequest.endTime = endTime;
      mVideoPlaybackRequest.videourl = mRawUrl;
      LogUtil.d(TAG, "onActivityResult() mVideoPlaybackRequest=" + mVideoPlaybackRequest);
      mPresenter.playbackRequest();
      ToastUtil.toast("准备开始回放");
    }

  }

}
