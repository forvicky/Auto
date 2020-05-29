package com.zdd.autolibrary.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * 1、换行符很重要，没有的话，cmd认为指令还没结束是不会执行的
 * 2、outputStream最好复用，不然启动一次su很慢，会导致adb命令执行缓慢
 * 3、su不退出的话，会一直阻塞在 process.waitFor()
 * 4、截屏不要保存到同一个文件中，上传文件是异步的
 * 5、搜狗输入法会影响input text 的准确性
 * Created by zdd on 2019/11/20
 */
public class ShellUtil {
    private static final String LINE_SEP = System.getProperty("line.separator");

    private ShellUtil() {
    }


    /**
     * 执行shell命令
     *
     * @param cmd
     */
    public static boolean execShellCmd(String cmd) {
        String cmdLine = cmd;

        //换行符很重要，没有的话，cmd认为指令还没结束是不会执行的
        if (!cmd.contains(LINE_SEP))
            cmdLine = cmd + LINE_SEP;

        Log.d("zdd", "执行 cmd=" + cmd);
        try {
            // 申请获取root权限，这一步很重要，不然会没有作用

            //outputStream最好复用，不然启动一次su很慢，会导致adb命令执行缓慢
            Process process = Runtime.getRuntime().exec("su");

            OutputStream outputStream = process.getOutputStream();


            // 获取输出流
            outputStream.write(cmdLine.getBytes());
            outputStream.write("exit\n".getBytes());
            outputStream.flush();

            new RunThread(process.getInputStream(), "INFO").start();
            new RunThread(process.getErrorStream(), "ERR").start();

            process.waitFor();

            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (process != null) {
                process.destroy();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    static class RunThread extends Thread {
        InputStream is;
        String printType;

        RunThread(InputStream is, String printType) {
            this.is = is;
            this.printType = printType;
        }

        public void run() {
            try {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line = null;
                while ((line = br.readLine()) != null)
                    Log.d("zdd", printType + ">" + line);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }




}
