package com.hlbd.electric.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hlbd.electric.ElectricApplication;
import com.hlbd.electric.R;

/**
 * Created by YySleep on 2018/1/17.
 *
 * @author YySleep
 */

public class ToastUtil {

  private static volatile Toast sToast;
  private static volatile Context sContext;

  @SuppressLint("ShowToast")
  public static void toast(final String content) {
    if (Looper.getMainLooper() == Looper.myLooper()) {
      toastInternal(content);
    } else {
      ElectricApplication.sHandler.post(new Runnable() {
        @Override
        public void run() {
          toastInternal(content);
        }
      });
    }
  }

  private static void toastInternal(final String content) {
    if (sToast != null) {
      sToast.cancel();
    }
    sToast = Toast.makeText(sContext, content, Toast.LENGTH_SHORT);
    sToast.show();
  }

  @SuppressLint("ShowToast")
  public static void init(Context context) {
    sContext = context;
  }

}
