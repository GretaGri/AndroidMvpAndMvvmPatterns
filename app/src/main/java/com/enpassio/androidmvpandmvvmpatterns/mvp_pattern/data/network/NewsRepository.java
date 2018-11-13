package com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.data.network;

import com.enpassio.androidmvpandmvvmpatterns.BuildConfig;
import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.data.model.NewsResponse;

import java.util.concurrent.Executor;

public class NewsRepository {
    // static variable single_instance of type Singleton/NewsRepository
    private static NewsRepository newsRepository = null;
    private static NewsApiService newsApiService;
    private static Executor mExecutor;

    // static method to create instance of Singleton/NewsRepository class
    public static NewsRepository getInstance() {
        newsApiService = APIClient.getClient().create(NewsApiService.class);

        if (newsRepository == null)
            newsRepository = new NewsRepository();

        return newsRepository;
    }

    public void getNewsList(String searchQuery, RemoteCallBack<NewsResponse> listener) {
        newsApiService.getNewsArticles(BuildConfig.NEWS_API_DOT_ORG_KEY,
                searchQuery).enqueue(listener);
    }
}