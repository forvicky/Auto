package com.zdd.auto;

import android.content.Intent;
import android.os.Bundle;
import com.zdd.autolibrary.utils.ShellUtil;

public class MainActivity extends PermissonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, TaskRunningService.class);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShellUtil.exitShell();
    }
}
