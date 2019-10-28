package com.hlbd.electric.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Environment;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by archermind on 2018.8.15
 *
 * @author yysleep
 */
public class BitmapUtil {

  private static final String TAG = "BitmapUtil";

  /**
   * 获得指定大小的bitmap
   */
  public static Bitmap decodeFile(String uri, int length) {
    BitmapFactory.Options options = new BitmapFactory.Options();
    // 仅获取大小
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(uri, options);
    int maxLength = options.outWidth > options.outHeight ? options.outWidth : options.outHeight;
    // 压缩尺寸，避免卡顿
    int inSampleSize = maxLength / length;
    if (inSampleSize < 1) {
      inSampleSize = 1;
    }
    options.inSampleSize = inSampleSize;
    // 获取bitmap
    options.inJustDecodeBounds = false;
    return BitmapFactory.decodeFile(uri, options);
  }

  /**
   * 获得指定大小的bitmap
   */
  public static Bitmap decodeByteArray(byte[] data, int length) {
    BitmapFactory.Options options = new BitmapFactory.Options();
    // 仅获取大小
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeByteArray(data, 0, data.length, options);
    int maxLength = options.outWidth > options.outHeight ? options.outWidth : options.outHeight;
    // 压缩尺寸，避免卡顿
    int inSampleSize = maxLength / length;
    if (inSampleSize < 1) {
      inSampleSize = 1;
    }
    options.inSampleSize = inSampleSize;
    // 获取bitmap
    options.inJustDecodeBounds = false;
    return BitmapFactory.decodeByteArray(data, 0, data.length, options);
  }

  /**
   * res目录下面的一张图片保存到本地
   *
   * @param id 图片的id
   */
  public static void saveImage(final Context context, final int id) {
    ThreadUtil.startRunnable(new Runnable() {
      @Override
      public void run() {
        saveImageInternal(context, id);
      }
    });
  }

  private static void saveImageInternal(Context context, int id) {
    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/aaa_image_user.png";
    //在本地创建一个文件夹
    File file = new File(path);
    // File absoluteFile = getFilesDir().getAbsoluteFile();
    //判断本地是否存在，防止每次启动App都要创建
    if (file.exists()) {
      LogUtil.i(TAG, "saveImageInternal() file.size= " + file.length());
      file.deleteOnExit();
    }
    LogUtil.i(TAG, "----------------------------------------------------------------");
    //使用BitmapFactory把res下的图片转换成Bitmap对象
    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);
    FileOutputStream out = null;
    try {
      out = new FileOutputStream(file);
      bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (out != null) {
        try {
          out.flush();
          out.close();
          ToastUtil.toast("图片存储成功");
          LogUtil.i(TAG, "图片存储成功, " + file.getAbsolutePath());
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    LogUtil.i(TAG, "绝对路径" + path);
  }

  public static Bitmap toRoundBitmap(Bitmap bitmap) {
    LogUtil.i(TAG, "toRoundBitmap() bitmap=" + bitmap);
    if (bitmap == null) {
      return null;
    }
    int width = bitmap.getWidth();
    int height = bitmap.getHeight();
    float roundPx;
    float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
    if (width <= height) {
      roundPx = width / 2;
      top = 0;
      bottom = width;
      left = 0;
      right = width;
      height = width;
      dst_left = 0;
      dst_top = 0;
      dst_right = width;
      dst_bottom = width;
    } else {
      roundPx = height / 2;
      float clip = (width - height) / 2;
      left = clip;
      right = width - clip;
      top = 0;
      bottom = height;
      width = height;
      dst_left = 0;
      dst_top = 0;
      dst_right = height;
      dst_bottom = height;
    }
    Bitmap output = Bitmap.createBitmap(width,
            height, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(output);
    final int color = 0xff424242;
    final Paint paint = new Paint();
    final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
    final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
    final RectF rectF = new RectF(dst);
    paint.setAntiAlias(true);
    canvas.drawARGB(0, 0, 0, 0);
    paint.setColor(color);
    canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    canvas.drawBitmap(bitmap, src, dst, paint);
    return output;
  }

  public static String bmpTo64String(Bitmap bitmap) {
    // 将Bitmap转换成字符串
    String str64 = null;
    ByteArrayOutputStream bStream = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
    byte[] bytes = bStream.toByteArray();
    str64 = Base64.encodeToString(bytes, Base64.DEFAULT);
    return str64;
  }

}
