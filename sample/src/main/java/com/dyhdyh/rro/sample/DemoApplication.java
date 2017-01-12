package com.dyhdyh.rro.sample;

import android.app.Application;

import com.dyhdyh.rro.RxRetrofitClient;

/**
 * author  dengyuhan
 * created 2017/1/12 17:10
 */
public class DemoApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        //默认配置(15秒超时,HttpLoggingInterceptor,ScalarsConverterFactory,GsonConverterFactory,RxJavaCallAdapterFactory)
        //传入接口BaseUrl,是否输出log
        RxRetrofitClient.init(BuildConfig.API_URL,BuildConfig.DEBUG);

        //自定义配置
        /*
        RxRetrofitConfig config = new RxRetrofitConfig.Builder().connectTimeout(20 * 1000)
                //.addInterceptor()
                //.addConverterFactory()
                //.addCallAdapterFactory()
                .build();
        RxRetrofitClient.init(config);
        */
    }
}
