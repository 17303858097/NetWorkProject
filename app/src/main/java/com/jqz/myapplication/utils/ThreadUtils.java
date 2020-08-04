package com.jqz.myapplication.utils;

import android.os.Looper;

public class ThreadUtils {
    //判断是否是主线程
    public static boolean isMainThread(){
        //如果是主线程返回true，不是返回false
        return Looper.getMainLooper().getThread().getId()==Thread.currentThread().getId();
    }
}
