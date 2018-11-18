package com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern;


import android.app.Application;
import android.app.KeyguardManager;
import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PagedList;
import android.util.Log;

import com.enpassio.androidmvpandmvvmpatterns.BuildConfig;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.AppExecutors;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.database.NewsDao;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.database.NewsDatabase;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.model.Article;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.model.NewsResponse;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.network.APIClient;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.network.LocalCache;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.network.NewsApiService;

import java.util.ArrayList;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.arch.paging.LivePagedListBuilder;

/**
 * Created by Greta Grigutė on 2018-11-09.
 */
public class NewsRepository {
    private static final String LOG_TAG = "my_tag";
    private static final int DATABASE_PAGE_SIZE = 10;
    private final NewsApiService newsApiService;
    private NewsDao newsDao;
    private ArrayList<Article> responseResults;
    private DataSource.Factory<Integer, Article> dataSourceFactory;
    private Executor executor;

    // A constructor that gets a handle to the database and initializes the member variables.
    public NewsRepository(final Application application) {
        NewsDatabase db = NewsDatabase.getDatabase(application);
        newsDao = db.newsDao();
        newsApiService = APIClient.getClient().create(NewsApiService.class);
        executor = AppExecutors.getInstance().diskIO();
    }

    public void insert(final Article article) {executor.execute(new Runnable() {
        @Override
        public void run() {
            newsDao.insert(article);}});
    }

    // A wrapper for getAllWords(). Room executes all queries on a separate thread. Observed
    // LiveData will notify the observer when the data has changed.
    public LiveData<PagedList<Article>> getAllNews(String searchQuery) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                newsDao.deleteAll();
            }
        });

        Log.d(LOG_TAG, "Getting the repository");
        Log.v("my_tag", "newsApiService is: " + newsApiService);
        LiveData<PagedList<Article>> allNews;
        newsApiService.getNewsArticles(BuildConfig.NEWS_API_DOT_ORG_KEY,
                searchQuery).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()) {
                    responseResults = (ArrayList<Article>) response.body().getArticles();
                    Log.d(LOG_TAG, "Getting the reponse size: " + responseResults.size());
                    LocalCache localCache = new LocalCache(newsDao,executor);
                    localCache.insert(responseResults,false);
                } else {
                    Log.d(LOG_TAG, "Getting Error");
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.e(LOG_TAG, "error is: " + t.getMessage());
            }
        });
        // Get data source factory from the local database
        dataSourceFactory = newsDao.getAllNews();

        allNews = new LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE).build();

        return allNews;
    }
}
