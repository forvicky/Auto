package com.zdd.auto;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.zdd.autolibrary.sdk.Auto;
import com.zdd.autolibrary.utils.ShellUtil;

import java.util.Random;

public class MainActivity extends PermissonActivity {
    private static final String PACKAGE_NAME="com.foreverht.newland.workplus";
    private final Context CONTEXT=MainActivity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(){
            @Override
            public void run() {
                super.run();

                while (true){

                    Log.d("zdd","Thread.run...");
                    Random random=new Random();
                    int delay=random.nextInt(10)*60*1000;

                    Log.d("zdd","delay time="+delay);

                    Auto.doTaskAtTimeWorkDelay(new int[]{-1}, new int[]{-1},delay, new Auto.TaskListener() {
                        @Override
                        public void doTask() {
                            Auto.finishApp(PACKAGE_NAME);
                            Auto.sleep(3000);
                            Auto.startApp(CONTEXT,PACKAGE_NAME);
                            while(true){
                                if(Auto.findImg(CONTEXT,R.drawable.yy,"应用")==true){
                                    break;
                                }

                                if(Auto.findImg(CONTEXT,R.drawable.mmdl,"密码登录")==true){
                                    Auto.sleep(2000);
                                    Auto.inputTxt("zdd@2093419");
                                    Auto.sleep(2000);

                                    Auto.findImg(CONTEXT,R.drawable.dl,"登录");
                                }

                            }

                            Auto.sleep(2000);

                            while(true){
                                if(Auto.findImg(CONTEXT,R.drawable.ydkq,"移动考勤")==true){
                                    break;
                                }
                            }

                            Auto.sleep(5000);


                            while(true){
                                if(Auto.findImg(CONTEXT,R.drawable.ljdk,"立即打卡")==true){
                                    Auto.sleep(2000);
                                    Auto.finishApp(PACKAGE_NAME);
                                    break;
                                }

                                Auto.findImg(CONTEXT,R.drawable.cxlj,"重新连接");
                            }

                        }
                    });

                }

            }
        }.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShellUtil.exitShell();
    }
}
