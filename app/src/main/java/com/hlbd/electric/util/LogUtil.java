package com.hlbd.electric.util;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LogUtil {

    private static final String TAG = "Electric-";
    private static boolean DEBUG = true;
    public static boolean sIsWrite = false;
    private static String root = Environment.getExternalStorageDirectory().getAbsolutePath();
    private static FileWriter fileWriter;
    private static boolean fileExists;
    private static SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:SSS", Locale.US);
    private static String mLogPath;

    public static void i(String tag, String msg) {
        if (DEBUG) {
            Log.i(TAG + tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(TAG + tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            Log.e(TAG + tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable e) {
        if (DEBUG) {
            Log.e(TAG + tag, msg, e);
        }
    }

    public static void w(String tag, String msg) {
        if (DEBUG) {
            Log.w(TAG + tag, msg);
        }
    }

    public static void write(String tag, String msg) {
        createFile();
        d(tag, msg);
        if (sIsWrite) {
            String time = format.format(new Date());
            try {
                fileWriter = new FileWriter(mLogPath, true);
                //fileWriter.append(time).append("-").append(tag).append(": ").append(msg).append("\n");
                fileWriter.write(msg);
                fileWriter.flush();
            } catch (Exception e) {
                Log.e(TAG, "write error", e);
            } finally {
                if (fileWriter != null) {
                    try {
                        fileWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                fileWriter = null;
            }
        }
    }

    public static void createFile()  {
        String dirPath = root + "/Electric";
        mLogPath = dirPath + "/log.txt";
        File dir = new File(dirPath);
        if(!dir.exists()) {
            boolean exists = dir.mkdirs();
            if(exists) {
                createFile(mLogPath);
            }
        } else {
            createFile(mLogPath);
        }
        d(TAG, "init() fileExists=" + fileExists);
    }

    private static void createFile(String filePath) {

        File file = new File(filePath);
        if(!file.exists()) {
            try {
                fileExists = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            fileExists = true;
        }
    }
}
