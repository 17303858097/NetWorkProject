package com.jqz.myapplication.callback;

import android.util.Log;

import com.google.gson.JsonElement;
import com.jqz.myapplication.apiexception.ExceptionEngine;

/*
项目名：NetWorkProject
包名：com.jqz.myapplication.callback
文件名：BaseCallBack
创建者：梦想
创建时间：2020/8/4  14:55  
描述：TODO
//继承了baseObserver
*/public abstract class BaseCallBack<T> extends BaseObserver {
    //解析成功的标志
    boolean callSuccess=true;
    @Override
    public void onNext(Object o) {//网络加载成功返回数据，会调用此方法
        super.onNext(o);
        Log.e("111", "onNext: 解析前："+o.toString());
        //返回的是json串
        T parse=onParse((String)o);
        Log.e("111", "onNext: 解析后："+parse.toString());

        if(callSuccess&&isCodeSuccess()){
            onSuccess(parse);
        }

    }

    private T onParse(String result) {
        T data=null;

        try {
            data=onConver(result);
            callSuccess=true;
        } catch (Exception e) {
            e.printStackTrace();
            callSuccess=false;
            onCallBackError("解析数据错误", ExceptionEngine.ANALYTIC_SERVER_DATA_ERROR);
        }

        return data;
    }

    protected abstract boolean isCodeSuccess();//判断返回状态

    protected abstract void onSuccess(T parse);//返回获取的泛型数据

    //将jsonElement转换为response(实体类类型)，并且通过子类来获取data数据
    protected abstract T onConver(String result);//解析数据

    //将我们需要的数据解析出来
    public abstract T convert(JsonElement result);


}
