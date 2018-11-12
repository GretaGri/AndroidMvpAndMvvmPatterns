package com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data;

import android.util.Log;

import com.enpassio.androidmvpandmvvmpatterns.BuildConfig;
import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data.model.NewsResponse;
import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data.network.APIClient;
import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data.network.NewsApiService;
import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data.network.RemoteCallback;

public class NewsRepository {

    private static final String LOG_TAG = NewsRepository.class.getSimpleName();
    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static NewsRepository sInstance;
    private final NewsApiService mNewsApiService;

    private NewsRepository() {
        //create the service
        mNewsApiService = APIClient.getClient().create(NewsApiService.class);
    }

    public synchronized static NewsRepository getInstance() {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new NewsRepository();
                Log.d(LOG_TAG, "Made new repository");
            }
        }
        return sInstance;
    }

    public void getNewsForQueriedParameter(String searchQuery, RemoteCallback<NewsResponse> listener) {
        mNewsApiService.getNewsArticles(BuildConfig.NEWS_API_DOT_ORG_KEY, searchQuery).enqueue(listener);
    }
}
