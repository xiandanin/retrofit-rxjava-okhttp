package com.dyhdyh.rro.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.dyhdyh.rro.RxRetrofitClient;
import com.dyhdyh.rro.sample.adapter.MovieAdapter;
import com.dyhdyh.rro.sample.api.IMovieApi;
import com.dyhdyh.rro.sample.model.HotMovieModel;
import com.dyhdyh.widget.loadingbar.LoadingBar;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView rv;
    private View rlContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = (RecyclerView) findViewById(R.id.rv);
        rlContent = findViewById(R.id.rl_content);
    }

    /**
     * 请求热映电影列表
     *
     * @param v
     */
    public void requestHotMovie(View v) {
        //两种写法用哪种看自己需要
        requestByLambda();
        //requestBySubscribe();
    }


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

    private void bindData(List<HotMovieModel.SubjectsEntity> data) {
        MovieAdapter adapter = new MovieAdapter(data);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }


}
