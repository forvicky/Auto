package com.zdd.autolibrary.utils;

import java.io.OutputStream;

/**
 * 1、换行符很重要，没有的话，cmd认为指令还没结束是不会执行的
 * 2、outputStream最好复用，不然启动一次su很慢，会导致adb命令执行缓慢
 * Created by zdd on 2019/11/20
 */
public class ShellUtil {
    private static final String LINE_SEP = System.getProperty("line.separator");

    private ShellUtil() {
    }

    private static OutputStream outputStream;

    /**
     * 执行shell命令
     *
     * @param cmd
     */
    public static void execShellCmd(String cmd) {
        String cmdLine=cmd;

        //换行符很重要，没有的话，cmd认为指令还没结束是不会执行的
        if(!cmd.contains(LINE_SEP))
            cmdLine=cmd+LINE_SEP;

        try {
            // 申请获取root权限，这一步很重要，不然会没有作用
            if(outputStream==null){
                //outputStream最好复用，不然启动一次su很慢，会导致adb命令执行缓慢
                outputStream = Runtime.getRuntime().exec("su").getOutputStream();
            }

            // 获取输出流
            outputStream.write(cmdLine.getBytes());
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void exitShell() {
        try {
            outputStream.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
