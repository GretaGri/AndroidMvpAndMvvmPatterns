package com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern;


import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.enpassio.androidmvpandmvvmpatterns.BuildConfig;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.database.NewsDao;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.database.NewsDatabase;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.model.Article;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.model.NewsResponse;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.network.APIClient;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.network.NewsApiService;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.network.RemoteCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Greta Grigutė on 2018-11-09.
 */
public class NewsRepository {
    private static final String LOG_TAG = "my_tag";
    private NewsDao newsDao;
    private MutableLiveData<List<Article>> allNews;
    private NewsApiService newsApiService;
    private ArrayList<Article> responseResults;


    public void getNewsList(String searchQuery, RemoteCallBack<NewsResponse> listener) {
        newsApiService.getNewsArticles(BuildConfig.NEWS_API_DOT_ORG_KEY,
                searchQuery).enqueue(listener);
    }

    // A constructor that gets a handle to the database and initializes the member variables.
     public NewsRepository(final Application application) {
         NewsDatabase db = NewsDatabase.getDatabase(application);
         newsDao = db.newsDao();
         if (responseResults != null) {
             Log.d(LOG_TAG, "response is not null");
             int i;
             for (i = 0; i < responseResults.size(); i++) {
                 newsDao.insert(responseResults.get(i));
             }
             allNews = (MutableLiveData<List<Article>>) newsDao.getAllNews();
         }
    }

    // A wrapper for getAllWords(). Room executes all queries on a separate thread. Observed
    // LiveData will notify the observer when the data has changed.
    public LiveData<List<Article>> getAllNews(String searchQuery) {
        Log.d(LOG_TAG, "Getting the repository");
        newsApiService = APIClient.getClient().create(NewsApiService.class);
        getNewsList(searchQuery, new RemoteCallBack<NewsResponse>() {
            @Override
            public void onSuccess(NewsResponse response) {
                responseResults = (ArrayList<Article>) response.getArticles();
                Log.d(LOG_TAG, "Getting the reponse");
            }

            @Override
            public void onUnauthorized() {

            }

            @Override
            public void onFailed(Throwable throwable) {

            }
        });
        return allNews;
    }

    // A wrapper for the insert() method. You must call this on a non-UI thread or your app will
    // crash. Room ensures that you don't do any long-running operations on the main thread,
    // blocking the UI.
    public void insert(Article article) {
        new insertAsyncTask(newsDao).execute(article);
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
}
