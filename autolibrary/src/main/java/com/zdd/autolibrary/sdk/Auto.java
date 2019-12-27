package com.zdd.autolibrary.sdk;

import com.zdd.autolibrary.utils.ShellUtil;

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

}
