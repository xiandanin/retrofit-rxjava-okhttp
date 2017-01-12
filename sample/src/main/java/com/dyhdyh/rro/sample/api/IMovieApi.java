package com.dyhdyh.rro.sample.api;

import com.dyhdyh.rro.sample.model.HotMovieModel;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * author  dengyuhan
 * created 2017/1/12 17:20
 */
public interface IMovieApi {

    /**
     * 热映电影列表
     * @return
     */
    @GET("movie/in_theaters")
    Observable<HotMovieModel> getHotMovie();


    /**
     * 根据id获取电影信息
     * @param id 路径参数:电影id
     * @return
     */
    @GET("movie/subject/{id}")
    Observable<HotMovieModel> getMovieDetail(@Path("id") String id);


    /**
     * 根据关键字搜索电影
     * @param q 参数:关键字
     * @return
     */
    @GET("movie/search")
    Observable<HotMovieModel> getSearchMovie(@Query("q") String q);
}
