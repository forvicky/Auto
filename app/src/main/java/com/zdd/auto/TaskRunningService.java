package com.zdd.auto;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;
import com.zdd.autolibrary.sdk.Auto;
import java.util.Random;

/**
 * Created by zdd on 2019/12/30
 */
public class TaskRunningService extends Service {
    private static final String PACKAGE_NAME="com.foreverht.newland.workplus";
    private final Context CONTEXT= TaskRunningService.this;

    private boolean running;
    private boolean finished=false;

    private MyBinder mBinder=new MyBinder();

    private PowerManager.WakeLock wakeLock;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("zdd", "TaskRunningService onCreate");
        running=true;

        //增加屏幕常亮方法，屏幕暗淡对截图会有影响
        PowerManager powerManager=(PowerManager) getSystemService(POWER_SERVICE);
        wakeLock=powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK,"myapp:MyWakelockTag");
        wakeLock.acquire();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("zdd", "TaskRunningService onStartCommand");

        Notification.Builder builder=new Notification.Builder((this.getApplicationContext()));
        Intent nfIntent=new Intent();

        builder.setContentIntent(PendingIntent.getActivity(this,0,nfIntent,0))
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), com.zdd.autolibrary.R.drawable.ic_launcher))
                .setContentTitle("title")
                .setSmallIcon(com.zdd.autolibrary.R.drawable.ic_launcher)
                .setContentText("contentText")
                .setWhen(System.currentTimeMillis());

        Notification notification=builder.build();
        notification.defaults=Notification.DEFAULT_SOUND;

        startForeground(1,notification);

        new Thread(){
            @Override
            public void run() {
                super.run();
                while (!finished){
                    Log.d("zdd","TaskRunningService Thread.run...");

                    Random random=new Random();
                    int delay=random.nextInt(6)*60*1000;

                    Log.d("zdd","delay time="+delay);
                   // new int[]{7,8,17,20,0}, new int[]{30,10,30,0,0}
                    Auto.doTaskAtTimeWorkDelay(new int[]{7,8,17,20,0,14}, new int[]{30,10,30,0,0,56},delay, new Auto.TaskListener() {
                        @Override
                        public void doTask() {
                            running=true;
                            Auto.finishApp(PACKAGE_NAME);
                            Auto.sleep(3000);
                            Auto.startApp(CONTEXT,PACKAGE_NAME);
                            Auto.sleep(6000);


                            new Thread(){
                                @Override
                                public void run() {
                                    super.run();

                                    try {
                                        Thread.sleep(20*60*1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                    running=false;
                                    Auto.finishApp(PACKAGE_NAME);
                                    Log.d("zdd","结束异常任务");
                                }
                            }.start();

                            while(running){
                                if(Auto.findImg(CONTEXT,R.drawable.yy,"应用")==true){
                                    break;
                                }

                                if(Auto.findImg(CONTEXT,R.drawable.mmdl,"密码登录")==true){
                                    Auto.sleep(2000);
                                    Auto.inputTxt("zdd@2093419");
                                    Auto.sleep(2000);
                                }

                                Auto.findImg(CONTEXT,R.drawable.dl,"登录");

                            }

                            Auto.sleep(2000);

                            while(running){
                                if(Auto.findImg(CONTEXT,R.drawable.ydkq,"移动考勤")==true){
                                    break;
                                }
                            }

                            Auto.sleep(5000);


                            while(running){
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


        return super.onStartCommand(intent,flags,startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("zdd", "TaskRunningService onDestroy");

        running=false;
        wakeLock.release();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    class MyBinder extends Binder{
        public void start(){
            running=true;
        }

        public void pause(){
            running=false;
        }

        public void finish(){
            finished=true;
        }
    }

}
