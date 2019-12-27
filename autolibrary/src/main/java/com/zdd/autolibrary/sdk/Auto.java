package com.zdd.autolibrary.sdk;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.zdd.autolibrary.utils.ShellUtil;

import java.lang.reflect.Method;

/**
 * Created by zdd on 2019/12/26
 */
public class Auto {

    public static void click(int x,int y){
        ShellUtil.execShellCmd(String.format("input tap %d %d",x,y));
    }

    public static void screencap(String savePath){
        ShellUtil.execShellCmd(String.format("screencap -p %s",savePath));
    }

    public static void findImg(String bigImg,String smallImg){

    }

    public static void startApp(Context context,String packageName){
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        context.startActivity(intent);
    }

    public static void finishApp(String packageName) {
//        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
//        homeIntent.addCategory( Intent.CATEGORY_HOME );
//        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        context.startActivity(homeIntent);

        ShellUtil.execShellCmd(String.format("am force-stop %s",packageName));

    }

}
