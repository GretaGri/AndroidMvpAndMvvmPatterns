package com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data;

import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.util.Log;

import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data.model.Article;
import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data.network.APIClient;
import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data.network.NewsApiService;
import com.enpassio.androidmvpandmvvmpatterns.MvpByAbhi.data.network.RemoteCallBack;

import java.util.concurrent.Executor;

public class NewsRepository {

    private static final String LOG_TAG = NewsRepository.class.getSimpleName();
    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static NewsRepository sInstance;
    private static NewsApiService mNewsApiService;
    private static Executor mExecutor;

    public synchronized static NewsRepository getInstance() {
        //create the service
        mNewsApiService = APIClient.getClient().create(NewsApiService.class);
        mExecutor = AppExecutors.getInstance().networkIO();
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new NewsRepository();
                Log.d(LOG_TAG, "Made new repository");
            }
        }
        return sInstance;
    }

    public LiveData<PagedList<Article>> getLiveDataOfPagedList(String searchQuery, RemoteCallBack remoteCallBack) {

        ArticleDataFactory articleDataFactory = new ArticleDataFactory(mNewsApiService, searchQuery, remoteCallBack);

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
