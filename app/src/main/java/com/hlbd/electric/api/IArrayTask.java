package com.hlbd.electric.api;

import java.util.List;

public interface IArrayTask<T> {

    // T为返回实体类
    void startTask(String url, Object obj, Callback<List<T>> c);
}
