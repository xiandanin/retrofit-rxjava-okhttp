# retrofit-rxjava-okhttp
Retrofit-RxJava-OkHttp二次封装<br/>
正在逐渐完善，有问题和建议也可以在issues提出
<br/>
### Screenshot
![Example1](/screenshot.gif)
<br/>

### Android Studio - 在build.gradle中引入
```java
compile 'com.dyhdyh.rro:retrofit-rxjava-okhttp:1.0.0-Preview'
```
### 需要的依赖
```java
compile 'com.squareup.okhttp3:okhttp:3.5.0'
compile 'com.squareup.retrofit2:retrofit:2.1.0'
compile 'com.squareup.retrofit2:adapter-rxjava:2.1.0'
compile 'com.squareup.retrofit2:converter-gson:2.1.0'
compile 'com.squareup.retrofit2:converter-scalars:2.1.0'
compile 'io.reactivex:rxandroid:1.2.1'
compile 'com.google.code.gson:gson:2.8.0'
compile 'com.squareup.okhttp3:logging-interceptor:3.5.0'
 ```
<br/>

#### Example
初始化配置
```java
//初始化配置
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
```

<br/>
##### 请求的两种用法
```java
    /**
     * lambda写法
     */
    private void requestByLambda() {
        RxRetrofitClient.create(IMovieApi.class).getHotMovie()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> {
                    Log.d(TAG, Thread.currentThread().getName() + "---onStart......");
                    //显示loading
                    LoadingBar.show(rlContent);
                })
                .subscribe(hotMovieModel -> {
                    Log.d(TAG, Thread.currentThread().getName() + "---onNext......" + hotMovieModel);
                    //绑定数据
                    bindData(hotMovieModel.getSubjects());
                }, throwable -> {
                    Log.d(TAG, Thread.currentThread().getName() + "---onError......" + throwable);
                    //取消loading
                    LoadingBar.cancel(rlContent);
                }, () -> {
                    Log.d(TAG, Thread.currentThread().getName() + "---onCompleted......");
                    //取消loading
                    LoadingBar.cancel(rlContent);
                });
    }


    /**
     * subscribe写法
     */
    public void requestBySubscribe() {
        RxRetrofitClient.create(IMovieApi.class).getHotMovie()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HotMovieModel>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, Thread.currentThread().getName() + "---onStart......");
                        //显示loading
                        LoadingBar.show(rlContent);
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, Thread.currentThread().getName() + "---onCompleted......");
                        //取消loading
                        LoadingBar.cancel(rlContent);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, Thread.currentThread().getName() + "---onError......" + e);
                        //取消loading
                        LoadingBar.cancel(rlContent);
                    }

                    @Override
                    public void onNext(HotMovieModel hotMovieModel) {
                        Log.d(TAG, Thread.currentThread().getName() + "---onNext......" + hotMovieModel);
                        //绑定数据
                        bindData(hotMovieModel.getSubjects());
                    }
                });
    }
```


###### Android交流QQ群:146262062
###### http://dyhdyh.com/
