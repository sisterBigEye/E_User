package com.hlbd.electric.util;

import android.annotation.SuppressLint;
import android.content.Context;

public class PxUtil {

    @SuppressLint("StaticFieldLeak")
    private static Context sContent;

    public static void init(Context context) {
        sContent = context.getApplicationContext();
    }
    /*
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int px(float dp) {
        final float scale = sContent.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
