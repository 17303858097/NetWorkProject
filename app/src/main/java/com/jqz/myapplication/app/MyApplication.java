package com.jqz.myapplication.app;

import android.app.Application;

import com.jqz.myapplication.HttpContans;
import com.jqz.myapplication.HttpGlobalConfig;
import com.jqz.myapplication.callback.HttpCallBack;

/*
项目名：NetWorkProject
包名：com.jqz.myapplication.app
文件名：MyApplication
创建者：梦想
创建时间：2020/8/4  16:35  
描述：TODO
格言：唯有自己强大，才不会感到害怕！
*/public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //下载数据
        HttpGlobalConfig.getInstance()
                .setBaseUrl("https://www.wanandroid.com/")//baseurl
                .setTimeOut(HttpContans.TIME_OUT)//添加时间
                .setTimeUnit(HttpContans.TIME_UNIT)//添加时间单位
                .setShowLog(true)//设置日志开关
                .initReady(this);


    }
}
