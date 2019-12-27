package com.zdd.auto;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;


/**
 * Created by zdd on 2018/11/15
 */
public class PermissonActivity extends FragmentActivity implements EasyPermissions.PermissionCallbacks{
    private static final String TAG=PermissonActivity.class.getSimpleName();
    private static final int REQUEST_CAMERA=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //String[] perms = {Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION};
        String[] perms = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE};

        EasyPermissions.requestPermissions(
                new PermissionRequest.Builder(this, REQUEST_CAMERA, perms)
                        .setRationale("请授予App手机权限，保证App的正常使用")
                        .setPositiveButtonText("确定")
                        .setNegativeButtonText("取消")
                        .build());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG,"this is onRequestPermissionsResult");
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        // Some permissions have been granted
        // ...
        Log.d(TAG,"this is onPermissionsGranted");
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        // Some permissions have been denied
        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        Log.d(TAG,"this is onPermissionsDenied");
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

}
