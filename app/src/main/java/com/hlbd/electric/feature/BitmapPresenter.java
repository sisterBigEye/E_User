package com.hlbd.electric.feature;

import android.graphics.Bitmap;

import com.hlbd.electric.api.Callback;
import com.hlbd.electric.api.ITask;
import com.hlbd.electric.base.IRequest;
import com.hlbd.electric.task.BitmapTask;
import com.hlbd.electric.util.LogUtil;


public class BitmapPresenter implements BaseDataContract.Presenter {
    private static final String TAG = "BitmapPresenter";
    private BaseDataContract.View<Bitmap> view;
    private IRequest request;
    private ITask<Bitmap> bmpTask;

    public BitmapPresenter(BaseDataContract.View<Bitmap> view, IRequest request) {
        this.view = view;
        this.view.setPersonal(this);
        this.request = request;
    }

    @Override
    public void start() {
        request();
    }

    @Override
    public void request() {
        if (request == null) {
            LogUtil.e(TAG, "loadBitmap() --- startTask is null");
            return;
        }
        if (bmpTask == null) {
            bmpTask = new BitmapTask();
        }
        bmpTask.startTask(request, new Callback<Bitmap>() {
            @Override
            public void result(Bitmap bitmap) {
                view.result(bitmap);
            }
        });
    }
}
