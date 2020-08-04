package com.jqz.myapplication.disposable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import io.reactivex.disposables.Disposable;
    //主要用来处理订阅关系
public class RequestManagerImpl implements RequestManager {

    //单例创建本类对象
    private static volatile RequestManagerImpl instance;

    //管理所有订阅的集合map
    private static Map<String,Disposable> map=new HashMap<>();

    //要想单例必须写私有无参构造
    private RequestManagerImpl() {
    }

    public static RequestManagerImpl getInstance(){
        if(instance==null){
            synchronized (RequestManagerImpl.class){
                if(instance==null){
                    instance=new RequestManagerImpl();
                }
            }
        }

        return instance;
    }

    @Override//添加订阅到集合
    public void addDisposable(String tag, Disposable disposable) {
        //判断
        if(!tag.isEmpty()){//如果tag不为空则保存它所对应的订阅
            map.put(tag,disposable);
        }
    }

    @Override//移除指定标识的订阅
    public void removeDisposable(String tag) {
        //移除
        //tag不为空，并且map集合不为空，并且tag对应的订阅对象不为空
        if(!tag.isEmpty()&&!map.isEmpty()&&map.get(tag)!=null){
            map.remove(tag);//移除
        }
    }

    @Override//解除指定标识的订阅
    public void cancleDisposable(String tag) {
        //关闭订阅关系
        //tag不为空，并且map集合不为空，并且tag对应的订阅对象不为空，并且订阅关系没有被解除的情况下
        if(!tag.isEmpty()&&!map.isEmpty()&&map.get(tag)!=null&&!map.get(tag).isDisposed()){
            //先解除订阅
            map.get(tag).dispose();
            map.remove(tag);//然后再移除
        }
    }

    @Override
    public void cancleAllDisposable() {
        Disposable disposable=null;
        //解除所有的订阅
         if(!map.isEmpty()){
             //如果集合不为null
             Set<String> strings = map.keySet();
             for (String string : strings) {
                 disposable = map.get(string);
                 if(!string.isEmpty()&&disposable!=null&&!disposable.isDisposed()){
                     //解除订阅
                     disposable.dispose();
                 }
             }

         }

        //清空集合
        map.clear();
    }

    //订阅监听判断
    @Override
    public boolean isDisposable(String tag) {

        if(!tag.isEmpty()&&map.get(tag)!=null){
            return map.get(tag).isDisposed();
        }

        return false;
    }
}
