package com.hlbd.electric.feature.launcher.data.realtime.video;


import com.hlbd.electric.base.BasePresenter;
import com.hlbd.electric.base.BaseView;

class VideoContract {

    interface Presenter extends BasePresenter {

        void requestChannel();

        void controlVideo();

        void playbackRequest();

    }

    interface View extends BaseView<Presenter> {

        void channelResult(VideoChannel channel);

        void controlVideoResult(VideoControlResult result);

        void playbackResult(VideoPlaybackResult result);


    }

}
