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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Greta Grigutė on 2018-11-09.
 */
public class NewsRepository {
    private static final String LOG_TAG = "my_tag";
    private NewsDao newsDao;

    private final NewsApiService newsApiService;
    private ArrayList<Article> responseResults;
    private Executor mExecutor;


    // A constructor that gets a handle to the database and initializes the member variables.
    public NewsRepository(final Application application) {
        NewsDatabase db = NewsDatabase.getDatabase(application);
        newsDao = db.newsDao();
        newsApiService = APIClient.getClient().create(NewsApiService.class);
        mExecutor = AppExecutors.getInstance().mainThread();
    }


    // A wrapper for getAllWords(). Room executes all queries on a separate thread. Observed
    // LiveData will notify the observer when the data has changed.
    public LiveData<List<Article>> getAllNews(String searchQuery) {
        Log.d(LOG_TAG, "Getting the repository");
        Log.v("my_tag", "newsApiService is: " + newsApiService);
        final MutableLiveData<List<Article>>  allNews = new MutableLiveData<>();
        newsApiService.getNewsArticles(BuildConfig.NEWS_API_DOT_ORG_KEY,
                searchQuery).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()) {
                    responseResults = (ArrayList<Article>) response.body().getArticles();
                    Log.d(LOG_TAG, "Getting the reponse size: " + responseResults.size());
                    mExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            allNews.postValue(responseResults);
                        }
                    });
                } else {
                    Log.d(LOG_TAG, "Getting Error");
                }

            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.e(LOG_TAG, "error is: " + t.getMessage());
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