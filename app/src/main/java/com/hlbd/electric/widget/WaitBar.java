package com.hlbd.electric.widget;

import android.app.Activity;
import android.view.Gravity;
import android.view.WindowManager;

import com.hlbd.electric.util.LogUtil;

public class WaitBar {

    private static final String TAG = "WaitBar";
    private WindowManager windowManager;
    private Activity activity;
    private boolean showing;
    private CircularProgressView bar;
    private WindowManager.LayoutParams params;

    public WaitBar(Activity activity) {
        this.activity = activity;
        windowManager = activity.getWindowManager();

    }

    public void show() {
        if (showing) {
            LogUtil.d(TAG, "show() 已经显示");
            return;
        }
        if (bar == null) {
            bar = new CircularProgressView(activity);
            params = new WindowManager.LayoutParams();
            params.gravity = Gravity.CENTER;
            params.width = 200;
            params.height = 200;
            //params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        }
        showing = true;
        windowManager.addView(bar, params);
        LogUtil.d(TAG, "show() 正在显示中");
    }

    public void dismiss() {
        if (showing) {
            windowManager.removeViewImmediate(bar);
            showing = false;
            LogUtil.d(TAG, "show() 已经移除");
        }
    }


}
