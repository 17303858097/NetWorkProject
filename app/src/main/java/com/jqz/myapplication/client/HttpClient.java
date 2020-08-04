package com.jqz.myapplication.client;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonElement;
import com.jqz.myapplication.HttpContans;
import com.jqz.myapplication.HttpGlobalConfig;
import com.jqz.myapplication.HttpManager;
import com.jqz.myapplication.callback.BaseCallBack;
import com.jqz.myapplication.net.ApiService;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/*
项目名：NetWorkProject
包名：com.jqz.myapplication.client
文件名：HttpClient
创建者：梦想
创建时间：2020/8/4  15:21  
描述：TODO
格言：唯有自己强大，才不会感到害怕！
*/public class HttpClient {
    //请求方式
    MyMethod method;
    //请求参数
    Map<String, Object> parmser;
    //请求头信息
    Map<String, Object> heades;
    //Rxjava绑定生命周期
    LifecycleProvider lifecycleProvider;
    //绑定activity生命周期
    ActivityEvent activityEvent;
    //fragment生命周期
    FragmentEvent fragmentEvent;
    //公共的url
    String baseUrl;
    //拼接的url
    String apiUrl;
    //是否是json上传标志
    boolean isUpJson;
    //json字符串
    String jsonBody;
    //超时时间
    long time;
    //时间单位
    TimeUnit timeUnit;
    //回调接口
    BaseCallBack baseCallBack;
    //订阅关系的标识
    String tag;


    public HttpClient(Builder builder) {
        this.method = builder.method;
        this.activityEvent = builder.activityEvent;
        this.fragmentEvent = builder.fragmentEvent;
        this.parmser = builder.paramser;
        this.heades = builder.headres;
        this.lifecycleProvider = builder.lifecycleProvider;
        this.baseUrl = builder.baseUrl;
        this.apiUrl = builder.apiUrl;
        this.isUpJson = builder.isJson;
        this.jsonBody = builder.jsonbody;
        this.time = builder.time;
        this.timeUnit = builder.timeUnit;
        this.tag = builder.tag;
    }

    public static final class Builder {
        //请求方式
        MyMethod method;
        //请求参数
        Map<String, Object> paramser;
        //请求头信息
        Map<String, Object> headres;
        //Rxjava绑定生命周期
        LifecycleProvider lifecycleProvider;
        //绑定Activity具体的生命周的
        ActivityEvent activityEvent;
        //绑定Fragment的具体的生命周期的
        FragmentEvent fragmentEvent;
        String baseUrl;
        //拼接的url
        String apiUrl;
        //是否是json上传表示
        boolean isJson;
        //json字符串
        String jsonbody;
        //超时时间
        long time;
        //时间单位
        TimeUnit timeUnit;
        //订阅的标签
        String tag;


        public Builder setTag(String tag) {
            this.tag = tag;
            return this;
        }

        public Builder get() {
            this.method = MyMethod.GET;
            return this;
        }

        public Builder post() {
            this.method = MyMethod.POST;
            return this;
        }

        public Builder delete() {
            this.method = MyMethod.DELETE;
            return this;
        }

        public Builder put() {
            this.method = MyMethod.PUT;
            return this;
        }

        public Builder() {
        }


        //设置参数的拼接
        public Builder setParamser(Map<String, Object> paramser) {
            this.paramser = paramser;
            return this;
        }

        public Map<String, Object> getHeadres() {
            return headres;
        }

        //请求头的拼接
        public Builder setHeadres(Map<String, Object> headres) {
            this.headres = headres;
            return this;
        }

        public LifecycleProvider getLifecycleProvider() {
            return lifecycleProvider;
        }

        //用于绑定Rxjava的生命周期
        public Builder setLifecycleProvider(LifecycleProvider lifecycleProvider) {
            this.lifecycleProvider = lifecycleProvider;
            return this;
        }

        public ActivityEvent getActivityEvent() {
            return activityEvent;
        }

        public Builder setActivityEvent(ActivityEvent activityEvent) {
            this.activityEvent = activityEvent;
            return this;
        }

        public FragmentEvent getFragmentEvent() {
            return fragmentEvent;
        }

        public Builder setFragmentEvent(FragmentEvent fragmentEvent) {
            this.fragmentEvent = fragmentEvent;
            return this;
        }


        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }


        public Builder setApiUrl(String apiUrl) {
            this.apiUrl = apiUrl;
            return this;
        }

        public long getTime() {
            return time;
        }

        public Builder setTime(long time) {
            this.time = time;
            return this;
        }


        public Builder setTimeUnit(TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
            return this;
        }

        public Builder setJsonBody(String jsonBody, boolean isJson) {
            this.jsonbody = jsonBody;
            this.isJson = isJson;
            return this;
        }

        public HttpClient build() {
            return new HttpClient(this);
        }


    }

    public void request(BaseCallBack baseCallBack) {
        if (baseCallBack == null) {
            //如果为空，则加载异常
            new RuntimeException("no have callback,must do Observer");
        }
        this.baseCallBack = baseCallBack;

        doRequest();

    }

    private void doRequest() {
        //组装Obserable,并且根据请求方式返回对应的Obserable，去处理异常结果的回调
        if (TextUtils.isEmpty(tag)) {
            //如果为空
            tag = System.currentTimeMillis() + "";
        }

        baseCallBack.setTag(tag);

        //添加参数信息
        addParmers();
        //添加头信息
        addHeaders();
        //判断
        if (HttpGlobalConfig.getInstance().getBaseUrl() != null) {
            this.baseUrl = HttpGlobalConfig.getInstance().getBaseUrl();
        }
        //创建observer
        Observable observer = createObserver();

        HttpObserable.Buidler buidler = new HttpObserable.Buidler(observer);
        HttpObserable httpObserable = buidler
                .setActivityEvent(activityEvent)
                .setFragmentEvent(fragmentEvent)
                .setBaseObserver(baseCallBack)
                .build();
        httpObserable.observer().subscribe(baseCallBack);

    }

    private Observable createObserver() {
        Observable observable = null;
        boolean hasBodyString = !TextUtils.isEmpty(jsonBody);

        RequestBody requestBody = null;
        if (hasBodyString) {
            Log.e("111", "createObserver: " + hasBodyString);
            String mediaType = isUpJson ? "application/json; charset=utf-8" : "text/plain;charset=utf-8";
            requestBody = RequestBody.create(okhttp3.MediaType.parse(mediaType), jsonBody);

        }

        //默认请求是post
        if (method == null) {
            method = MyMethod.POST;
        }
        if (HttpGlobalConfig.getInstance().getTimeOut() != 0) {
            this.time = HttpGlobalConfig.getInstance().getTimeOut();
        }
        if (this.time == 0) {
            this.time = HttpContans.TIME_OUT;
        }
        if (HttpGlobalConfig.getInstance().getTimeUnit() != null) {
            this.timeUnit = HttpGlobalConfig.getInstance().getTimeUnit();
        }

        if (this.timeUnit == null) {
            this.timeUnit = HttpContans.TIME_UNIT;
        }
        Log.e("111", "createObservable: " + baseUrl);
        ApiService apiServer = HttpManager.getInstance().getRetrofit(baseUrl, time, timeUnit).create(ApiService.class);


        switch (method) {
            case GET:
                observable = apiServer.get(apiUrl, parmser, heades);
                break;
            case POST:
                if (isUpJson) {
                    //如果是post上传json
                    observable = apiServer.postJson(apiUrl, requestBody, heades);
                } else {
                    //如果不是
                    observable = apiServer.post(apiUrl, parmser, heades);
                }
                break;
            case DELETE:
                observable = apiServer.delete(apiUrl, parmser, heades);
                break;
            case PUT:
                observable = apiServer.put(apiUrl, parmser, heades);
                break;


        }
        return observable;

    }

    private void addHeaders() {
        if (heades == null) {
            heades = new HashMap<>();
        }

        //添加公共的头信息
        if (HttpGlobalConfig.getInstance().getBaseHeaders() != null) {
            heades.putAll(HttpGlobalConfig.getInstance().getBaseHeaders());
        }
    }

    private void addParmers() {
        if (parmser == null) {
            parmser = new HashMap<>();
        }

        //添加公共的请求参数
        if (HttpGlobalConfig.getInstance().getBasePrams() != null) {
            parmser.putAll(HttpGlobalConfig.getInstance().getBasePrams());//添加集合
        }

    }


}
