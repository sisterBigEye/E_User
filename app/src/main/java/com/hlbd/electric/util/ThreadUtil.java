package com.hlbd.electric.util;

import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtil {

    private static final String TAG = "ThreadUtil";
    private static ExecutorService sExecutorService;


    public static void startRunnable(Runnable runnable) {
        if (runnable == null) {
            LogUtil.e(TAG, "startRunnable() --- runnable is null");
            return;
        }
        if (Looper.getMainLooper() != Looper.myLooper()) {
            LogUtil.e(TAG, "startRunnable() --- is not main thread, don not need other thread");
            return;
        }

        if (sExecutorService == null) {
            sExecutorService = Executors.newFixedThreadPool(4);
        }
        sExecutorService.execute(runnable);
    }

}
