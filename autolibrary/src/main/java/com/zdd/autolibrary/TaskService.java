package com.zdd.autolibrary;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by zdd on 2019/12/30
 */
public class TaskService extends Service {

    private static final String TAG = TaskService.class.getSimpleName();

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
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),R.drawable.ic_launcher))
                .setContentTitle("title")
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentText("contentText")
                .setWhen(System.currentTimeMillis());

        Notification notification=builder.build();
        notification.defaults=Notification.DEFAULT_SOUND;

        startForeground(1,notification);
        return super.onStartCommand(intent,flags,startId);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
