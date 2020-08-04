package com.jqz.myapplication.callback;


import android.text.TextUtils;

import com.jqz.myapplication.HttpGlobalConfig;
import com.jqz.myapplication.apiexception.ApiException;
import com.jqz.myapplication.apiexception.ExceptionEngine;
import com.jqz.myapplication.disposable.RequestManagerImpl;
import com.jqz.myapplication.utils.ThreadUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
//继承Observer
public abstract class BaseObserver implements Observer {
    //定义标识tag
    String tag;

    @Override//提供对订阅操作的方法
    public void onSubscribe(Disposable d) {
        if (!TextUtils.isEmpty(tag)) {
            RequestManagerImpl.getInstance().addDisposable(tag,d);
        }
    }

    @Override//数据得到后对订阅进行操作
    public void onNext(Object o) {
        if (!TextUtils.isEmpty(tag)) {
            RequestManagerImpl.getInstance().removeDisposable(tag);
        }
    }

    @Override
    public void onError(Throwable e) {//对错误信息的操作
        if(e instanceof ApiException){
            ApiException apiException= (ApiException) e;
            //回调错误信息
            onCallBackError(apiException.getMsg(),apiException.getCode());
        }else{
            onCallBackError("未知异常", ExceptionEngine.UN_KNOWN_ERROR);//未知异常
        }

        if (!TextUtils.isEmpty(tag)) {
            //然后再处理订阅
            RequestManagerImpl.getInstance().removeDisposable(tag);
        }
    }

    //错误回调方法
    protected abstract void onCallBackError(String msg, int code);

    @Override
    public void onComplete() {//成功信息的操作
        //成功之后解除订阅
        //如果没有被解除订阅
        if(!RequestManagerImpl.getInstance().isDisposable(tag))
            RequestManagerImpl.getInstance().cancleDisposable(tag);//则解除订阅
    }


    public void canclend(){//关闭订阅
        //如果不是主线程
        if(!ThreadUtils.isMainThread()){
            HttpGlobalConfig.getInstance().getHandler().post(new Runnable() {
                @Override
                public void run() {
                    cancle();
//                    RequestManagerImpl.getInstance().cancleDisposable(tag);
                }
            });
        }

    }

    protected abstract void cancle();//关闭订阅


    public void setTag(String tag) {
        this.tag = tag;
    }
}
