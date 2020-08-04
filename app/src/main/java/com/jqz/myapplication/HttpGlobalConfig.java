package com.jqz.myapplication;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;

public class HttpGlobalConfig {

    private long timeOut;
    private TimeUnit timeUnit;

    //定义公共的url

    private String baseUrl;
    //公共的请求参数
    private Map<String, Object> basePrams;
    //公共的请求头信息
    private Map<String, Object> baseHeaders;
    //拦截器
    private List<Interceptor> interceptors;
    //日志开关
    private boolean isShowLog;
    //上下文和handler
    private Context context;
    private Handler handler;

    private HttpGlobalConfig() {
    }

    public long getTimeOut() {
        return timeOut;
    }

    public HttpGlobalConfig setTimeOut(long timeOut) {
        this.timeOut = timeOut;
        return HttpGlobalConfig.getInstance();
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public HttpGlobalConfig setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
        return HttpGlobalConfig.getInstance();

    }

    //静态内部类
    public static final class HttpGlobalConfigHodler {
        //初始化外部类对象
        private static HttpGlobalConfig INSTENCE = new HttpGlobalConfig();
    }

    public static HttpGlobalConfig getInstance() {
        return HttpGlobalConfigHodler.INSTENCE;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public HttpGlobalConfig setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return HttpGlobalConfig.getInstance();
    }

    public Map<String, Object> getBasePrams() {
        return basePrams;
    }

    public HttpGlobalConfig setBasePrams(Map<String, Object> basePrams) {
        this.basePrams = basePrams;
        return HttpGlobalConfig.getInstance();
    }

    public Map<String, Object> getBaseHeaders() {
        return baseHeaders;
    }

    public HttpGlobalConfig setBaseHeaders(Map<String, Object> baseHeaders) {
        this.baseHeaders = baseHeaders;
        return HttpGlobalConfig.getInstance();
    }

    public List<Interceptor> getInterceptors() {
        return interceptors;
    }

    public HttpGlobalConfig setInterceptors(List<Interceptor> interceptors) {
        this.interceptors = interceptors;
        return HttpGlobalConfig.getInstance();
    }

    public boolean isShowLog() {
        return isShowLog;
    }

    public HttpGlobalConfig setShowLog(boolean showLog) {
        isShowLog = showLog;
        return HttpGlobalConfig.getInstance();
    }

    public Context getContext() {
        return context;
    }

    public HttpGlobalConfig setContext(Context context) {
        this.context = context;
        return HttpGlobalConfig.getInstance();
    }

    public Handler getHandler() {
        return handler;
    }

    public HttpGlobalConfig setHandler(Handler handler) {
        this.handler = handler;
        return HttpGlobalConfig.getInstance();
    }


    //
    public HttpGlobalConfig initReady(Context context){
        this.context=context.getApplicationContext();
        this.handler=new Handler(Looper.getMainLooper());
        return HttpGlobalConfig.getInstance();
    }
}
