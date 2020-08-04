package com.jqz.myapplication.callback;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jqz.myapplication.app.Response;
import com.jqz.myapplication.demo.wananzhuo.WanResponse;
import com.jqz.myapplication.utils.JsonUtils;

/*
项目名：NetWorkProject
包名：com.jqz.myapplication.callback
文件名：HttpCallBack
创建者：梦想
创建时间：2020/8/4  15:09  
描述：TODO
格言：唯有自己强大，才不会感到害怕！
例子
*/public abstract class HttpCallBack<T> extends BaseCallBack<T> {
    WanResponse response;


    @Override//只需重写onConver方法和isCodeSuccess方法即可
    protected T onConver(String result) {
        T t=null;
        response=new Gson().fromJson(result,WanResponse.class);
        JsonElement data = response.getData();
        String errorMsg = response.getErrorMsg();
        int errorCode = response.getErrorCode();

        switch (errorCode){
            case -1001:
                onCallBackError("登录失效",errorCode);
                break;
            default:
                if(isCodeSuccess()){
                    t=convert(data);
                }
            break;
        }

        Log.e("111", "onConver: "+t.toString());

        return t;
    }



    @Override//判断服务器返回json串的响应状态
    protected boolean isCodeSuccess() {
        if(response!=null){
            return response.getErrorCode()==0;
        }
        return false;
    }


}
