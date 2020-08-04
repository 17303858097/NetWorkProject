package com.jqz.myapplication.disposable;

import io.reactivex.disposables.Disposable;

public interface RequestManager {
    //添加订阅，根据标识添加相应的订阅

    void addDisposable(String tag, Disposable disposable);
    //根据标识符移除相应的订阅
    void removeDisposable(String tag);
    //取消订阅,根据指定的标识
    void cancleDisposable(String tag);
    //取消所有订阅
    void cancleAllDisposable();
    //判断订阅状态
    boolean isDisposable(String tag);

}
