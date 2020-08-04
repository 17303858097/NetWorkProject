package com.jqz.myapplication;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpManager {
    //静态本类对象，单例双重锁得到创建对象
    private static volatile HttpManager instance;

    private HttpManager() {
        //要想单例必须要有私有的无参构造
    }

    //单例双重锁得到本类对象
    public static HttpManager getInstance() {
        if (instance == null) {
            synchronized (HttpManager.class) {
                if (instance == null)
                    instance = new HttpManager();
            }
        }

        return instance;
    }

    //封装retrofit            //参数为，1、公共的baseUrl，2、时间，3、时间单位
    public Retrofit getRetrofit(String baseUrl, long timeOut, TimeUnit timeUnit) {
        return new Retrofit.Builder()
                .client(getOkHttpClent(timeOut, timeUnit))
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private OkHttpClient getOkHttpClent(long timeOut, TimeUnit timeUnit) {
        //创建日志拦截器
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e("log日志拦截器", "log: " + message);
            }
        });

        //创建普通拦截器
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                return chain.proceed(request);

            }
        };

        //把创建的拦截器添加到数组中
        Interceptor[] interceptors = {logInterceptor, interceptor};

        return getClent(timeOut, timeUnit, interceptors);
    }

    //可变参数...
    private OkHttpClient getClent(long timeOut, TimeUnit timeUnit, Interceptor... interceptors) {
        //创建okhttp
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //添加时间
                builder
                .readTimeout(timeOut, timeUnit)
                .writeTimeout(timeOut, timeUnit)
                .connectTimeout(timeOut, timeUnit);

        //添加拦截器
        for (Interceptor interceptor : interceptors) {

            builder.addInterceptor(interceptor);

        }

        return builder.build();//返回clent
    }

}
