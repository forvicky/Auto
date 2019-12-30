package com.zdd.auto;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
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

    private static final String TAG = TaskRunningService.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
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
                while (true){
                    Log.d("zdd","TaskRunningService Thread.run...");

                    Auto.click(100,200);

                    Random random=new Random();
                    int delay=random.nextInt(10)*60*1000;

                    delay=0;
                    Log.d("zdd","delay time="+delay);

                    Auto.doTaskAtTimeWorkDelay(new int[]{0,7}, new int[]{0,30},delay, new Auto.TaskListener() {
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


        return super.onStartCommand(intent,flags,startId);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
