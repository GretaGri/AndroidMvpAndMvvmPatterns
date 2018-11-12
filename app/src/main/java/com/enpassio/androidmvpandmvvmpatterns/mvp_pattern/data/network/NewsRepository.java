package com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.data.network;

import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.enpassio.androidmvpandmvvmpatterns.BuildConfig;
import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data.ArticleDataFactory;
import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data.model.Article;
import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.data.model.NewsResponse;

import java.util.concurrent.Executor;

public class NewsRepository {
    // static variable single_instance of type Singleton/NewsRepository
    private static NewsRepository newsRepository = null;
    private static NewsApiService newsApiService;
    private static com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data.network.NewsApiService mNewsApiService;
    private static Executor mExecutor;

    // static method to create instance of Singleton/NewsRepository class
    public static NewsRepository getInstance() {
        newsApiService = APIClient.getClient().create(NewsApiService.class);
        //create the service
        mNewsApiService = com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data.network.APIClient.getClient().create(com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data.network.NewsApiService.class);

        if (newsRepository == null)
            newsRepository = new NewsRepository();

        return newsRepository;
    }

    public void getNewsList(String searchQuery, RemoteCallBack<NewsResponse> listener) {
        newsApiService.getNewsArticles(BuildConfig.NEWS_API_DOT_ORG_KEY,
                searchQuery).enqueue(listener);
    }

    public LiveData<PagedList<Article>> getLiveDataOfPagedList(String searchQuery) {

        ArticleDataFactory articleDataFactory = new ArticleDataFactory(mNewsApiService, searchQuery);

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(20 * 3)
                        .setPageSize(20)
                        .setPrefetchDistance(10)
                        .build();
        LiveData<PagedList<Article>> articleLiveData = (new LivePagedListBuilder(articleDataFactory, pagedListConfig))
                .setFetchExecutor(mExecutor)
                .build();

        return articleLiveData;
    }
}
