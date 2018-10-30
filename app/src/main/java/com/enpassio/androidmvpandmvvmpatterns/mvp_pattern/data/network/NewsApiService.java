package com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.data.network;

import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.data.model.Article;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiService {

    /**
     * Retrieve list of articles
     */
    @GET("v2/top-headlines")
    Call<List<Article>> getNewsArticles(@Query("apiKey") String publicKey,
                                        @Query("q") String searchQuery);
}