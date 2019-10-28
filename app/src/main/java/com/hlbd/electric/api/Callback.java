package com.hlbd.electric.api;

public interface Callback<T> {

    // 返回实体类
    void result(T t);

}
