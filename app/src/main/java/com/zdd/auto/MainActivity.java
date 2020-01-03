package com.zdd.auto;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;

import com.zdd.autolibrary.sdk.Auto;
import com.zdd.autolibrary.utils.ShellUtil;

public class MainActivity extends PermissonActivity {
    Intent intent;
    private TaskRunningService.MyBinder myBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("zdd", "ServiceConnection onServiceConnected");
            myBinder = (TaskRunningService.MyBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("zdd", "ServiceConnection onServiceDisconnected");

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(this, TaskRunningService.class);
        startService(intent);
        bindService(intent, connection, BIND_AUTO_CREATE);

    }

    public void clickListener(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.btn_start:
                myBinder.start();
                break;
            case R.id.btn_stop:
                myBinder.pause();
                break;
            case R.id.btn_finish:
                myBinder.finish();

                stopService(intent);
                unbindService(connection);
                break;

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShellUtil.exitShell();
    }
}
