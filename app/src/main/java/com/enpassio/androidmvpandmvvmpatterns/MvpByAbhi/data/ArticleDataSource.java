package com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data;


import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.enpassio.androidmvpandmvvmpatterns.BuildConfig;
import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data.model.Article;
import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data.model.NewsResponse;
import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data.network.NewsApiService;
import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.presenter.mainscreen.ListContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleDataSource extends PageKeyedDataSource<Long, Article> {

    private NewsApiService mNewsApiService;
    private int mPageNumber;
    private String mSearchQuery;
    private ListContract.RecyclerView mView;

    ArticleDataSource(NewsApiService newsApiService,
                      String searchQuery, ListContract.RecyclerView view) {
        this.mNewsApiService = newsApiService;
        this.mPageNumber = 1;
        this.mSearchQuery = searchQuery;
        this.mView = view;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params,
                            @NonNull final LoadInitialCallback<Long, Article> callback) {
        Log.d("my_tag", "before page number is " + mPageNumber);
        mNewsApiService.getNewsArticles(BuildConfig.NEWS_API_DOT_ORG_KEY, mSearchQuery, mPageNumber).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                if (response.body() != null) {
                    if (!(response.body().getArticles().size() > 0)) {
                        mView.showEmpty();

                    } else {
                        mView.hideProgress();
                        callback.onResult(response.body().getArticles(), (long) mPageNumber, (long) mPageNumber + 1);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable throwable) {
                    mView.showError(throwable.getMessage().toString());
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull
            LoadCallback<Long, Article> callback) {

    }

    @Override
    public void loadAfter(@NonNull final PageKeyedDataSource.LoadParams<Long> params,
                          @NonNull final LoadCallback<Long, Article> callback) {
        Log.d("my_tag", "after page number is " + params.key);
        mNewsApiService.getNewsArticles(BuildConfig.NEWS_API_DOT_ORG_KEY, mSearchQuery, Integer.parseInt(params.key.toString())).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                if (response.body() != null) {
                    if (!(response.body().getArticles().size() > 0)) {
                        mView.showEmpty();

                    } else {
                        mView.hideProgress();
                        callback.onResult(response.body().getArticles(), params.key + 1);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable throwable) {
                mView.showError(throwable.getMessage().toString());
            }
        });
    }
}