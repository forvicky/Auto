package com.zdd.auto;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import com.zdd.autolibrary.TestActivity;
import com.zdd.autolibrary.sdk.Auto;
import com.zdd.autolibrary.utils.ShellUtil;

public class MainActivity extends PermissonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Auto.click(100,100);
            }
        },2000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Auto.startApp(MainActivity.this,"com.foreverht.newland.workplus");
            }
        },5000);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Auto.finishApp("com.foreverht.newland.workplus");
            }
        },7000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Auto.startApp(MainActivity.this,"com.foreverht.newland.workplus");
            }
        },9000);


        findViewById(R.id.tv_result).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TestActivity.class));


            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShellUtil.exitShell();
    }
}
