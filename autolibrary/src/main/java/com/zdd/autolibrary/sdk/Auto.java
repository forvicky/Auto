package com.zdd.autolibrary.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;

import com.zdd.autolibrary.R;
import com.zdd.autolibrary.model.ImgRect;
import com.zdd.autolibrary.opencv.OpencvHelper;
import com.zdd.autolibrary.utils.ShellUtil;

import java.io.FileInputStream;
import java.util.Calendar;

/**
 * Created by zdd on 2019/12/26
 */
public class Auto {
    static {//加载so库
        System.loadLibrary("native-lib");
    }

    private static final String SCREEN_CAP = "/mnt/sdcard/test.png";

    public interface TaskListener {
        void doTask();
    }

    public static void click(int x, int y) {
        ShellUtil.execShellCmd(String.format("input tap %d %d", x, y));
    }

    public static void screencap(String savePath) {
        ShellUtil.execShellCmd(String.format("screencap -p %s", savePath));
    }


    public static void inputTxt(String txt){
        ShellUtil.execShellCmd(String.format("input text %s", txt));

    }

    public static boolean findImg(Context context, int resId,String mark) {
        Log.d("zdd","找图中...||"+mark);
        screencap(SCREEN_CAP);
        sleep(2000);

        FileInputStream fis = null;
        ImgRect imgRect = new ImgRect();
        try {
            fis = new FileInputStream(SCREEN_CAP);
            Bitmap bitmap = BitmapFactory.decodeStream(fis);

            if(bitmap==null)
                return false;

            Log.d("zdd", "width=" + bitmap.getWidth() + "    height=" + bitmap.getHeight());

            Bitmap bitmap2 = BitmapFactory.decodeResource(context.getResources(), resId);
            imgRect = OpencvHelper.matchBitmap(bitmap, bitmap2, Bitmap.Config.ARGB_8888, 5);
            Log.d("zdd", imgRect.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (imgRect.getX() != -1) {
                Auto.click(imgRect.getCenterX(), imgRect.getCenterY());
                return true;
            }else{
                return false;
            }
        }

    }

    public static void startApp(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        context.startActivity(intent);
    }

    public static void finishApp(String packageName) {
        ShellUtil.execShellCmd(String.format("am force-stop %s", packageName));
    }


    public static void doTaskAtTimeFull(int hour, int minute, TaskListener taskListener) {
        long time = System.currentTimeMillis();

        final Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(time);
        int mHour = mCalendar.get(Calendar.HOUR);
        int mMinuts = mCalendar.get(Calendar.MINUTE);

        if (hour == mHour && minute == mMinuts && taskListener != null) {
            taskListener.doTask();
        }
    }

    //工作日
    public static void doTaskAtTimeWorkDelay(int[] hour, int[] minute,int delay,final TaskListener taskListener) {
        long time = System.currentTimeMillis();

        final Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(time);
        int mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int mMinuts = mCalendar.get(Calendar.MINUTE);

        Log.d("zdd","mHour="+mHour+" mMinuts="+mMinuts);

        int week = mCalendar.get(Calendar.DAY_OF_WEEK);

        if (week < 2 || week > 6) {
            return;
        }

        if (taskListener == null) {
            return;
        }

        if (hour.length != minute.length) {
            return;
        }


        for (int i = 0; i < hour.length; i++) {
            if ((mHour == hour[i] && mMinuts == minute[i])||hour[i]==-1&&minute[i]==-1) {
                Log.d("zdd","执行延迟中...");
                sleep(delay);
                taskListener.doTask();
            }
        }

    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
