package com.jqz.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jqz.myapplication.callback.BaseCallBack;
import com.jqz.myapplication.callback.BaseObserver;
import com.jqz.myapplication.callback.HttpCallBack;
import com.jqz.myapplication.client.HttpClient;
import com.jqz.myapplication.demo.wananzhuo.BannerDemo;
import com.jqz.myapplication.demo.wananzhuo.Demo;
import com.jqz.myapplication.demo.wananzhuo.LoginDemo;
import com.jqz.myapplication.demo.wananzhuo.WanResponse;
import com.jqz.myapplication.disposable.RequestManagerImpl;
import com.jqz.myapplication.utils.JsonUtils;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* new HttpClient.Builder()
                .setApiUrl("banner/json")
                .get()
                .build().request(new HttpCallBack<List<BannerDemo>>() {
            @Override
            protected void onCallBackError(String msg, int code) {

            }

            @Override
            protected void cancle() {

            }

            @Override
            protected void onSuccess(List<BannerDemo> parse) {

            }

            @Override
            public List<BannerDemo> convert(JsonElement result) {
                return JsonUtils.jsonToClassList(result,BannerDemo.class);
            }
        });*/

        HashMap<String, Object> map = new HashMap<>();

        map.put("username","zhangsan123");
        map.put("password","123456");
        HttpClient build = new HttpClient.Builder()
                .setApiUrl("user/login")
                .post()
//                .setTag()
                .setParamser(map)
                .build();
        build.request(new HttpCallBack<LoginDemo>() {
            @Override
            protected void onCallBackError(String msg, int code) {
                Log.e("111", "onCallBackError: "+msg);
            }

            @Override
            protected void cancle() {

//                RequestManagerImpl.getInstance().cancleDisposable();
            }

            @Override
            protected void onSuccess(LoginDemo parse) {
                Log.e("111", "onSuccess: "+parse.toString());
            }

            @Override
            public LoginDemo convert(JsonElement result) {
                return new Gson().fromJson(result,LoginDemo.class);
            }
        });

    }
}
