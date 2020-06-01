package com.zdd.auto;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.upyun.library.common.Params;
import com.upyun.library.common.UploadEngine;
import com.upyun.library.listener.UpCompleteListener;
import com.upyun.library.utils.UpYunUtils;
import com.zdd.auto.http.BaseHeaderInterceptor;
import com.zdd.auto.http.BaseResponse;
import com.zdd.auto.http.NlpLoggingInterceptor;
import com.zdd.auto.http.SSLSocketFactoryUtils;
import com.zdd.auto.http.model.Img;
import com.zdd.auto.http.model.Token;
import com.zdd.autolibrary.sdk.Auto;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * Created by zdd on 2019/12/30
 */
public class TaskRunningService extends Service {
    private static final String PACKAGE_NAME = "com.foreverht.newland.workplus";
    private final Context CONTEXT = TaskRunningService.this;

    private boolean running;
    private boolean finished = false;

    private MyBinder mBinder = new MyBinder();

    private PowerManager.WakeLock wakeLock;
    NotificationManager notificationManager;
    String notificationId = "zdd";
    String notificationName = "zdd";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("zdd", "TaskRunningService onCreate");
        startForegroundService();

        running = true;

        //增加屏幕常亮方法，屏幕暗淡对截图会有影响
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "myapp:MyWakelockTag");
        wakeLock.acquire();
    }

    private void startForegroundService() {

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //创建NotificationChannel

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(notificationId, notificationName, NotificationManager.IMPORTANCE_HIGH);

            notificationManager.createNotificationChannel(channel);

        }

        startForeground(1, getNotification());

    }

    private Notification getNotification() {
        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle("zdd服务")
                .setContentText("zdd服务正在运行...");

        //设置Notification的ChannelID,否则不能正常显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            builder.setChannelId(notificationId);

        }

        Notification notification = builder.build();

        return notification;

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("zdd", "TaskRunningService onStartCommand");


        new Thread() {
            @Override
            public void run() {
                super.run();
                while (!finished) {
//                    Log.d("zdd", "TaskRunningService Thread.run...");

                    Random random = new Random();
//                    int delay = random.nextInt(2) * 60 * 1000;
                    int delay=0;
//                    new int[]{7,8,17}, new int[]{50,20,30}
                //    new int[]{-1}, new int[]{-1}
                    //new int[]{7,8,17,17,18}, new int[]{50,30,00,30,20}

                    Auto.doTaskAtTimeWorkDelay(new int[]{7,8,16,17,18}, new int[]{50,30,30,30,20}, delay, new Auto.TaskListener() {
                        @Override
                        public void doTask() {
                            Auto.open();
                            Auto.unlock();
                            Auto.sleep(3000);
                            running = true;
                            Auto.finishApp(PACKAGE_NAME);
                            Auto.sleep(3000);
                            Auto.startApp(CONTEXT, PACKAGE_NAME);
                            Auto.sleep(3000);


                            //先注释看看任务执行异常点
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

                            while (running) {
                                if (Auto.findImg(CONTEXT, R.drawable.yy, "应用") == true) {
                                    break;
                                }

                                if (Auto.findImg(CONTEXT, R.drawable.mmdl, "密码登录") == true) {
                                    Auto.sleep(2000);
//                                    Auto.inputTxt("zdd@2093419");
                                    Auto.inputTxt("z");
                                    Auto.inputTxt("d");
                                    Auto.inputTxt("d");
                                    Auto.inputTxt("@");
                                    Auto.inputTxt("2");
                                    Auto.inputTxt("0");
                                    Auto.inputTxt("9");
                                    Auto.inputTxt("3");
                                    Auto.inputTxt("4");
                                    Auto.inputTxt("1");
                                    Auto.inputTxt("9");
                                    Auto.sleep(2000);
                                }

                                Auto.findImg(CONTEXT, R.drawable.dl, "登录");

                            }

                            Auto.sleep(2000);

                            while (running) {
                                if (Auto.findImg(CONTEXT, R.drawable.ydkq, "移动考勤") == true) {
                                    break;
                                }
                            }

                            Auto.sleep(3000);


                            while (running) {
                                if (Auto.findImg(CONTEXT, R.drawable.ljdk, "立即打卡") == true) {
                                    sendOk();

                                    Auto.sleep(5000);

                                    try {
                                        uploadFileBySM("/mnt/sdcard/result.png");

                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }


                                    Auto.sleep(5000);
                                    Auto.finishApp(PACKAGE_NAME);
                                    break;
                                }

                                Auto.findImg(CONTEXT, R.drawable.cxlj, "重新连接");
                            }

                            Auto.close();

                        }
                    });

                }


            }
        }.start();


        return super.onStartCommand(intent, flags, startId);

    }

    private void sendOk(){
        String serverUrl = String.format("https://sc.ftqq.com/%s.send?text=%s&desp=%s", "SCU99351Tbb1a3de4adc9283801a01cfd6c7c6d635eccbb94bd0f7", "Result", "成功");

        Request request = new Request.Builder()
                .get()
                .url(serverUrl)
                .build();

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.d("zdd", "server酱 response=" + response.body().string());
            }
        });
    }

    private void uploadFile(final String filepath) {

        Auto.screencap(filepath);

        File temp = new File(filepath);

        final Map<String, Object> paramsMap = new HashMap<>();
        //上传空间
        paramsMap.put(Params.BUCKET, "image-zdd");
        //保存路径
        paramsMap.put(Params.SAVE_KEY,filepath);
        //添加 CONTENT_LENGTH 参数使用大文件表单上传
        paramsMap.put(Params.CONTENT_LENGTH, temp.length());


        UploadEngine.getInstance().formUpload(temp, paramsMap, "zdd", UpYunUtils.md5("AmEjF1EYIiGjbWYaNkwQ1ngls1xUX42E"), new UpCompleteListener() {
            @Override
            public void onComplete(boolean isSuccess, Response response, Exception error) {
                try {
                    String result = null;
                    if (response != null) {
                        result = response.body().string();
                    } else if (error != null) {
                        result = error.toString();
                    }
                    //http://youpaiyun.pbzdd.cn/imgs/result.png
                    Log.d("zdd", "isSuccess:" + isSuccess + " result:" + result);

                    if (isSuccess) {
                        String serverUrl = String.format("https://sc.ftqq.com/%s.send?text=%s&desp=%s", "SCU99351Tbb1a3de4adc9283801a01cfd6c7c6d635eccbb94bd0f7", "Result", "http://youpaiyun.pbzdd.cn"+filepath);

                        Request request = new Request.Builder()
                                .get()
                                .url(serverUrl)
                                .build();

                        OkHttpClient client = new OkHttpClient.Builder()
                                .connectTimeout(10, TimeUnit.SECONDS)
                                .readTimeout(10, TimeUnit.SECONDS)
                                .build();

                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                Log.d("zdd", "server酱 response=" + response.body().string());
                            }
                        });
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }, null);

    }

    private void uploadFileBySM(final String filepath){
        Auto.screencap(filepath);


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
            @org.jetbrains.annotations.Nullable
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




                RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), new File(filepath));
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

                        if (imgBaseResponse.isSuccess()) {
                            String serverUrl = String.format("https://sc.ftqq.com/%s.send?text=%s&desp=%s", "SCU99351Tbb1a3de4adc9283801a01cfd6c7c6d635eccbb94bd0f7", "Result", imgBaseResponse.getData().getUrl());

                            Request request = new Request.Builder()
                                    .get()
                                    .url(serverUrl)
                                    .build();

                            OkHttpClient client = new OkHttpClient.Builder()
                                    .connectTimeout(10, TimeUnit.SECONDS)
                                    .readTimeout(10, TimeUnit.SECONDS)
                                    .build();

                            client.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                                }

                                @Override
                                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                    Log.d("zdd", "server酱 response=" + response.body().string());
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("zdd", "TaskRunningService onDestroy");

        running = false;
        wakeLock.release();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    class MyBinder extends Binder {
        public void start() {
            running = true;
        }

        public void pause() {
            running = false;
        }

        public void finish() {
            finished = true;
        }
    }

}
