package com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data;


import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.support.annotation.NonNull;

import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data.model.NewsResponse;
import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data.network.NewsApiService;
import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data.network.RemoteCallback;
import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.presenter.mainscreen.ListContract;

public class ArticleDataFactory extends DataSource.Factory {
    private MutableLiveData<ArticleDataSource> mutableLiveData;
    private NewsApiService mNewsApiService;
    private String mSearchQuery;
    private ListContract.RecyclerView mView;
    RemoteCallback<NewsResponse> mRemoteCallback;

    /*
    public ArticleDataFactory(NewsApiService newsApiService,
                              String searchQuery, ListContract.RecyclerView view) {
        this.mNewsApiService = newsApiService;
        this.mSearchQuery = searchQuery;
        this.mutableLiveData = new MutableLiveData<ArticleDataSource>();
        this.mView = view;
    }
    */
    public ArticleDataFactory(NewsApiService newsApiService,
                              String searchQuery, RemoteCallback<NewsResponse> remoteCallback){
        this.mNewsApiService = newsApiService;
        this.mSearchQuery = searchQuery;
        this.mutableLiveData = new MutableLiveData<ArticleDataSource>();
        this.mRemoteCallback = remoteCallback;
    }

    @NonNull
    @Override
    public DataSource create() {
        ArticleDataSource articleDataSource = new ArticleDataSource(mNewsApiService,
                mSearchQuery, mRemoteCallback);
        mutableLiveData.postValue(articleDataSource);
        return articleDataSource;
    }

    MutableLiveData<ArticleDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}
