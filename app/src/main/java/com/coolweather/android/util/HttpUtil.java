package com.coolweather.android.util;


import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil {

    /**
     * 向服务器发送请求
     * @param address  请求地址
     * @param callback 回调，处理服务器响应*/
    public static void sendOkHttpRequest(String address, Callback callback){
        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(15, TimeUnit.SECONDS).
                readTimeout(15,TimeUnit.SECONDS).writeTimeout(15,TimeUnit.SECONDS).build();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
