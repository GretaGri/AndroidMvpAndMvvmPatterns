package com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern;


import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.enpassio.androidmvpandmvvmpatterns.BuildConfig;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.AppExecutors;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.database.NewsDao;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.database.NewsDatabase;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.model.Article;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.model.NewsResponse;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.network.APIClient;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.network.NewsApiService;
import com.enpassio.androidmvpandmvvmpatterns.mvvm_pattern.data.network.RemoteCallBack;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Greta Grigutė on 2018-11-09.
 */
public class NewsRepository {
    private static final String LOG_TAG = "my_tag";
    private NewsDao newsDao;
    private LiveData<NewsResponse> allNews;
    private NewsApiService newsApiService;
    private LiveData <NewsResponse> responseResults;


//    public void getNewsList(String searchQuery, RemoteCallBack<LiveData<NewsResponse>> listener) {
//        newsApiService.getNewsArticles(BuildConfig.NEWS_API_DOT_ORG_KEY,
//                searchQuery).enqueue(listener);
//    }

    // A constructor that gets a handle to the database and initializes the member variables.
     public NewsRepository(final Application application) {


//         NewsDatabase db = NewsDatabase.getDatabase(application);
//         newsDao = db.newsDao();
//         if (responseResults != null) {
//             Log.d(LOG_TAG, "response is not null");
//             int i;
//             for (i = 0; i < responseResults.size(); i++) {
//                 newsDao.insert(responseResults.get(i));
//             }
//             allNews = newsDao.getAllNews();
//         }
  }

    // A wrapper for getAllWords(). Room executes all queries on a separate thread. Observed
    // LiveData will notify the observer when the data has changed.
    public LiveData<NewsResponse> getAllNews(final String searchQuery) {
                Log.d(LOG_TAG, "Getting the repository");
                newsApiService = APIClient.getClient().create(NewsApiService.class);
                newsApiService.getNewsArticles(BuildConfig.NEWS_API_DOT_ORG_KEY, searchQuery).enqueue(new Callback<LiveData<NewsResponse>>() {
                    @Override
                    public void onResponse(Call<LiveData<NewsResponse>> call, Response<LiveData<NewsResponse>> response) {
                        allNews = response.body();
                        Log.d(LOG_TAG, "Getting the reponse");
                    }

                    @Override
                    public void onFailure(Call<LiveData<NewsResponse>> call, Throwable t) {

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
