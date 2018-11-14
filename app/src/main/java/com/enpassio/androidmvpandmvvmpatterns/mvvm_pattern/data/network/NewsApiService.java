package com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.network;

import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.model.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiService {

    /**
     * Retrieve list of articles
     */
    @GET("v2/top-headlines")
    Call<NewsResponse> getNewsArticles(@Query("apiKey") String publicKey,
                                       @Query("q") String searchQuery);
}