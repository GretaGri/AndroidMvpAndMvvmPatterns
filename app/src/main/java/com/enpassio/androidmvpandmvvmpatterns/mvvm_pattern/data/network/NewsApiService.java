package com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data.network;

import com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi.data.model.NewsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiService {

    /**
     * Retrieve list of articles
     */
    @GET("v2/everything")
    Call<NewsResponse> getNewsArticles(@Query("apiKey") String publicKey,
                                       @Query("q") String searchQuery,
                                       @Query("page") int pageNumber);