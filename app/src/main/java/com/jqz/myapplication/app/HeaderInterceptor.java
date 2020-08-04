package com.jqz.myapplication.app;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/*
项目名：NetWorkProject
包名：com.jqz.myapplication.app
文件名：HeaderInterceptor
创建者：梦想
创建时间：2020/8/4  16:34  
描述：TODO
格言：唯有自己强大，才不会感到害怕！
*/public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        builder.removeHeader("User-Agent");
        builder.addHeader("User-Agent", "apps/api");
        Request build = builder.build();
        return chain.proceed(build);
    }
}
