package com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.data.network;

import com.enpassio.androidmvpandmvvmpatterns.BuildConfig;
import com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.data.model.NewsResponse;

public class NewsRepository {
    // static variable single_instance of type Singleton/NewsRepository
    private static NewsRepository newsRepository = null;
    private final NewsApiService newsApiService;


    public NewsRepository() {
        newsApiService = APIClient.getClient().create(NewsApiService.class);
    }

    // static method to create instance of Singleton/NewsRepository class
    public static NewsRepository getInstance() {
        if (newsRepository == null)
            newsRepository = new NewsRepository();

        return newsRepository;
    }

    public void getNewsList(String searchQuery, RemoteCallBack<NewsResponse> listener) {
        newsApiService.getNewsArticles(BuildConfig.NEWS_API_DOT_ORG_KEY,
                searchQuery).enqueue(listener);
    }
}
