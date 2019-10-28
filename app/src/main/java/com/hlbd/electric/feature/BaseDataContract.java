package com.hlbd.electric.feature;


import com.hlbd.electric.base.BasePresenter;
import com.hlbd.electric.base.BaseView;

public class BaseDataContract {

    public interface Presenter<T> extends BasePresenter<T> {

        void request();

    }

    public interface View<T> extends BaseView<Presenter> {

        void requestStart();

        void result(T result);

        void requestEnd();

    }

}
