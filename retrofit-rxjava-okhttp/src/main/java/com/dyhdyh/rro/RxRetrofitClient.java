package com.dyhdyh.rro;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * author  dengyuhan
 * created 2017/1/12 15:36
 */
public class RxRetrofitClient {
    private static RxRetrofitClient mInstance;

    private Retrofit mRetrofit;
    private RxRetrofitConfig mConfig;

    public static void init(String baseUrl,boolean logger) {
        init(RxRetrofitConfig.createDefault(baseUrl,logger));
    }

    public static void init(RxRetrofitConfig config) {
        if (mInstance == null) {
            mInstance=new RxRetrofitClient(config);
        }
    }

    private RxRetrofitClient(RxRetrofitConfig config){
        this.mConfig=config;
        this.mRetrofit=newRetrofit();

    }


    public static <T> T create(final Class<T> service) {
        if (mInstance==null){
            throw new NullPointerException("未初始化RxRetrofitClient");
        }
        return mInstance.mRetrofit.create(service);
    }


    private Retrofit newRetrofit() {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(mConfig.getBaseUrl());
        //设置转换器
        List<Converter.Factory> converterFactories = mConfig.getConverterFactories();
        if (converterFactories != null&&!converterFactories.isEmpty()) {
            for (Converter.Factory factory : converterFactories) {
                builder.addConverterFactory(factory);
            }
        }
        //设置适配器
        List<CallAdapter.Factory> adapterFactories = mConfig.getAdapterFactories();
        if (adapterFactories != null&&!adapterFactories.isEmpty()) {
            for (CallAdapter.Factory factory : adapterFactories) {
                builder.addCallAdapterFactory(factory);
            }
        }
        //设置okhttp
        OkHttpClient httpClient = newOkHttpClient();
        builder.client(httpClient).build();
        return builder.build();
    }


    private OkHttpClient newOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(mConfig.getConnectTimeoutMilliseconds(), TimeUnit.MILLISECONDS);
        //设置拦截器
        List<Interceptor> interceptors = mConfig.getInterceptors();
        if (interceptors != null&&!interceptors.isEmpty()) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }
        return builder.build();
    }

}
