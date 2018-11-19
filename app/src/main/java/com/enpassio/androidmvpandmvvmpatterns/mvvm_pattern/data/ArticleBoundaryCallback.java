package com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data;

import android.arch.paging.PagedList;
import android.util.Log;

import com.enpassio.androidmvpandmvvmpatterns.BuildConfig;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.model.Article;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.model.NewsResponse;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.network.LocalCache;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.network.NewsApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Greta Grigutė on 2018-11-18.
 */
public class ArticleBoundaryCallback extends PagedList.BoundaryCallback<Article> {

    private static final String LOG_TAG = "my_tag";
    private String query;
    private NewsApiService service;
    private LocalCache cache;
    private int page = 1;

    public ArticleBoundaryCallback(String query, NewsApiService service, LocalCache cache) {
        this.query = query;
        this.service = service;
        this.cache = cache;
    }


    @Override
    public void onZeroItemsLoaded() {
        super.onZeroItemsLoaded();
        requestAndSaveData(query);
    }

    @Override
    public void onItemAtEndLoaded(Article itemAtEnd) {
        super.onItemAtEndLoaded(itemAtEnd);
        Log.v("my_tag", "onItemAtEndLoaded called");
        requestAndSaveData(query);
    }

    private void requestAndSaveData(String query) {
        Log.v("my_tag", "page number is: " + page);
        service.getNewsArticles(BuildConfig.NEWS_API_DOT_ORG_KEY,
                query, page).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Article> responseResults = (ArrayList<Article>) response.body().getArticles();
                    Log.d(LOG_TAG, "Getting the reponse size: " + responseResults.size());
                    page++;
                    cache.insert(responseResults);
                } else {
                    Log.d(LOG_TAG, "Getting Error");
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.e(LOG_TAG, "error is: " + t.getMessage());
            }

        });
    }

}
