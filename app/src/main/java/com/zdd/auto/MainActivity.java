package com.zdd.auto;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zdd.auto.http.BaseHeaderInterceptor;
import com.zdd.auto.http.BaseResponse;
import com.zdd.auto.http.NlpLoggingInterceptor;
import com.zdd.auto.http.SSLSocketFactoryUtils;
import com.zdd.auto.http.model.Img;
import com.zdd.auto.http.model.Token;
import com.zdd.autolibrary.sdk.Auto;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

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

        //test1();
//        test2();
    }

    private void test1() {
        new Thread() {
            @Override
            public void run() {
                super.run();

                while (true) {
                    Auto.open();
                    Auto.unlock();

                    try {
                        Thread.sleep(3 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Auto.close();
                }

            }
        }.start();
    }


    private void test2() {
        Auto.screencap("/mnt/sdcard/result.png");


        final OkHttpClient mClient =new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(new BaseHeaderInterceptor())
                .sslSocketFactory(SSLSocketFactoryUtils.createSSLSocketFactory(), SSLSocketFactoryUtils.createTrustAllManager())
                .hostnameVerifier(new SSLSocketFactoryUtils.TrustAllHostnameVerifier())                                          //信任所有主机名
                .addInterceptor(new NlpLoggingInterceptor(new NlpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.d("zdd",message);
                    }
                }).setLevel(NlpLoggingInterceptor.Level.BODY)).build();

        RequestBody requestBody=new RequestBody() {
            @Nullable
            @Override
            public MediaType contentType() {
                return null;
            }

            @Override
            public void writeTo(@NotNull BufferedSink bufferedSink) throws IOException {

            }
        };
        Request request = new Request.Builder()
                .addHeader("User-Agent","PostmanRuntime/7.25.0")
                .url("https://sm.ms/api/v2/token?username=zdd2389&password=zdd2093419")
                .post(requestBody)
                .build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final BaseResponse<Token> tokenBaseResponse = new Gson().fromJson(response.body().string(), new TypeToken<BaseResponse<Token>>() {
                }.getType());

                Log.d("zdd",new Gson().toJson(tokenBaseResponse));




                RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), new File("/mnt/sdcard/result.png"));
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("smfile", "smfile", fileBody)
                        .build();


                Request fileRequest = new Request.Builder()
                        .addHeader("User-Agent","PostmanRuntime/7.25.0")
                        .addHeader("Content-Type","multipart/form-data")
                        .addHeader("Authorization",tokenBaseResponse.getData().getToken())
                        .url("https://sm.ms/api/v2/upload")
                        .post(requestBody)
                        .build();


                mClient.newCall(fileRequest).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        final BaseResponse<Img> imgBaseResponse = new Gson().fromJson(response.body().string(), new TypeToken<BaseResponse<Img>>() {
                        }.getType());
                        Log.d("zdd",new Gson().toJson(imgBaseResponse));
                        Log.d("zdd","地址="+imgBaseResponse.getData().getUrl());
                    }
                });
            }
        });

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

    }
}
