package com.zdd.auto.http;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 请求头里面所有接口的公共参数
 * Created by zdd on 2018/5/3.
 */
public class BaseHeaderInterceptor implements Interceptor {
    private static final String DEVICE_TYPE="android";

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request newRequest = chain.request().newBuilder()
                .addHeader("deviceType",DEVICE_TYPE)
                .addHeader("deviceTime",System.currentTimeMillis()+"")
                .build();

        return chain.proceed(newRequest);
    }
}
