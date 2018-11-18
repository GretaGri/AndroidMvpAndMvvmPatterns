package com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PagedList;

import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.model.Article;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.network.LocalCache;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.network.NewsApiService;

/**
 * Created by Greta Grigutė on 2018-11-18.
 */
public class ArticleBoundaryCallback extends PagedList.BoundaryCallback<Article> {
    private static final int NETWORK_PAGE_SIZE = 40;
    String query;
    NewsApiService service;
    LocalCache cache;

    public ArticleBoundaryCallback(String query, NewsApiService service, LocalCache cache) {
        this.query = query;
        this.service = service;
        this.cache = cache;
    }

    // keep the last requested page.
    // When the request is successful, increment the page number.
    Integer lastRequestedPage = 1;

    MutableLiveData<String> _networkErrors = new MutableLiveData<>();
    // LiveData of network errors.
    LiveData<String> networkErrors;


    // avoid triggering multiple requests in the same time
    Boolean isRequestInProgress = false;

    @Override
   public void onZeroItemsLoaded() {
        requestAndSaveData(query);
    }

    @Override
    public void onItemAtEndLoaded(Article itemAtEnd) {
            requestAndSaveData(query);
    }

    private void requestAndSaveData(String query) {
        if (isRequestInProgress) return;

                isRequestInProgress = true;
//        searchArticles(service, query, lastRequestedPage, NETWORK_PAGE_SIZE, {repos ->
//                cache.insert(repos, {
//                        lastRequestedPage++
//                        isRequestInProgress = false
//                })
//        }, {error ->
//                _networkErrors.postValue(error)
//                isRequestInProgress = false
//        })
    }


    MutableLiveData<String> get() {
        return _networkErrors;
    }
}
