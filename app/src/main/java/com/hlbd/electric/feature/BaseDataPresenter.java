package com.hlbd.electric.feature;

import com.hlbd.electric.api.Callback;
import com.hlbd.electric.api.ITask;
import com.hlbd.electric.base.IRequest;
import com.hlbd.electric.task.RequestDataTask;
import com.hlbd.electric.util.LogUtil;

public class BaseDataPresenter<T> implements BaseDataContract.Presenter<T> {

    private static final String TAG = "BaseDataPresenter";
    private BaseDataContract.View<T> view;
    private String url;
    private ITask<T> task;
    private IRequest request;
    private Class<T> clazz;

    public BaseDataPresenter(BaseDataContract.View<T> view, IRequest request, Class<T> clazz) {
        this.view = view;
        this.url = url;
        this.request = request;
        this.clazz = clazz;
        this.view.setPersonal(this);
    }

    @Override
    public void start() {
        request();
    }

    @Override
    public void request() {
        if (url == null) {
            LogUtil.e(TAG, "loadBitmap() --- url is null");
            return;
        }
        if (task == null) {
            task = new RequestDataTask<>(clazz);
        }
        view.requestStart();
        task.startTask(request, new Callback<T>() {
            @Override
            public void result(T result) {
                view.result(result);
                view.requestEnd();
            }
        });

    }

}
