package com.jqz.myapplication.net;

import com.google.gson.JsonElement;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiService {
    //封装请求方式
    //获取网络数据
    @GET
    Observable<JsonElement> get(@Url String url, @QueryMap Map<String,Object> queryMap, @HeaderMap Map<String,Object> headerMap);

    //上传json串
    @POST
    Observable<JsonElement> postJson(@Url String url, RequestBody requestBody,@HeaderMap Map<String,Object> headerMap);

    //上传表单信息
    @POST
    @FormUrlEncoded
    Observable<JsonElement> post(@Url String url, @FieldMap Map<String,Object> fieldMap,@HeaderMap Map<String,Object> headerMap);

    //删除数据库中的数据
    @DELETE
    Observable<JsonElement> delete(@Url String url,@QueryMap Map<String,Object> queryMap,@HeaderMap Map<String,Object> headerMap);

    //更新数据库中的数据
    @PUT
    @FormUrlEncoded
    Observable<JsonElement> put(@Url String url,@QueryMap Map<String,Object> queryMap,@HeaderMap Map<String,Object> headerMap);

    //上传文件
    @POST
    @Multipart
    Observable<JsonElement> postPart(@Url String url, @PartMap Map<String,Object> partMap, List<MultipartBody.Part> partList);

    //下载文件
    @GET
    @Streaming
    Observable<ResponseBody> getFile(@Url String url,@QueryMap Map<String,Object> queryMap,@HeaderMap Map<String,Object> headerMap);




}
