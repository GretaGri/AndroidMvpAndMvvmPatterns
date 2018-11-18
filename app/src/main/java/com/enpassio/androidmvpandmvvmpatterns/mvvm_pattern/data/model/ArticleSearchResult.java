package com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.model;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;

/**
 * Created by Greta Grigutė on 2018-11-18.
 */
public class ArticleSearchResult {

    private LiveData<PagedList<Article>> data;
    private LiveData<String> networkErrors;

    public ArticleSearchResult(LiveData<PagedList<Article>> data, LiveData<String> networkErrors) {
        this.data = data;
        this.networkErrors = networkErrors;
    }

    public LiveData<PagedList<Article>> getData() {
        return data;
    }

    public void setData(LiveData<PagedList<Article>> data) {
        this.data = data;
    }

    public LiveData<String> getNetworkErrors() {
        return networkErrors;
    }

    public void setNetworkErrors(LiveData<String> networkErrors) {
        this.networkErrors = networkErrors;
    }
}
