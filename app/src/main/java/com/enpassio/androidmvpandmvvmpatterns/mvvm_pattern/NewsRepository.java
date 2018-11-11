package com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern;


import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.enpassio.androidmvpandmvvmpatterns.BuildConfig;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.database.NewsDao;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.database.NewsDatabase;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.model.Article;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.model.NewsResponse;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.network.APIClient;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.network.NewsApiService;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.network.RemoteCallBack;

import java.util.List;

/**
 * Created by Greta Grigutė on 2018-11-09.
 */
public class NewsRepository {
    private NewsDao mNewsDao;
    private LiveData<List<Article>> mAllNews;
    private static NewsRepository newsRepository = null;
    private final NewsApiService newsApiService;


    public void getNewsList(String searchQuery, RemoteCallBack<NewsResponse> listener) {
        newsApiService.getNewsArticles(BuildConfig.NEWS_API_DOT_ORG_KEY,
                searchQuery).enqueue(listener);
    }

    // A constructor that gets a handle to the database and initializes the member variables.
     public NewsRepository(Application application) {

        newsApiService = APIClient.getClient().create(NewsApiService.class);

        NewsDatabase db = NewsDatabase.getDatabase(application);
        mNewsDao = db.newsDao();
        mAllNews = mNewsDao.getAllNews();
    }

    // A wrapper for getAllWords(). Room executes all queries on a separate thread. Observed
    // LiveData will notify the observer when the data has changed.
    public LiveData<List<Article>> getAllNews() {
        return mAllNews;
    }

    // A wrapper for the insert() method. You must call this on a non-UI thread or your app will
    // crash. Room ensures that you don't do any long-running operations on the main thread,
    // blocking the UI.
    public void insert(Article article) {
        new insertAsyncTask(mNewsDao).execute(article);
    }

    //AsyncTask method
    private static class insertAsyncTask extends AsyncTask<Article, Void, Void> {

        private NewsDao mAsyncTaskDao;

        insertAsyncTask(NewsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Article... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }


    // static method to create instance of Singleton/NewsRepository class
//    public static NewsRepository getInstance()
//    {
//        if (newsRepository == null)
//            newsRepository = new NewsRepository();
//
//        return newsRepository;
//    }
}
